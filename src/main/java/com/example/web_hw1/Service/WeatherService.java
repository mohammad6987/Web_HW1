package com.example.web_hw1.Service;

import com.example.web_hw1.Model.*;
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

    public WeatherDto CountryWether(String name) {
        String capitalName = "null";
        String url1 = "https://api.api-ninjas.com/v1/country?name=" + name;
        HttpHeaders headers1 = new HttpHeaders();
        headers1.set("x-api-key", "LIPQv0O9lPlFFgxNslVs5g==sQ6DOXmXmTdOZPCm");
        HttpEntity<Void> request1 = new HttpEntity<>(headers1);
        ResponseEntity<Country[]> responseEntity1 = restTemplate.exchange(
                url1, HttpMethod.GET, request1, Country[].class
        );
        for (Country country : responseEntity1.getBody()) {
            if (country.getName().contains(name)) {
                capitalName = country.getCapital();
            }
        }
        WeatherDto finalWeather = new WeatherDto();

        String url = "https://api.api-ninjas.com/v1/weather?city=" + capitalName;
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", "LIPQv0O9lPlFFgxNslVs5g==sQ6DOXmXmTdOZPCm");
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Weather> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, request, Weather.class
        );
        // this can be implemented by constructor (maybe in future)
        finalWeather.setCountry_name(name);
        finalWeather.setCapital(capitalName);
        finalWeather.setHumidity(responseEntity.getBody().getHumidity());
        finalWeather.setTemp(responseEntity.getBody().getTemp());
        finalWeather.setWind_speed(responseEntity.getBody().getWind_speed());
        finalWeather.setWind_degrees(responseEntity.getBody().getWind_degrees());
        return finalWeather;
    }
}
