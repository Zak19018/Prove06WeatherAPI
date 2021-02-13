package com.example.prove06_weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> forecastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getTemp(View view) {
        EditText cityInput = findViewById(R.id.cityName);
        String city = cityInput.getText().toString();

        Log.d("Main Activity", "Getting weather for " + city);

        GetTemperature getTemp = new GetTemperature(this, city);

        Thread background = new Thread(getTemp);

        background.start();
    }

    public void getForecast(View view) {
        EditText cityInput = findViewById(R.id.cityName);
        String city = cityInput.getText().toString();

        Log.d("Main Activity", "Getting forecast for " + city);

        GetForecast getForecast = new GetForecast(this, city);

        Thread forecastThread = new Thread(getForecast);

        forecastThread.start();
    }

    public void handleResult(WeatherConditions conditions) {
        Log.d("MainActivity", "Conditions: " + conditions.getMeasurements().toString());

        Float temp = conditions.getMeasurements().get("temp");

        Toast.makeText(this, "It is currently " + temp + "°C.",
                    Toast.LENGTH_LONG).show();
    }

    public void handleForecastResult(WeatherForecast forecast) {
        Log.d("MainActivity", "Forecast: " + forecast.getWeatherForecasts());

        ListView listView = (ListView) findViewById(R.id.forecastList);
        TextView textView = (TextView) findViewById(R.id.textView);
        forecastList = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView, forecastList);
        listView.setAdapter(adapter);

        for (WeatherForecastItem item : forecast.getWeatherForecasts()) {
            forecastList.add("Time: "+item.getTime());
            forecastList.add("Temp: "+item.getMeasurements().get("temp")+"°C");
            for (Weather details : item.getWeather()) {
                forecastList.add("Description: "+details.description);
            }
            forecastList.add("Wind Speed: "+item.getWindSpeed().get("speed")+"m/s\n");
            forecastList.add("");
        }
    }
}
