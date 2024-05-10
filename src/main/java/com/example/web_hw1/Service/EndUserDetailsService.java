package com.example.web_hw1.Service;

import com.example.web_hw1.Exception.RepeatedUsername;
import com.example.web_hw1.JWTUtils.TokenManger;
import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Model.TokenPack;
import com.example.web_hw1.Repository.EndUserRepository;
import com.example.web_hw1.Repository.TokenRepository;
import com.sun.security.auth.UserPrincipal;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class EndUserDetailsService {

    private final EndUserRepository endUserRepository;
    private final TokenRepository tokenRepository;
    private final TokenManger tokenManger;

    public EndUserDetailsService(EndUserRepository endUserRepository,TokenRepository tokenRepository,TokenManger tokenManger) {
        this.endUserRepository = endUserRepository;
        this.tokenRepository = tokenRepository;
        this.tokenManger = tokenManger;
    }


    public String createUser(EndUserDto endUserDto) throws Exception {
        if(endUserRepository.getEndUserByUsername(endUserDto.getUsername()).isPresent()){
            throw new RepeatedUsername("username already taken!");
        }
        EndUser endUser = new EndUser();
        endUser.setUsername(endUserDto.getUsername());
        endUser.setPassword(hashString(endUserDto.getPassword()));
        endUser.setAuthorized(false);
        endUser.setRole("USER");
        endUserRepository.save(endUser);

        return ("new User created!\n" +
                "username = "+ endUserDto.getUsername()+
                "\nid = " + endUser.getId()+
                "\nrole = "+ endUser.getRole());

    }
    public void generateToken(String tokenName , Date expireDate , EndUser endUser){
        TokenPack tokenPack = new TokenPack();
        tokenPack.setTokenValue(tokenManger.generateToken(tokenName, expireDate));
        tokenPack.setName(tokenName);
        tokenPack.setExpireDate(expireDate);
        tokenRepository.save(tokenPack);

    }
    public EndUser getUserById(long id){
        Optional<EndUser> user = endUserRepository.getEndUserById(id);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("this user doesn't exist!");
        }else{
            return user.get();
        }
    }
    public EndUser getUserByUsername(String username){
        Optional<EndUser> user = endUserRepository.getEndUserByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("this user doesn't exist!");
        }else{
            return user.get();
        }
    }
    public boolean validatePassword(String username, String password) throws NoSuchAlgorithmException {
        EndUser endUser = endUserRepository.getEndUserByUsername(username).get();
        return (hashString(password).equals(endUser.getPassword()));
    }

    public static String hashString(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : encodedHash) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public void enableUser(String username, boolean condition){
        Optional<EndUser> endUser = endUserRepository.getEndUserByUsername(username);
        if(endUser.isPresent()) {
            endUser.get().setAuthorized(condition);
            endUserRepository.save(endUser.get());
        }else{
            throw new UsernameNotFoundException("this username doesn't exist!");
        }
    }


    public String getAllTokens(EndUser endUser){
        Collection<TokenPack> tokens = tokenRepository.findByUsername(endUser.getUsername());
        if(tokens.isEmpty()){
            return "this user hasn't created any tokens yet!";
        }
        return  "tokens count :" + tokens.size()+"\n"+ tokens.stream().map(tokenPack -> "{\ntoken name : "+ tokenPack.getName()+"\n" +
                "expire date : "+ tokenPack.getExpireDate()+"\n" +
                "token value ; "+ tokenPack.getTokenValue().substring(0,10)+"****\n}").collect(Collectors.joining("*************\n"));
    }
}
