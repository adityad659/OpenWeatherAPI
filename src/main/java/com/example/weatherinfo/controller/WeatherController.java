package com.example.weatherinfo.controller;


import com.example.weatherinfo.dto.response.WeatherInfoResponse;
import com.example.weatherinfo.response.APIResponse;
import com.example.weatherinfo.service.WeatherInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping(value = "/weather")
public class WeatherController {

    @Autowired
    WeatherInfoService weatherInfoService;

    @GetMapping()
    @ApiOperation(value = "Get Weather Information", notes = "Provides weather information for a specific pincode and date.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WeatherInfoResponse.class),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    private APIResponse getWeatherInfo(@RequestParam(required = true, name = "pincode") String pincode,
                                       @RequestParam(required = true, name = "date") LocalDate date) {
        WeatherInfoResponse weatherInfoResponse = weatherInfoService.getWeatherInfo(pincode, date);
        return APIResponse.ok(weatherInfoResponse);
    }
}
