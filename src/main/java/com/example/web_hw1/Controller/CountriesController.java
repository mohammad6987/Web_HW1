package com.example.web_hw1.Controller;


import com.example.web_hw1.Model.Country;
import com.example.web_hw1.Model.CountryContainer;
import com.example.web_hw1.Model.CountryDto;
import com.example.web_hw1.Model.datatype;
import com.example.web_hw1.Service.WeatherService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CountriesController {

//    private RestTemplate restTemplate;
//    private final org.springframework.http.HttpHeaders headers = new HttpHeaders();
//
//    public CountriesController(RestTemplate restTemplate){
//        this.restTemplate = restTemplate;
//        headers.setContentType(MediaType.APPLICATION_JSON);
////    }
//
//    private final WebClient webClient;
//
    private final RestTemplate restTemplate;
    public CountriesController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
//        this.webClient = builder.baseUrl("https://countriesnow.space/api/v0.1").build();

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
}
