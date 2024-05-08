package com.example.web_hw1.Model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CountryContainer {
    private ArrayList<CountryDto> countries;
    private int count;

    public CountryContainer(ArrayList<CountryDto> countries, int count) {
        this.countries = countries;
        this.count = count;
    }
}
