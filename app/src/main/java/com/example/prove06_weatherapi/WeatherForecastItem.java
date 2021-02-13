package com.example.prove06_weatherapi;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class WeatherForecastItem {
    Weather[] weather;
    @SerializedName("main")
    Map<String, Float> measurements;
    @SerializedName("dt_txt")
    String time;
    @SerializedName("wind")
    Map<String, Float> windSpeed;

    public Map<String, Float> getMeasurements() {return measurements;}


    public String getTime() {return time;}


    public Weather[] getWeather() {
        return weather;
    }


    public Map<String, Float> getWindSpeed() {return windSpeed;}

}
