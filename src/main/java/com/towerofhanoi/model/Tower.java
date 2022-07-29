package com.towerofhanoi.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class Tower {
    private int towerId;
    private Deque<Disk> towerDisks;

    public Tower(int id) {
        this.towerId = id;
        this.towerDisks = new ArrayDeque<>();
    }

    public void addDisk(Disk disk) {
        towerDisks.push(disk);
    }

    public Disk popDisk() {
        return towerDisks.pop();
    }

    private void pushDisk(Disk disk) {
        if (!towerDisks.isEmpty()) {
            if (towerDisks.peek().getDiskId() < disk.getDiskId()) {
                towerDisks.push(disk);
            }
        } else {
            towerDisks.push(disk);
        }
    }

    public void moveDisk(Tower tower) {
        tower.pushDisk(popDisk());
    }

    public Disk peekDisk() {
        if (!towerDisks.isEmpty()) {
            return towerDisks.peek();
        }
        return null;
    }

    public void clearTower() {
        if (towerDisks.isEmpty()) {
            return;
        }
        towerDisks.clear();
    }

    public int getTowerId() {
        return towerId;
    }

    public Deque<Disk> getTowerDisks() {
        return towerDisks;
    }

    public int getTowerSize() {
        return towerDisks.size();
    }

    @Override
    public String toString() {
        return "Tower:" + getTowerId() + " Disks:" + getTowerDisks();
    }
}
