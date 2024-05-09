package com.example.web_hw1.Model;

import lombok.Data;

@Data
public class CountryDtoForSearch {
    private String name;
    private String capital;
    private String iso2;
    private double population;
    private double pop_growth;
    private Currency currency;

    public CountryDtoForSearch() {}

    public CountryDtoForSearch(String name,
                               String capital,
                               String iso2,
                               double population,
                               double pop_growth,
                               Currency currency) {
        this.name = name;
        this.currency = currency;
        this.capital = capital;
        this.iso2 = iso2;
        this.population = population;
        this.pop_growth = pop_growth;
    }
}
