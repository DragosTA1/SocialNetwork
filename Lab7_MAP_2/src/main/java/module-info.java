module com.example.lab7_map {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.lab7_map_2 to javafx.fxml;
    opens com.example.lab7_map_2.Controller to javafx.fxml;
    exports com.example.lab7_map_2;
    exports com.example.lab7_map_2.Controller;
    opens com.example.lab7_map_2.Domain to javafx.base;
}