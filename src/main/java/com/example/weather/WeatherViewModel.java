package com.example.weather;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


public class WeatherViewModel {

    private ObjectProperty<WeatherData> weatherData = new SimpleObjectProperty<>();
    private StringProperty tempInfo = new SimpleStringProperty();
    private StringProperty tempFeels = new SimpleStringProperty();
    private StringProperty tempMax = new SimpleStringProperty();
    private StringProperty tempMin = new SimpleStringProperty();
    private StringProperty pressure = new SimpleStringProperty();
    private StringProperty windSpeed = new SimpleStringProperty();
    private StringProperty windDirection = new SimpleStringProperty();
    private StringProperty humidity = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    private OpenWeatherMapClient weatherClient = new OpenWeatherMapClient();

    public WeatherData getWeatherData(String city) {
        WeatherData data = weatherClient.getWeatherData(city);

        // Update the properties with the new weather data on the JavaFX Application Thread
        Platform.runLater(() -> {
            tempInfo.set("Температура: " + data.getTemperature() + "°C");
            tempFeels.set("Ощущается: "+ data.getFeelsLikeTemperature() + "°C");
            tempMax.set("Максимум: " + data.getMaxTemperature() + "°C");
            tempMin.set("Минимум: " + data.getMinTemperature() + "°C");
            pressure.set("Давление: " + data.getPressure() + " мм рт. ст.");
            windSpeed.set("Скорость ветра: " + data.getWindSpeed() + " м/с");
            windDirection.set("Направление: " + data.getWindDirection() + "°");
            humidity.set("Влажность: " + data.getHumidity()+ "%");
            description.set("Описание: " + data.getDescription());
        });

        // Return the weather data
        return data;
    }

    public ObjectProperty<WeatherData> weatherDataProperty() {
        return weatherData;
    }

    public WeatherData getWeatherData() {
        return weatherData.get();
    }

    public void setWeatherData(WeatherData data) {
        weatherData.set(data);
    }

    public StringProperty tempInfoProperty() {
        return tempInfo;
    }

    public StringProperty tempFeelsProperty() {
        return tempFeels;
    }

    public StringProperty tempMaxProperty() {
        return tempMax;
    }

    public StringProperty tempMinProperty() {
        return tempMin;
    }

    public StringProperty pressureProperty() {
        return pressure;
    }

    public StringProperty windSpeedProperty() {
        return windSpeed;
    }

    public StringProperty windDirectionProperty() {
        return windDirection;
    }

    public StringProperty humidityProperty() {
        return humidity;
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}

