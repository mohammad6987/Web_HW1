package com.example.web_hw1.Controller;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Service.EndUserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    private EndUserDetailsService endUserDetailsService;
    public UsersController(EndUserDetailsService endUserDetailsService){
        this.endUserDetailsService = endUserDetailsService;
    }
    @PostMapping("/users/register")
    public EndUser register(@RequestBody EndUserDto endUserDto){
        return endUserDetailsService.createUser(endUserDto);
    }
    @PostMapping("/users/login")
    public void login(){

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
