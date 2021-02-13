package com.example.prove06_weatherapi;

import android.util.Log;

public class GetTemperature implements Runnable {

    private MainActivity activity;
    private String city;

    public GetTemperature(MainActivity activity, String city) {
        this.activity = activity;
        this.city = city;
    }

    @Override
    public void run() {
        // This function gets API data in the background
        GetApiData data = new GetApiData();

        final WeatherConditions conditions = data.getWeatherAndPostResults(city);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.handleResult(conditions);
            }
        });
    }
}
