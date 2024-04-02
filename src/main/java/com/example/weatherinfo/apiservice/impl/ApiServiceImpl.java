package com.example.weatherinfo.apiservice.impl;

import com.example.weatherinfo.apiservice.ApiRequest;
import com.example.weatherinfo.apiservice.ApiService;
import com.example.weatherinfo.apiservice.response.weatherinfo.GeoCodingResponse;
import com.example.weatherinfo.apiservice.response.weatherinfo.WeatherDataResponse;
import com.example.weatherinfo.config.GlobalConfiguration;
import com.example.weatherinfo.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Component
public class ApiServiceImpl implements ApiService {

    @Autowired
    private GlobalConfiguration globalConfig;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(ApiServiceImpl.class);


    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseEntity<String> exchangeGet(String url, HttpHeaders headers) {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        } catch (Exception e) {
            LOG.info("exchangeGet:" + e.getMessage() + "url::" + url);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public GeoCodingResponse getPincodeGeoCode(String pincode, String countryCode) {
        String url = String.format(globalConfig.getOpenWeather().getBaseUrl() + ApiRequest.GEOCODING_API, pincode,
                countryCode, globalConfig.getOpenWeather().getAppId());

        ResponseEntity<String> response = exchangeGet(url, getHeaders());
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            try {
                GeoCodingResponse resp = ObjectMapperUtil.toObject(response.getBody(), GeoCodingResponse.class);
                return resp;
            } catch (IOException e) {
                LOG.error("getPincodeGeoCodeError", e);
            }
        }
        return null;
    }


    public WeatherDataResponse getWeatherInfo(Double latitude, Double longitude) {
        String url = String.format(globalConfig.getOpenWeather().getBaseUrl() + ApiRequest.WEATHER_API, latitude,
                longitude, globalConfig.getOpenWeather().getAppId());

        ResponseEntity<String> response = exchangeGet(url, getHeaders());
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            try {
                WeatherDataResponse resp = ObjectMapperUtil.toObject(response.getBody(), WeatherDataResponse.class);
                return resp;
            } catch (IOException e) {
                LOG.error("getWeatherInfoError", e);
            }
        }
        return null;
    }
}
