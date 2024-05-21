package com.example.web_hw1.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonMixinModule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CountriesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonMixinModule jsonMixinModule;

    @Test
    void getCountry() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.count").value(227))
                .andExpect(jsonPath("$.countries[0].name").value("Afghanistan"))
                .andExpect(jsonPath("$.countries[1].name").value("Albania"))
                .andExpect(jsonPath("$.countries[2].name").value("Algeria"))
                .andExpect(jsonPath("$.countries[226].name").value("Zimbabwe"));
    }

    @Test
    void getCountrySomeOtherCountries() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.count").value(227))
                .andExpect(jsonPath("$.countries[3].name").value("Andorra"))
                .andExpect(jsonPath("$.countries[126].name").value("Macau"))
                .andExpect(jsonPath("$.countries[162].name").value("Papua New Guinea"))
                .andExpect(jsonPath("$.countries[186].name").value("Sierra Leone"));
    }

    @Test
    void getCountryByNameIran() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/Iran"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Iran, Islamic Republic Of"))
                .andExpect(jsonPath("$.capital").value("Tehran"))
                .andExpect(jsonPath("iso2").value("IR"));
    }

    @Test
    void getCountryBynameUK() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/United"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("United Kingdom"))
                .andExpect(jsonPath("$.capital").value("London"))
                .andExpect(jsonPath("iso2").value("GB"));
    }

    @Test
    void getCountryBynameFrance() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/france"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("France"))
                .andExpect(jsonPath("$.capital").value("Paris"))
                .andExpect(jsonPath("iso2").value("FR"))
                .andExpect(jsonPath("$.population").value(65274));
    }

    @Test
    void getCountryBynameRussia() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/Russia"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Russian Federation"))
                .andExpect(jsonPath("$.capital").value("Moscow"))
                .andExpect(jsonPath("iso2").value("RU"));
    }

    @Test
    void getCountryWeatherIran() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/Iran/weather"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.country_name").value("Iran, Islamic Republic Of"))
                .andExpect(jsonPath("$.capital").value("Tehran"));
//                .andExpect(jsonPath("wind_speed").value(4.12))
//                .andExpect(jsonPath("temp").value(28))
//                .andExpect(jsonPath("$.wind_degrees").value(200))
//                .andExpect(jsonPath("$.humidity").value(20));
    }

    @Test
    void getCountryWeatherChina() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/china/weather"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.country_name").value("China"))
                .andExpect(jsonPath("$.capital").value("Beijing"));
//                .andExpect(jsonPath("wind_speed").value(3.78))
//                .andExpect(jsonPath("temp").value(27))
//                .andExpect(jsonPath("$.wind_degrees").value(180))
//                .andExpect(jsonPath("$.humidity").value(26));
    }

    @Test
    void getCountryWeatherUSA() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/usa/weather"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.country_name").value(	"United States"))
                .andExpect(jsonPath("$.capital").value("Washington, D.C."));
    }

    @Test
    void getCountryWeatherArgentina() throws Exception {
        mockMvc.perform(get("http://localhost:8080/countries/argentina/weather"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.country_name").value(	"Argentina"))
                .andExpect(jsonPath("$.capital").value(	"Buenos Aires"));
//                .andExpect(jsonPath("wind_speed").value(3.6))
//                .andExpect(jsonPath("temp").value(12))
//                .andExpect(jsonPath("$.wind_degrees").value(360))
//                .andExpect(jsonPath("$.humidity").value(88));
    }
}