package com.example.web_hw1.Controller;

import com.example.web_hw1.Model.EndUser;
import com.example.web_hw1.Model.EndUserDto;
import com.example.web_hw1.Model.TokenPack;
import com.example.web_hw1.Service.EndUserDetailsService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public void login(@RequestBody String username , @RequestBody String password){
        EndUser endUser = endUserDetailsService.getUserByUsername(username);
        if(endUser != null){

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
    public void createToken(@AuthenticationPrincipal EndUser endUser) {
    }

    @GetMapping("/user/api-tokens")
    @Secured("ROLE_USER")
    public Collection<TokenPack> tokensList(@AuthenticationPrincipal EndUser endUser){
        if(endUser == null){
            throw new UsernameNotFoundException("nobody has logged in!");
        }
        return null;
    }

    @DeleteMapping("/user/api-tokens")
    @Secured("ROLE_USER")
    public void removeToken(@AuthenticationPrincipal EndUser endUser){

    }
}
