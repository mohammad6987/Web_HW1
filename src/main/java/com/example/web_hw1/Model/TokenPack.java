package com.example.web_hw1.Model;

import com.example.web_hw1.JWTUtils.TokenManger;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity(name = "Tokens")
@NoArgsConstructor
public class TokenPack {
    @Id
    private String name;
    private String username;
    private String tokenValue;
    private Date expireDate;

    public TokenPack(String name, String username, Date expireDate) {
        this.name = name;
        this.username = username;
        this.expireDate = expireDate;
        this.tokenValue = TokenManger.generateToken(username,expireDate);

    }
}
