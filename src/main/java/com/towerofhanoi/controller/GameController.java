package com.towerofhanoi.controller;

import com.towerofhanoi.model.Disk;
import com.towerofhanoi.model.Tower;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages logic for the Tower of Hanoi game.
 */
public class GameController {
    private Map<Integer, Tower> towers;
    private int numTowers;
    private int numDisks;
    private int numTotalMoves;
    private int numMinMoves;

    public GameController(int numTowers, int numDisks) {
        towers = new LinkedHashMap<>();
        this.numTowers = numTowers;
        this.numDisks = numDisks;
        resetNumTotalMoves();
        setNumMinMoves(numDisks);

        createGame();
    }

    public void createGame() {
        resetNumTotalMoves();

        // Create towers
        for (int i = 0; i < numTowers; i++) {
            Tower tower = new Tower(i);
            towers.put(i, tower);
        }

        // Create disks and add to first tower
        for (int i = 0; i < numDisks; i++) {
            Disk disk = new Disk(i);
            towers.get(0).addDisk(disk);
        }
    }

    public void addDisk() {
        Disk disk = new Disk(numDisks);
        towers.get(0).addDisk(disk);
        numDisks += 1;
    }

    public void removeDisk() {
        towers.get(0).popDisk();
        numDisks -= 1;
    }

    public void moveDisk(int fromTowerId, int toTowerId) {
        if (isMoveLegal(fromTowerId, toTowerId)) {
            towers.get(fromTowerId).moveDisk(towers.get(toTowerId));
            numTotalMoves += 1;
        }
    }

    public boolean isMoveLegal(int fromTowerId, int toTowerId) {
        Disk fromTop = towers.get(fromTowerId).peekDisk();
        Disk toTop = towers.get(toTowerId).peekDisk();

        if (fromTop != null) {
            if (toTop == null) {
                return true;
            } else if (fromTop.getDiskId() > toTop.getDiskId()) {
                return true;
            }
        }
        return false;
    }

    private void clearTowers() {
        for (Map.Entry<Integer, Tower> tower: towers.entrySet()) {
            tower.getValue().clearTower();
        }
    }

    public void resetGame() {
        clearTowers();
        createGame();
    }

    public boolean isGameOver() {
        if (towers.get(2).getTowerSize() == numDisks && towers.get(2).getTowerDisks().peek().getDiskId() == numDisks - 1) {
            return true;
        }
        return false;
    }

    public int getNumTowers() {
        return numTowers;
    }

    public int getNumDisks() {
        return numDisks;
    }

    public int getNumTotalMoves() {
        return numTotalMoves;
    }

    public int getNumMinMoves() {
        return numMinMoves;
    }

    public Map<Integer, Tower> getTowers() {
        return towers;
    }

    public void setNumMinMoves(int numDisks) {
        numMinMoves = (int) Math.pow(2, numDisks) - 1;
    }

    public void resetNumTotalMoves() {
        numTotalMoves = 0;
    }

    @Override
    public String toString() {
        return "towers=" + towers.values();
    }
}
