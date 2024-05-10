package com.example.web_hw1.Controller;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Service.EndUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UsersController {
    private EndUserDetailsService endUserDetailsService;
    public UsersController(EndUserDetailsService endUserDetailsService){
        this.endUserDetailsService = endUserDetailsService;
    }
    @PostMapping("/users/register")
    public EndUser register(@RequestBody EndUserDto endUserDto) throws Exception {
        try {
            return endUserDetailsService.createUser(endUserDto);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    @PostMapping("/users/login")
    public void login(@RequestBody EndUserDto endUserDto){
        EndUser endUser = endUserDetailsService.getUserById(endUserDto.getId());
        if(endUser != null){

        }
        else {
            throw new UsernameNotFoundException("This user doesn't exist!");
        }
    }

    @PutMapping("/admin/users?username={username}&active={active}")
    public void changeUserStatues(){

    }

    @PostMapping("/user/api-tokens")
    public void createToken(){

    }

    @GetMapping("/user/api-tokens")
    public void tokensList(){

    }

    @DeleteMapping("/user/api-tokens")
    public void removeToken(){

    }
}
