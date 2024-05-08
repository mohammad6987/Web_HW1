package com.example.web_hw1.Model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//@Entity
public class Country {
    private String iso2;
    private String iso3;
    private String country;
    private String[] cities;

    public Country() {}
}
