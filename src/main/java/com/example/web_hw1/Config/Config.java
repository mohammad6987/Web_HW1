package com.example.web_hw1.Config;

import com.example.web_hw1.Controller.CountriesController;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CountriesController countriesController(RestTemplate restTemplate) {
        return new CountriesController(restTemplate);
    }
//
//    @Bean
//    WebClient webclient() {
//        return new WebClient()
//    }
}
