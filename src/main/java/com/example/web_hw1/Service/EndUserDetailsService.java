package com.example.web_hw1.Service;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Repository.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EndUserDetailsService {
    @Autowired
    EndUserRepository endUserRepository;
    public EndUser createUser(EndUserDto endUserDto) {
        EndUser endUser = new EndUser();
        endUser.setId(endUserDto.getId());
        endUser.setUsername(endUserDto.getUsername());
        endUser.setPassword(endUserDto.getPassword());
        endUser.setAuthorized(false);
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
}
