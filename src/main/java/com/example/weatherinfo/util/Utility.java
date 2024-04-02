package com.example.weatherinfo.util;

public class Utility {

    public static Float convertKelvinToCelsius(Float kelvin) {
        if(kelvin == null) {
            return 0F;
        }
        return kelvin  - 273.15F;
    }
}
