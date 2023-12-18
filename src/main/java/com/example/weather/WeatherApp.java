package com.example.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WeatherApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/weather/hello-view.fxml"));
            WeatherController weatherController = new WeatherController();
            fxmlLoader.setController(weatherController);

            Parent root = fxmlLoader.load();
            weatherController.setViewModel(new WeatherViewModel());

            Scene scene = new Scene(root, 400, 300);

            primaryStage.setTitle("Weather App");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // or use a logger
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
