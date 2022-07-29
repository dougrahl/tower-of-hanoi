package com.towerofhanoi.ui;

import com.towerofhanoi.controller.GameController;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages UI objects for the Tower of Hanoi game.
 */
public class UIController {
    // Constants for disk
    private static final int DEFAULT_DISK_WIDTH = 180;
    private static final int DEFAULT_DISK_HEIGHT = 20;

    private int currentDiskWidth;

    private List<VBox> towerVBoxes;
    private int numTowers;
    private int numDisks;

    private GameController gameController;

    public UIController() {
        towerVBoxes = new ArrayList<>();
        currentDiskWidth = DEFAULT_DISK_WIDTH;
    }

    public void initialize(GameController gameController, List<VBox> vBoxes) {
        this.gameController = gameController;
        towerVBoxes = vBoxes;
        numTowers = gameController.getNumTowers();
        numDisks = gameController.getNumDisks();

        createGameUI();
    }

    public void createGameUI() {
        currentDiskWidth = DEFAULT_DISK_WIDTH;
        for (int i = 0; i < numDisks; i++) {
            towerVBoxes.get(0).getChildren().add(0, UIUtility.createRectangle(currentDiskWidth, DEFAULT_DISK_HEIGHT, i));
            currentDiskWidth -= 17.5;
        }
    }

    public void addDiskUI() {
        towerVBoxes.get(0).getChildren().add(0, UIUtility.createRectangle(currentDiskWidth, DEFAULT_DISK_HEIGHT, numDisks));
        currentDiskWidth -= 17.5;
        numDisks += 1;
    }

    public void removeDiskUI() {
        this.towerVBoxes.get(0).getChildren().remove(0);
        this.currentDiskWidth += 17.5;
        numDisks -= 1;
    }

    public void moveDiskUI(int fromTowerId, int toTowerId) {
        if (gameController.isMoveLegal(fromTowerId, toTowerId)) {
            Rectangle fromTower = (Rectangle) towerVBoxes.get(fromTowerId).getChildren().get(0);
            towerVBoxes.get(toTowerId).getChildren().add(0, fromTower);
        }
    }

    private void clearTowersUI() {
        for (VBox vBox: towerVBoxes) {
            vBox.getChildren().clear();
        }
    }

    public void resetGameUI() {
        clearTowersUI();
        createGameUI();
    }

    public GameController getGameController() {
        return this.gameController;
    }
}
