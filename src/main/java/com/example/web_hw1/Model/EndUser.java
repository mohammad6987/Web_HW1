package com.example.web_hw1.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "Users")
@Data
public class EndUser {
    @Id
    private long id;
    @Column(name = "username" , nullable = false)
    private String username;
    @Column(name = "password" , nullable = false)
    private String password;
    @Column(name = "auth" , nullable = false)
    private boolean authorized;
}
