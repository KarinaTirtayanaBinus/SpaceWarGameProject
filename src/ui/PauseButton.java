package ui;

import main.Sound;

import java.awt.*;

public class PauseButton {
    public static final int SOUND_SIZE = 42;
    public static final int URM_SIZE = 56;
    public static final int VOLUME_WIDTH = 28;
    public static final int VOLUME_HEIGHT = 44;
    public static final int SLIDER_WIDTH = 215;
    protected int x, y, width, height;
    protected Rectangle bounds;

    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
