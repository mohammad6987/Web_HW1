package com.example.web_hw1.Controller;


import com.example.web_hw1.Exception.UnAuthorizedAccess;
import com.example.web_hw1.Model.*;
import com.example.web_hw1.Service.WeatherService;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class CountriesController {

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;
//    private final HttpClient httpClient;
//    private final HttpHeaders headers;
    private final String API_KEY = "LIPQv0O9lPlFFgxNslVs5g==sQ6DOXmXmTdOZPCm";

    public CountriesController(RestTemplate restTemplate, WeatherService weatherService) {
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
//        this.httpClient = httpClient;
    }


    @GetMapping("/countries")
    public CountryContainer getCountry(@AuthenticationPrincipal EndUser endUser) throws UnAuthorizedAccess {
        if(endUser == null || !endUser.isAuthorized()){
            throw new UnAuthorizedAccess("this user hasn't been enables!\nwait until the admin authenticate this account!");
        }else{
        datatype datatype = restTemplate.getForEntity("https://countriesnow.space/api/v0.1/countries", datatype.class).getBody();
        Country[] listofCountry = datatype.getData();
        ArrayList<CountryDto> countries = new ArrayList<CountryDto>();
        for (Country country : listofCountry) {
            countries.add(new CountryDto(country.getCountry()));
        }
        return new CountryContainer(countries,countries.size());}
    }

    @GetMapping("/countries/{name}")
    public Optional<CountryDtoForSearch> getCountryByName(@AuthenticationPrincipal EndUser endUser,@PathVariable String name) throws UnAuthorizedAccess {
        if(endUser == null || !endUser.isAuthorized()){
            throw new UnAuthorizedAccess("this user hasn't been enables!\nwait until the admin authenticate this account!");
        }else{
        return Optional.ofNullable(weatherService.findByName(name));}
    }

    @GetMapping("/countries/{name}/weather")
    public WeatherDto getCountryWeather(@AuthenticationPrincipal EndUser endUser,@PathVariable String name) throws UnAuthorizedAccess {
        if(endUser == null || !endUser.isAuthorized()){
            throw new UnAuthorizedAccess("this user hasn't been enables!\nwait until the admin authenticate this account!");
        }else{
        return weatherService.CountryWether(name);}
    }
}
