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
    private double gdp;
    private double sex_ratio;
    private double surface_area;
    private double life_expectancy_male;
    private double unemployment;
    private double imports;
    private double homicide_rate;
    private Currency currency;
    private double employment_services;
    private double employment_industry;
    private double urban_population_growth;
    private double secondary_school_enrollment_female;
    private double employment_agriculture;
    private String capital;
    private double co2_emissions;
    private double forested_area;
    private double tourists;
    private double exports;
    private double life_expectancy_female;
    private double post_secondary_enrollment_female;
    private double post_secondary_enrollment_male;
    private double primary_school_enrollment_female;
    private double infant_mortality;
    private double gdp_growth;
    private double threatened_species;
    private double population;
    private double urban_population;
    private double secondary_school_enrollment_male;
    private String name;
    private double pop_growth;
    private String region;
    private double pop_density;
    private double internet_users;
    private double gdp_per_capita;
    private double fertility;
    private double refugees;
    private double primary_school_enrollment_male;

    public Country() {}
}
