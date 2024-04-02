package com.example.weatherinfo.dto.response;

import com.example.weatherinfo.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfoResponse  extends BaseDTO {

    @JsonProperty("temperature")
    private String temperature;

    @JsonProperty("description")
    private String description;

    public String getTemperature() {
        return temperature + " Celsius";
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
