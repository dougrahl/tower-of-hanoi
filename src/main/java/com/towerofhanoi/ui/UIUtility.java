package com.towerofhanoi.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UIUtility {
    // Constant for colors
    private static final Color[] COLORS = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
            Color.DARKBLUE, Color.PURPLE, Color.WHITE};

    public static Rectangle createRectangle(int width, int height, int index) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(COLORS[index]);
        return rectangle;
    }
}
