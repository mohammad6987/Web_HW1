package com.example.web_hw1.Controller;


import com.example.web_hw1.Service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/countries")
@RestController
public class CountriesController {

    private final WeatherService weatherService;

    public CountriesController (WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping()
    public List getCountries() {
        return (List) weatherService.getCountries();
    }
}
