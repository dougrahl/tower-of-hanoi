package com.towerofhanoi.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HowToPlayController {
    @FXML
    private Button closeButton;

    @FXML
    public void closeEvent(ActionEvent event) {
        Stage currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();
    }
}
