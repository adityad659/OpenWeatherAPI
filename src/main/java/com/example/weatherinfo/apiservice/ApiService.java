package com.example.weatherinfo.apiservice;

import com.example.weatherinfo.apiservice.response.weatherinfo.GeoCodingResponse;
import com.example.weatherinfo.apiservice.response.weatherinfo.WeatherDataResponse;

public interface ApiService {

    GeoCodingResponse getPincodeGeoCode(String pincode, String countryCode);

    WeatherDataResponse getWeatherInfo(Double latitude, Double longitude);

}
