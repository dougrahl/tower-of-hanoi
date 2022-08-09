package com.towerofhanoi.ui;

import com.towerofhanoi.controller.GameController;
import com.towerofhanoi.controller.SolveController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class TowerOfHanoiController implements Initializable {
    @FXML
    private Pane loadPane;
    @FXML
    private Label diskNumLabel;
    @FXML
    private Label totalMovesNumLabel;
    @FXML
    private Label minMovesNumLabel;
    @FXML
    private Button addDiskButton;
    @FXML
    private Button removeDiskButton;
    @FXML
    private Button tower1RightButton;
    @FXML
    private Button tower1ToTower3Button;
    @FXML
    private Button tower2LeftButton;
    @FXML
    private Button tower2RightButton;
    @FXML
    private Button tower3LeftButton;
    @FXML
    private Button tower3ToTower1Button;
    @FXML
    private Button restartButton;
    @FXML
    private Button solveButton;
    @FXML
    private VBox tower1VBox;
    @FXML
    private VBox tower2VBox;
    @FXML
    private VBox tower3VBox;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem howToPlayMenuItem;

    // Constant values
    private final int numTowers = 3;
    private int minNumDisks = 3;
    private int maxNumDisks = 8;

    private UIController uiController;
    private GameController gameController;

    private Alert cancelSolveAlert;

    private Thread solver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<VBox> towerVBoxes = new ArrayList<>();
        towerVBoxes.add(tower1VBox);
        towerVBoxes.add(tower2VBox);
        towerVBoxes.add(tower3VBox);

        this.uiController = new UIController();
        this.gameController = new GameController(numTowers, minNumDisks);
        uiController.initialize(gameController, towerVBoxes);

        setDiskNumLabel(minNumDisks);
        setMinMovesNumLabel();
    }

    public void setDiskNumLabel(int numDisks) {
        diskNumLabel.setText(String.valueOf(numDisks));
    }

    public void setTotalMovesNumLabel(int numMoves) {
        totalMovesNumLabel.setText(String.valueOf(numMoves));
    }

    public void setMinMovesNumLabel() {
        minMovesNumLabel.setText(String.valueOf(gameController.getNumMinMoves()));
    }

    @FXML
    public void buttonEvent(ActionEvent event) throws InterruptedException {
        Button pressedButton = (Button) event.getSource();
        String buttonId = pressedButton.getId();

        switch(buttonId) {
            case "removeDiskButton":
                if (gameController.getNumDisks() > minNumDisks) {
                    gameController.removeDisk(0);
                    uiController.removeDiskUI();
                    gameController.setNumMinMoves(gameController.getNumDisks());
                    restartGame();
                }
                return;
            case "addDiskButton":
                if (gameController.getNumDisks() < maxNumDisks) {
                    gameController.addDisk(0);
                    uiController.addDiskUI();
                    gameController.setNumMinMoves(gameController.getNumDisks());
                    restartGame();
                }
                return;
            case "restartButton":
                restartEvent(event);
                return;
            case "solveButton":
                solveEvent(event);
                return;
            case "tower1ToTower3Button":
                uiController.moveDiskUI(0, 2);
                gameController.moveDisk(0, 2);
                setTotalMovesNumLabel(gameController.getNumTotalMoves());

                if (gameController.isGameOver()) {
                    winEvent();
                }
                return;
            case "tower1RightButton":
                uiController.moveDiskUI(0, 1);
                gameController.moveDisk(0, 1);
                setTotalMovesNumLabel(gameController.getNumTotalMoves());
                return;
            case "tower2LeftButton":
                uiController.moveDiskUI(1, 0);
                gameController.moveDisk(1, 0);
                setTotalMovesNumLabel(gameController.getNumTotalMoves());
                return;
            case "tower2RightButton":
                uiController.moveDiskUI(1, 2);
                gameController.moveDisk(1, 2);
                setTotalMovesNumLabel(gameController.getNumTotalMoves());

                if (gameController.isGameOver()) {
                    winEvent();
                }
                return;
            case "tower3LeftButton":
                uiController.moveDiskUI(2, 1);
                gameController.moveDisk(2, 1);
                setTotalMovesNumLabel(gameController.getNumTotalMoves());
                return;
            case "tower3ToTower1Button":
                uiController.moveDiskUI(2, 0);
                gameController.moveDisk(2, 0);
                setTotalMovesNumLabel(gameController.getNumTotalMoves());
        }
    }

    @FXML
    public void exitEvent(ActionEvent event) {
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
            exitAlert.close();
        }
    }

    @FXML
    public void solveEvent(ActionEvent event) {
        Alert solveAlert = new Alert(Alert.AlertType.WARNING);
        solveAlert.setTitle("Solve Puzzle");
        solveAlert.setHeaderText("Are you sure you want to see the solution?");
        solveAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = solveAlert.showAndWait();

        if (!result.isPresent()) {
            solveAlert.close();
        } else if (result.get() == ButtonType.YES) {
            restartGame();
            solver = new Thread(new SolveController(uiController, this));
            solver.setDaemon(true);
            solver.start();
            cancelSolveEvent(event);
            showLoadPane(false);
        } else if (result.get() == ButtonType.NO) {
            solveAlert.close();
        }
    }

    @FXML
    public void cancelSolveEvent(ActionEvent event) {
        cancelSolveAlert = new Alert(Alert.AlertType.INFORMATION);
        cancelSolveAlert.setTitle("Solve Puzzle");
        cancelSolveAlert.setHeaderText("Solving puzzle...");
        cancelSolveAlert.setContentText("Click Cancel or close the dialog to stop solving the puzzle." +
                "\n(This will also reset the puzzle.)");
        cancelSolveAlert.getButtonTypes().setAll(ButtonType.CANCEL);
        cancelSolveAlert.showAndWait();

        if (!cancelSolveAlert.isShowing()) {
            solver.interrupt();

            if (gameController.isGameOver()) {
                winEvent();
                showLoadPane(false);
            } else {
                restartGame();
                restartConfirmAlert();
                showLoadPane(false);
            }

        }
    }

    public Alert getCancelSolveAlert() {
        return cancelSolveAlert;
    }

    @FXML
    public void winEvent() {
        Alert winAlert = new Alert(Alert.AlertType.CONFIRMATION);
        winAlert.setTitle("You Win!");
        winAlert.setHeaderText("You solved the puzzle in " + gameController.getNumTotalMoves() +  " moves.");
        winAlert.setContentText("Do you want to play again?");
        winAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = winAlert.showAndWait();

        if (!result.isPresent()) {
            winAlert.close();
        } else if (result.get() == ButtonType.YES) {
            restartGame();
            restartConfirmAlert();
        } else if (result.get() == ButtonType.NO) {
            System.exit(0);
        }
    }

    public void restartConfirmAlert() {
        Alert restartConfirmAlert = new Alert(Alert.AlertType.INFORMATION);
        restartConfirmAlert.setTitle("Restart Game");
        restartConfirmAlert.setHeaderText("Game restarted.");
        restartConfirmAlert.getButtonTypes().setAll(ButtonType.YES);
        restartConfirmAlert.show();
    }

    @FXML
    public void restartEvent(ActionEvent event) {
        Alert restartAlert = new Alert(Alert.AlertType.WARNING);
        restartAlert.setTitle("Restart Game");
        restartAlert.setHeaderText("Are you sure you want to restart the game?");
        restartAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = restartAlert.showAndWait();

        if (!result.isPresent()) {
            restartAlert.close();
        } else if (result.get() == ButtonType.YES) {
            restartGame();
            restartConfirmAlert();
        } else if (result.get() == ButtonType.NO) {
            restartAlert.close();
        }
    }

    public void restartGame() {
        uiController.resetGameUI();
        gameController.resetGame();

        setDiskNumLabel(gameController.getNumDisks());
        setMinMovesNumLabel();
        setTotalMovesNumLabel(gameController.getNumTotalMoves());
    }

    public void showLoadPane(boolean show) {
        loadPane.setVisible(show);
    }

    public void openHowToPlayWindow() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/howToPlay.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("How To Play");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/tower-pixel-icon.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
