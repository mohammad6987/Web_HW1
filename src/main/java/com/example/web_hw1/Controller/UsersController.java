package com.example.web_hw1.Controller;

import com.example.web_hw1.Exception.RepeatedUsername;
import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Model.TokenPack;
import com.example.web_hw1.Service.EndUserDetailsService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

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
    public void login(@RequestBody EndUserDto endUserDto){
        EndUser endUser = endUserDetailsService.getUserByUsername(endUserDto.getUsername());
        if(endUser != null){
        //TODO
        }
        else {
            throw new UsernameNotFoundException("This user doesn't exist!");
        }
    }

    @PutMapping("/admin/users?username={username}&active={active}")
    @Secured("ROLE_ADMIN")
    public void changeUserStatues(@PathVariable String username , @PathVariable boolean active){
        endUserDetailsService.enableUser(username ,active);
    }
    @Secured("ROLE_USER")
    @PostMapping("/user/api-tokens")
    public ResponseEntity<String> createToken(@AuthenticationPrincipal EndUser endUser, @RequestBody String name, @RequestBody Date date,@RequestHeader String key) {
        if(endUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("nobody logged in!");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(endUserDetailsService.generateToken(name, date, endUser, key));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("the expiration date is in the past!");
        }
    }

    @GetMapping("/user/api-tokens")
    @Secured("ROLE_USER")
    public ResponseEntity<String> tokensList(@AuthenticationPrincipal EndUser endUser){
        if(endUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("nobody logged in!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(endUserDetailsService.getAllTokens(endUser));
    }

    @DeleteMapping("/user/api-tokens")
    @Secured("ROLE_USER")
    public void removeToken(@AuthenticationPrincipal EndUser endUser){

    }
}
