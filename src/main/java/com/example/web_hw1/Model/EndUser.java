package com.example.web_hw1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "Users")
@Data
public class EndUser {
    @Id
    long id;
}
