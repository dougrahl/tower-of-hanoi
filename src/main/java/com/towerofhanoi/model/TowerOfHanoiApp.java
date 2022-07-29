package com.towerofhanoi.model;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class TowerOfHanoiApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/towerOfHanoi.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setTitle("Tower of Hanoi");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/tower-pixel-icon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);

                Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
                exitAlert.setTitle("Confirm Exit");
                exitAlert.setHeaderText("Are you sure you want to exit?");
                exitAlert.getButtonTypes().setAll(exit, ButtonType.CANCEL);
                Optional<ButtonType> result = exitAlert.showAndWait();

                if (!result.isPresent()) {
                    exitAlert.close();
                } else if (result.get() == exit) {
                    System.exit(0);
                } else if (result.get() == ButtonType.CANCEL) {
                    windowEvent.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
