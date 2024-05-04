package com.example.web_hw1.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EndUserDto {
    private Long id;
    private String username;
    private String password;
}
