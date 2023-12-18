package com.example.weather;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherMapClient {

    private static final String API_KEY = "14f5d9e3862216a858f7f2c001882c9a";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    private final Gson gson = new Gson();

    public WeatherData getWeatherData(String city) {
        try {
            String apiUrl = API_URL + "?q=" + city + "&appid=" + API_KEY + "&lang=ru";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            System.out.println(apiUrl);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonObject json = gson.fromJson(response.toString(), JsonObject.class);

                double temperature = json.getAsJsonObject("main").get("temp").getAsDouble();
                double feelsLikeTemperature = json.getAsJsonObject("main").get("feels_like").getAsDouble();
                double maxTemperature = json.getAsJsonObject("main").get("temp_max").getAsDouble();
                double minTemperature = json.getAsJsonObject("main").get("temp_min").getAsDouble();
                double pressure = json.getAsJsonObject("main").get("pressure").getAsDouble();
                double windSpeed = json.getAsJsonObject("wind").get("speed").getAsDouble();
                int windDirection = json.getAsJsonObject("wind").get("deg").getAsInt();
                int humidity = json.getAsJsonObject("main").get("humidity").getAsInt();
                String description = json.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

                System.out.println("temp = " + Math.round(temperature - 273.15) + " description = " +  description + " feels_like = " + Math.round(feelsLikeTemperature - 273.15) + " max = " + Math.round(maxTemperature - 273.15) + " min = " + Math.round(minTemperature - 273.15) + " pressure = " + Math.round(pressure * 0.750062) + " wind = " + windSpeed + "m/s" + " direction = " + windDirection + "°" + " humidity = " + humidity + "%");
                return new WeatherData(temperature, feelsLikeTemperature, maxTemperature, minTemperature, pressure, windSpeed, windDirection, humidity, description);
            } else {
                throw new RuntimeException("HTTP request failed with code: " + responseCode);
            }
        } catch (Exception e) {
            // Заменил e.printStackTrace() на выбрасывание исключения с более понятным сообщением
            throw new RuntimeException("Failed to fetch weather data for city '" + city + "': " + e.getMessage(), e);
        }
    }
}
