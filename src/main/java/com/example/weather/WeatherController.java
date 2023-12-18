package com.example.weather;

import com.example.weather.DataBase.DataBaseCon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;


public class WeatherController {

    @FXML
    private TextField cityField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button getDataButton;

    @FXML
    private Label tempInfoLabel;

    @FXML
    private Label tempFeelsLabel;

    @FXML
    private Label tempMaxLabel;

    @FXML
    private Label tempMinLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private Label windDirectionLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label descriptionLabel;

    private WeatherViewModel viewModel;

    @FXML
    private void initialize() {
        getDataButton.setOnAction(event -> getData());
    }

    @FXML
    public void getData() {
        try {
            String city = cityField.getText();
            WeatherData weatherData = viewModel.getWeatherData(city);

            // Check if weather data is not null before updating the labels
            if (weatherData != null) {
                viewModel.tempInfoProperty().set("Температура: " + weatherData.getTemperature() + "°C");
                viewModel.tempFeelsProperty().set("Ощущается: " + weatherData.getFeelsLikeTemperature() + "°C");
                viewModel.tempMaxProperty().set("Максимум: " + weatherData.getMaxTemperature() + "°C");
                viewModel.tempMinProperty().set("Минимум: " + weatherData.getMinTemperature() + "°C");
                viewModel.pressureProperty().set("Давление: " + weatherData.getPressure() + " мм рт. ст.");
                viewModel.windSpeedProperty().set("Скорость ветра: " + weatherData.getWindSpeed() + " м/с");
                viewModel.windDirectionProperty().set("Направление ветра: " + weatherData.getWindDirection() + "°");
                viewModel.humidityProperty().set("Влажность: " + weatherData.getHumidity() + "%");
                viewModel.descriptionProperty().set("Описание: "+ weatherData.getDescription());

                // Получаем дату из DatePicker
                String date = datePicker.getValue().toString();

                // Сохранение данных в базу данных
                DataBaseCon databaseCon = new DataBaseCon();
                databaseCon.insertWeatherData(city, weatherData);
                databaseCon.insertCityData(city, date);
                databaseCon.closeConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось получить данные", "Проверьте подключение к интернету и попробуйте еще раз.");
        }
    }


    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    public Parent getRoot() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/weather/hello-view.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();

            if (root == null) {
                throw new RuntimeException("FXML file is not loaded");
            }

            setViewModel(new WeatherViewModel()); // Создаем экземпляр WeatherViewModel
            return root;
        } catch (IOException e) {
            e.printStackTrace(); // или использование логгера
            return null;
        }
    }


    public void setViewModel(WeatherViewModel viewModel) {
        this.viewModel = viewModel;

        // Привязка свойств визуальных элементов к свойствам модели
        tempInfoLabel.textProperty().bind(viewModel.tempInfoProperty());
        tempFeelsLabel.textProperty().bind(viewModel.tempFeelsProperty());
        tempMaxLabel.textProperty().bind(viewModel.tempMaxProperty());
        tempMinLabel.textProperty().bind(viewModel.tempMinProperty());
        pressureLabel.textProperty().bind(viewModel.pressureProperty());
        windSpeedLabel.textProperty().bind(viewModel.windSpeedProperty());
        windDirectionLabel.textProperty().bind(viewModel.windDirectionProperty());
        humidityLabel.textProperty().bind(viewModel.humidityProperty());
        descriptionLabel.textProperty().bind(viewModel.descriptionProperty());
    }
}
