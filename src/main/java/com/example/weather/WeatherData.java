package com.example.weather;

public class WeatherData {

    private double temperature;
    private double feelsLikeTemperature;
    private double maxTemperature;
    private double minTemperature;
    private double pressure;
    private double windSpeed;
    private int windDirection;
    private int humidity;
    private String description;

    public WeatherData(double temperature, double feelsLikeTemperature, double maxTemperature, double minTemperature, double pressure, double windSpeed, int windDirection, int humidity,String description) {
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.humidity = humidity;
        this.description = description;
    }

    public double getTemperature() {
        return Math.ceil((temperature - 273.15) * 10) / 10;
    }

    public double getFeelsLikeTemperature() {
        return Math.ceil((feelsLikeTemperature - 273.15) * 10) / 10;
    }

    public double getMaxTemperature() {
        return Math.ceil((maxTemperature - 273.15) * 10) / 10;
    }

    public double getMinTemperature() {
        return Math.ceil((minTemperature - 273.15) * 10) / 10;
    }

    public double getPressure() {
        return Math.ceil((pressure * 0.750062) * 10) / 10;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }
}

