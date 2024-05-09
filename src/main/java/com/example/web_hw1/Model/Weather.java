package com.example.web_hw1.Model;

import lombok.Data;

@Data
public class Weather {
    private int cloud_pct;
    private int temp;
    private int feels_like;
    private int humidity;
    private int min_temp;
    private int max_temp;
    private double wind_speed;
    private int wind_degrees;
    private long sunrise;
    private long sunset;
}
