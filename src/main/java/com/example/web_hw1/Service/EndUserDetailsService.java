package com.example.web_hw1.Service;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Repository.EndUserRepository;
import com.example.web_hw1.Repository.TokenRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class EndUserDetailsService {

    private final EndUserRepository endUserRepository;
    private final TokenRepository tokenRepository;

    public EndUserDetailsService(EndUserRepository endUserRepository,TokenRepository tokenRepository) {
        this.endUserRepository = endUserRepository;
        this.tokenRepository = tokenRepository;
    }

    public EndUser createUser(EndUserDto endUserDto) throws Exception {
        EndUser endUser = new EndUser();
        endUser.setId(endUserDto.getId());
        endUser.setUsername(endUserDto.getUsername());
        endUser.setPassword(hashString(endUserDto.getPassword()));
        endUser.setAuthorized(false);
        endUser.setRole("USER");
        if(endUserRepository.getEndUserByUsername(endUser.getUsername()).isPresent()){
            throw new Exception("this user already exist!");
        }
        return endUserRepository.save(endUser);

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
        byte[] encodedhash = digest.digest(
                data.getBytes(StandardCharsets.UTF_8));

        // Convert byte array to hex string for readability
        StringBuilder sb = new StringBuilder();
        for (byte b : encodedhash) {
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
}
