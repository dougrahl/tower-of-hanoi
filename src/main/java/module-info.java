module com.example.towersofhanoi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.towerofhanoi to javafx.fxml;
    exports com.towerofhanoi;
    exports com.towerofhanoi.controller;
    opens com.towerofhanoi.controller to javafx.fxml;
    exports com.towerofhanoi.model;
    opens com.towerofhanoi.model to javafx.fxml;
    exports com.towerofhanoi.ui;
    opens com.towerofhanoi.ui to javafx.fxml;
}