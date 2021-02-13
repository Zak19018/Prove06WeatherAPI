package com.example.prove06_weatherapi;

import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GetApiData {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String weather_url = "https://api.openweathermap.org/data/2.5/weather";
    private static final String forecast_url = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String key = "8e9c64fa57cc9617996fe2f6b39a853d";
    private static String charset;

    public GetApiData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        ) {
            charset = StandardCharsets.UTF_8.name();
        }
    }

    private String getWebResults(String url) throws IOException {
        Log.d("GetApiData", "Calling " + url);

        URLConnection connection = new URL(url).openConnection();

        InputStream response = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(response));

        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    private String getWeatherJson(String city) throws IOException {
        String query = String.format("%s?q=%s&apiKey=%s&units=metric",
                weather_url,
                URLEncoder.encode(city, charset),
                URLEncoder.encode(key, charset));

        return getWebResults(query);
    }

    private String getForecastJson(String city) throws IOException {
        String query = String.format("%s?q=%s&apiKey=%s&units=metric",
                forecast_url,
                URLEncoder.encode(city, charset),
                URLEncoder.encode(key, charset));

        String result = getWebResults(query);

        System.out.println(result);

        return result;
    }

    public WeatherConditions getWeather(String city) throws IOException {
        String result = getWeatherJson(city);

        Gson gson = new Gson();
        WeatherConditions conditions = gson.fromJson(result, WeatherConditions.class);

        return conditions;
    }

    public void getWeatherAndPostResults(String city, WeatherConditionsResultHandler handler) {
        try {
            WeatherConditions conditions = getWeather(city);
            handler.handleResult(conditions);
        } catch (IOException e) {
            e.printStackTrace();
            handler.handleResult(null);
        }
    }


    public WeatherForecast getForecast(String city) throws IOException {
        String results = getForecastJson(city);

        Gson gson = new Gson();
        WeatherForecast forecast = gson.fromJson(results, WeatherForecast.class);

        return forecast;
    }


    public void getForecastAndPostResults(String city, WeatherForecastResultHandler handler) {
        try {
            WeatherForecast forecast = getForecast(city);
            handler.handleForecastResult(forecast);
        } catch (IOException e) {
            e.printStackTrace();
            handler.handleForecastResult(null);
        }
    }

    public WeatherConditions getWeatherAndPostResults(String city) {
        try {
            WeatherConditions conditions = getWeather(city);
            return conditions;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WeatherForecast getForecastAndPostResults(String city) {
        try {
            WeatherForecast forecast = getForecast(city);
            return forecast;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
