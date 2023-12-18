module com.example.weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires okhttp3;

    opens com.example.weather to javafx.fxml, javafx.base;
    exports com.example.weather;
}