package com.example.web_hw1.Controller;


import com.example.web_hw1.Model.Country;
import com.example.web_hw1.Service.WeatherService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class CountriesController {

    private RestTemplate restTemplate;
    private final org.springframework.http.HttpHeaders headers = new HttpHeaders();

    public CountriesController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @GetMapping("/countries")
    public Country[] getCountr() {
        return restTemplate.getForEntity("https://countriesnow.space/api/v0.1/countries", Country[].class).getBody();
    }
}
