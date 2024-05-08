package com.example.web_hw1.Model;

import lombok.Data;

@Data
public class CountryDto {
    private String name;

    public CountryDto(String name) {
        this.name = name;
    }
}
