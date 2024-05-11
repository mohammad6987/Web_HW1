package com.example.web_hw1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity(name = "Tokens")
@NoArgsConstructor
public class TokenPack {
    @Id
    private String name;
    private String ownerUsername;
    private String tokenValue;
    private Date expireDate;




}
