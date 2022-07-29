package com.towerofhanoi.controller;

import com.towerofhanoi.model.Tower;
import com.towerofhanoi.ui.TowerOfHanoiController;
import com.towerofhanoi.ui.UIController;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Map;

public class SolveController extends Task<Void> {
    private GameController gameController;
    private UIController uiController;
    private TowerOfHanoiController towerController;

    public SolveController(UIController uiController, TowerOfHanoiController towerController) {
        this.uiController = uiController;
        this.gameController = this.uiController.getGameController();
        this.towerController = towerController;
    }

    private void solver(int numDisks, int sourceTower, int destinationTower, int auxiliaryTower) throws InterruptedException{
        if (numDisks == 1) {
            // Move last disk of a tower to destination
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    uiController.moveDiskUI(sourceTower, destinationTower);
                    towerController.setTotalMovesNumLabel(gameController.getNumTotalMoves() + 1);
                }
            });
            Thread.sleep(800);
            gameController.moveDisk(sourceTower, destinationTower);
            return;
        }
        // Move disk n-1 from source to auxiliary
        solver(numDisks - 1, sourceTower, auxiliaryTower, destinationTower);

        // Move disk nth from source to destination
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                uiController.moveDiskUI(sourceTower, destinationTower);
                towerController.setTotalMovesNumLabel(gameController.getNumTotalMoves() + 1);
            }
        });
        Thread.sleep(800);
        gameController.moveDisk(sourceTower, destinationTower);

        // Move disk n-1 from auxiliary to destination
        solver(numDisks - 1, auxiliaryTower, destinationTower, sourceTower);
    }

    @Override
    protected Void call() throws Exception {
        int numDisks = gameController.getNumDisks();
        Map<Integer, Tower> towers = gameController.getTowers();
        int source = towers.get(0).getTowerId();
        int destination = towers.get(2).getTowerId();
        int auxiliary = towers.get(1).getTowerId();
        towerController.showLoadPane(true); // Show loading pane
        Thread.sleep(800);
        solver(numDisks, source, destination, auxiliary);

        if (gameController.isGameOver()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    towerController.getCancelSolveAlert().close(); // Close solving alert, triggering win alert
                }
            });
        }
        towerController.showLoadPane(false);
        return null;
    }
}
