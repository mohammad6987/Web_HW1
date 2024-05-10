package com.example.web_hw1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "Tokens")
public class TokenPack {
    @Id
    @ManyToOne
    private EndUser usernameOwner;

    private String tokenValue;
}
