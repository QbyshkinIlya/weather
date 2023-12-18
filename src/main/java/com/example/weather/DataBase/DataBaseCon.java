package com.example.weather.DataBase;

import com.example.weather.WeatherData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseCon {

    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;

    public DataBaseCon() {
        this.connection = getConnection();
        createTables(); // Вызываем метод создания таблиц при создании экземпляра класса
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error during database connection: " + e.getMessage());
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    public void createTables() {
        // SQL-запрос для создания таблицы городов с датой запроса
        String createCityTableQuery = "CREATE TABLE IF NOT EXISTS city (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "date DATE" +
                ")";
        // SQL-запрос для создания таблицы с информацией о погоде
        String createWeatherTableQuery = "CREATE TABLE IF NOT EXISTS weather_data (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "city VARCHAR(255)," +
                "temperature DOUBLE," +
                "feels_like DOUBLE," +
                "max_temperature DOUBLE," +
                "min_temperature DOUBLE," +
                "pressure DOUBLE," +
                "wind_speed DOUBLE," +
                "wind_direction DOUBLE," +
                "humidity DOUBLE," +
                "description VARCHAR(255)," +
                "date DATE" +
                ")";

        try (PreparedStatement cityStatement = connection.prepareStatement(createCityTableQuery);
             PreparedStatement weatherStatement = connection.prepareStatement(createWeatherTableQuery)) {

            // Выполняем запросы на создание таблиц
            cityStatement.executeUpdate();
            System.out.println("Table city create!");
            weatherStatement.executeUpdate();
            System.out.println("Table weather_data create!");

        } catch (SQLException e) {
            System.err.println("Error during table creation: " + e.getMessage());
        }
    }

    public void insertCityData(String city, String date) {
        // Метод для заполнения таблицы City
        String query = "INSERT INTO city (name, date) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, date);

            // Выполняем запрос
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Обработка ошибок базы данных
            System.err.println("Error during city data insertion: " + e.getMessage());
        }
    }

    public void insertWeatherData(String city, WeatherData weatherData) {
        // Метод для заполнения таблицы Weather
        String query = "INSERT INTO weather_data (city, temperature, feels_like, max_temperature, min_temperature, "
                + "pressure, wind_speed, wind_direction, humidity, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, city);
            preparedStatement.setDouble(2, weatherData.getTemperature());
            preparedStatement.setDouble(3, weatherData.getFeelsLikeTemperature());
            preparedStatement.setDouble(4, weatherData.getMaxTemperature());
            preparedStatement.setDouble(5, weatherData.getMinTemperature());
            preparedStatement.setDouble(6, weatherData.getPressure());
            preparedStatement.setDouble(7, weatherData.getWindSpeed());
            preparedStatement.setDouble(8, weatherData.getWindDirection());
            preparedStatement.setDouble(9, weatherData.getHumidity());
            preparedStatement.setString(10, weatherData.getDescription());
            // Выполняем запрос
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Обработка ошибок базы данных
            System.err.println("Error during weather data insertion: " + e.getMessage());
        }
    }
}
