package com.example.web_hw1.Service;

import com.example.web_hw1.Model.Country;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.util.Arrays;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List getCountries() {
        String theURI = "https://api-ninjas.com/api/country" ;
        Country[] listOfCountries = restTemplate.getForObject(theURI, Country[].class);
        return (List) Arrays.asList(listOfCountries);
    }
}
