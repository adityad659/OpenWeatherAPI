package com.example.weatherinfo.service.impl;

import com.example.weatherinfo.apiservice.ApiService;
import com.example.weatherinfo.apiservice.response.weatherinfo.GeoCodingResponse;
import com.example.weatherinfo.apiservice.response.weatherinfo.WeatherDataResponse;
import com.example.weatherinfo.constant.CountryCodeEnum;
import com.example.weatherinfo.dto.response.WeatherInfoResponse;
import com.example.weatherinfo.exception.ValidationException;
import com.example.weatherinfo.model.Pincode;
import com.example.weatherinfo.model.WeatherInfo;
import com.example.weatherinfo.repository.PincodeRepository;
import com.example.weatherinfo.repository.WeatherRepository;
import com.example.weatherinfo.service.WeatherInfoService;
import com.example.weatherinfo.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherInfoServiceImpl implements WeatherInfoService {


    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private ApiService apiService;

    @Override
    @Cacheable(value = "weatherInfoCache", key = "{#pincode, #date}")
    public WeatherInfoResponse getWeatherInfo(String pincode, LocalDate date) {
        Pincode pincodeModel = pincodeRepository.findByPincodeAndCountryCode(pincode, CountryCodeEnum.IN.name())
                .orElseGet(() -> createAndSavePincode(pincode));

        WeatherInfoResponse weatherInfoResponse = fetchAndSaveWeatherInfo(pincodeModel, date);
        return weatherInfoResponse;
    }

    private Pincode createAndSavePincode(String pincode) {
        GeoCodingResponse geoCodingResponse = apiService.getPincodeGeoCode(pincode, CountryCodeEnum.IN.name());
        if (geoCodingResponse == null) {
            throw new ValidationException("Invalid Pincode!");
        }
        Pincode pincodeModel = new Pincode(geoCodingResponse.getZip(), geoCodingResponse.getLat(),
                geoCodingResponse.getLon(), geoCodingResponse.getCountry());
        pincodeRepository.save(pincodeModel);
        return pincodeModel;
    }

    private WeatherInfoResponse fetchAndSaveWeatherInfo(Pincode pincodeModel, LocalDate date) {
        if (!LocalDate.now().isEqual(date)) {
            throw new ValidationException("No Weather Information for pincode: " + pincodeModel.getPincode() + " for date:" + date);
        }

        Optional<WeatherInfo> weatherInfoOptional = weatherRepository.getWeatherInfo(pincodeModel.getPincode(), pincodeModel.getCountryCode(), date);
        WeatherInfo weatherInfo = null;
        if(weatherInfoOptional.isPresent()) {
            weatherInfo = weatherInfoOptional.get();
        } else {
            WeatherDataResponse weatherDataResponse = apiService.getWeatherInfo(pincodeModel.getLatitude(), pincodeModel.getLongitude());
            if(weatherDataResponse == null) {
                throw new ValidationException("No Weather Information for pincode: " + pincodeModel.getPincode() + " for date:" + date);
            } else {
                weatherInfo = new WeatherInfo();
                weatherInfo.setPincode(pincodeModel.getPincode());
                weatherInfo.setDescription(weatherDataResponse.getWeatherList().get(0).getDescription());
                weatherInfo.setTemperature(Utility.convertKelvinToCelsius(weatherDataResponse.getTemperature().getTemp()));
                weatherInfo.setCountryCode(pincodeModel.getCountryCode());
                weatherInfo.setDate(date);
                weatherRepository.save(weatherInfo);
            }
        }

        WeatherInfoResponse weatherInfoResponse = new WeatherInfoResponse();
        weatherInfoResponse.setDescription(weatherInfo.getDescription());
        weatherInfoResponse.setTemperature(weatherInfo.getTemperature() + "");
        return weatherInfoResponse;
    }

}
