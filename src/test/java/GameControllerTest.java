import com.towerofhanoi.controller.GameController;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    private final int defaultNumTowers = 3;
    private final int defaultNumDisks = 3;

    @Test
    void gameShouldStartWithZeroTotalMoves() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var numMoves = gameController.getNumTotalMoves();
        assertEquals(0, numMoves); // Check number of towers
    }

    @Test
    void gameShouldStartWithThreeTowers() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        assertEquals(defaultNumTowers, towers.size()); // Check number of towers
    }

    @Test
    void firstTowerShouldStartWithThreeDisks() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        assertEquals(defaultNumDisks, towers.get(0).getTowerSize()); // Check if first tower has correct number of disks
    }

    @Test
    void addDiskShouldAddDiskToFirstTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.addDisk(0);
        assertEquals(4, towers.get(0).getTowerSize()); // First tower starts with 3 disks, should have 4 after add
    }

    @Test
    void addDiskShouldAddDiskToMiddleTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.addDisk(1);
        assertEquals(1, towers.get(1).getTowerSize()); // Middle tower starts with 0 disks, should have 1 after add
    }

    @Test
    void addDiskShouldAddDiskToLastTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.addDisk(2);
        assertEquals(1, towers.get(2).getTowerSize()); // Last tower starts with 0 disks, should have 1 after add
    }

    @Test
    void removeDiskShouldRemoveDiskFromFirstTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.removeDisk(0);
        assertEquals(2, towers.get(0).getTowerSize()); // First tower starts with 3 disks, should have 2 after removal
    }

    @Test
    void removeDiskShouldRemoveDiskFromMiddleTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.addDisk(1); // Add disk to test disk removal
        gameController.removeDisk(1);
        assertEquals(0, towers.get(1).getTowerSize()); // First tower starts with 3 disks, should have 2 after removal
    }

    @Test
    void removeDiskShouldRemoveDiskFromLastTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.addDisk(2); // Add disk to test disk removal
        gameController.removeDisk(2);
        assertEquals(0, towers.get(2).getTowerSize()); // First tower starts with 3 disks, should have 2 after removal
    }

    @Test
    void diskMoveShouldBeLegal() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        assertTrue(gameController.isMoveLegal(0, 1));
    }

    @Test
    void diskMoveShouldBeIllegal() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        assertFalse(gameController.isMoveLegal(1, 0));
    }

    @Test
    void diskShouldMoveFromTowerToTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.moveDisk(0, 1);
        assertEquals(1, towers.get(1).getTowerSize());
    }

    @Test
    void diskShouldNotMoveFromTowerToTower() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.moveDisk(1, 0);
        assertEquals(0, towers.get(1).getTowerSize());
    }

    @Test
    void thereShouldBeNoDisksInAnyTowers() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        var towers = gameController.getTowers();
        gameController.clearTowers();
        assertEquals(0, towers.get(0).getTowerSize());
        assertEquals(0, towers.get(1).getTowerSize());
        assertEquals(0, towers.get(2).getTowerSize());
    }

    @Test
    void gameShouldNotBeOverFreshStart() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        // Game cannot be over if game just started
        assertFalse(gameController.isGameOver());
    }

    @Test
    void gameShouldBeOverDefault() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        // Move default disks (3) to last tower to check if game is over
        gameController.moveDisk(0, 2);
        gameController.moveDisk(0, 1);
        gameController.moveDisk(2, 1);
        gameController.moveDisk(0, 2);
        gameController.moveDisk(1, 0);
        gameController.moveDisk(1, 2);
        gameController.moveDisk(0, 2);
        assertTrue(gameController.isGameOver());
    }

    @Test
    void gameShouldBeOverOneDisk() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        gameController.clearTowers();
        gameController.addDisk(0); // Game should end if only disk is moved to last tower.
        gameController.moveDisk(0, 2);
        assertTrue(gameController.isGameOver());
    }

    @Test
    void gameShouldNotBeOverNoDisks() {
        var gameController = new GameController(defaultNumTowers, defaultNumDisks);
        gameController.clearTowers(); // Game cannot be over if no disks exist
        assertFalse(gameController.isGameOver());
    }
}
