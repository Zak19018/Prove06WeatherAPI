package com.example.prove06_weatherapi;

public class WeatherForecast {
    private WeatherForecastItem[] list; //list works because it is the name of the key in JSON file
    //other variable names will not work

    public WeatherForecastItem[] getWeatherForecasts() {

        return list;
    }
}
