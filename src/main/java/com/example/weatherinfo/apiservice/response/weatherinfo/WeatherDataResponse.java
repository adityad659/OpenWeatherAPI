package com.example.weatherinfo.apiservice.response.weatherinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataResponse {

	@JsonProperty("weather")
	private List<WeatherResponse> weatherList;

	@JsonProperty("main")
	private TemperatureResponse temperature;

	public List<WeatherResponse> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(List<WeatherResponse> weatherList) {
		this.weatherList = weatherList;
	}

	public TemperatureResponse getTemperature() {
		return temperature;
	}

	public void setTemperature(TemperatureResponse temperature) {
		this.temperature = temperature;
	}
}
