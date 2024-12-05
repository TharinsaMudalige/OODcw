module com.example.oodcw {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.sql;
    requires java.desktop;


    opens com.example.oodcw to javafx.fxml;
    exports com.example.oodcw;
    exports com.example.oodcw.Controllers;
    opens com.example.oodcw.Controllers to javafx.fxml;
}