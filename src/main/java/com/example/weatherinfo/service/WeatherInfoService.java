package com.example.weatherinfo.service;

import com.example.weatherinfo.dto.response.WeatherInfoResponse;

import java.time.LocalDate;
import java.util.Date;

public interface WeatherInfoService {
    WeatherInfoResponse getWeatherInfo(String pincode, LocalDate date);
}
