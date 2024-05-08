package com.example.web_hw1.Controller;


import com.example.web_hw1.Model.*;
import com.example.web_hw1.Service.WeatherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CountriesController {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers = new HttpHeaders();
    private final String API_KEY = "LIPQv0O9lPlFFgxNslVs5g==sQ6DOXmXmTdOZPCm";

    public CountriesController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    @GetMapping("/countries")
    public CountryContainer getCountry() {
        datatype datatype = restTemplate.getForEntity("https://countriesnow.space/api/v0.1/countries", datatype.class).getBody();
        Country[] listofCountry = datatype.getData();
        ArrayList<CountryDto> countries = new ArrayList<CountryDto>();
        for (Country country : listofCountry) {
            countries.add(new CountryDto(country.getCountry()));
        }
        return new CountryContainer(countries,countries.size());
    }

    @GetMapping("/countries/{name}")
    public Country[] getCountryByName(@PathVariable String name) {
        String url = "https://api.api-ninjas.com/v1/country?name=" + name;
//        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<> response
        //        headers.set("x-api-key", API_KEY);
////        listOfProperty listOfTwoDataTypes = restTemplate.getForEntity(url, listOfProperty.class).getBody();
////        return listOfTwoDataTypes;
//        Country[] listOfCountry = restTemplate.getForEntity(url, Country[].class).getBody();
//        return listOfCountry;
    }
}
