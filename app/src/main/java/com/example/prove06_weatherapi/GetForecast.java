package com.example.prove06_weatherapi;

import android.util.Log;

public class GetForecast implements Runnable {

    private MainActivity activity;
    private String city;

    public GetForecast(MainActivity activity, String city) {
        this.activity = activity;
        this.city = city;
    }

    @Override
    public void run() {
        GetApiData data = new GetApiData();

        final WeatherForecast forecast = data.getForecastAndPostResults(city);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.handleForecastResult(forecast);
            }
    });
  }
}
