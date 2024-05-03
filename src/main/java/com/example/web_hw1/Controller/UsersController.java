package com.example.web_hw1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @PostMapping()
    public void register(){

    }
    @PostMapping
    public void login(){

    }

    @PutMapping()
    public void changeUserStatues(){

    }

    @PostMapping()
    public void createToken(){

    }

    @GetMapping()
    public void tokensList(){

    }

    @PostMapping()
    public void removeToken(){

    }
}
