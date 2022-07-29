package com.towerofhanoi.model;

public class Disk {
    private int diskId;

    public Disk(int id) {
        this.diskId = id;
    }

    public int getDiskId() {
        return this.diskId;
    }

    @Override
    public String toString() {
        return "Disk Id:" + diskId;
    }
}
