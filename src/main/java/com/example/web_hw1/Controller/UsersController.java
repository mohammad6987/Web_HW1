package com.example.web_hw1.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    @PostMapping("/users/register")
    public void register(){

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
