package com.example.web_hw1.Service;

import com.example.web_hw1.Model.Country;
import com.example.web_hw1.Model.CountryDtoForSearch;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public CountryDtoForSearch findByName(String name) {
        String url = "https://api.api-ninjas.com/v1/country?name=" + name;
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", "LIPQv0O9lPlFFgxNslVs5g==sQ6DOXmXmTdOZPCm");
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Country[]> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, request, Country[].class
        );
        CountryDtoForSearch result = new CountryDtoForSearch();
        for (Country country : responseEntity.getBody()) {
            result = new CountryDtoForSearch(country.getName(), country.getCapital(), country.getIso2(), country.getPopulation(), country.getPop_growth(), country.getCurrency());
        }
        return (result);
    }
}
