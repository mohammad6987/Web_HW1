package com.example.web_hw1.Controller;

import com.example.web_hw1.Exception.RepeatedUsername;
import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Model.TokenDto;
import com.example.web_hw1.Model.TokenPack;
import com.example.web_hw1.Service.EndUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
public class UsersController {
    private EndUserDetailsService endUserDetailsService;
    public UsersController(EndUserDetailsService endUserDetailsService){
        this.endUserDetailsService = endUserDetailsService;
    }
    @PostMapping("/users/register")
    public ResponseEntity<String> register(@RequestBody EndUserDto endUserDto) throws Exception {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(endUserDetailsService.createUser(endUserDto));
            }catch (RepeatedUsername e){
                return  ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
    }
    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody EndUserDto endUserDto){
        try {
            EndUser endUser = endUserDetailsService.getUserByUsername(endUserDto.getUsername());

                return ResponseEntity.status(HttpStatus.OK).body(endUserDetailsService.validatePassword(endUser.getUsername() , endUserDto.getPassword()));

        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


    }

    @PutMapping("/admin/username={username}&active={active}")
    public ResponseEntity<String> changeUserStatues(@AuthenticationPrincipal EndUser endUser,@PathVariable String username , @PathVariable boolean active) throws AccessDeniedException {
       try {
           endUserDetailsService.enableUser(endUser,username ,active);
           return ResponseEntity.status(HttpStatus.OK).body(username + " is now "+ (active ? "activated":"locked"));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
       }
    }
    @Secured("ROLE_USER")
    @PostMapping("/user/api-tokens")
    public ResponseEntity<String> createToken(@AuthenticationPrincipal EndUser endUser, @RequestBody TokenDto tokenDto) {
        System.out.println("error in controller!");
        if(endUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("nobody logged in!");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ObjectMapper().writeValueAsString(endUserDetailsService.generateToken(tokenDto.getName(), tokenDto.getDate(), endUser)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/user/api-tokens")
    @Secured("ROLE_USER")
    public ResponseEntity<String> tokensList(@AuthenticationPrincipal EndUser endUser){
        if(endUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("nobody logged in!");
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(endUserDetailsService.getAllTokens(endUser));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("there is a problem with your token!");
        }
    }

    @DeleteMapping("/user/api-tokens")
    @Secured("ROLE_USER")
    public ResponseEntity<String> removeToken(@AuthenticationPrincipal EndUser endUser , @RequestBody TokenDto tokenDto){
            try {
                return ResponseEntity.status(HttpStatus.OK).body(endUserDetailsService.removeToken(endUser , tokenDto.getName() , tokenDto.getTokenValue()));
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
    }

    @GetMapping("/admin/getAllUsers")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> getAllUser(){
        return  ResponseEntity.status(200).body(endUserDetailsService.getAllUsers());
    }
}
