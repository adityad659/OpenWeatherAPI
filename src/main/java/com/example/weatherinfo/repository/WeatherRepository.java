package com.example.weatherinfo.repository;

import com.example.weatherinfo.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {


    @Query("select w from WeatherInfo w where w.pincode = ?1 and w.countryCode = ?2 and date = ?3")
    Optional<WeatherInfo> getWeatherInfo(String pincode, String countryCode, LocalDate date);
}
