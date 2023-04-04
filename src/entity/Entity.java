package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;

public abstract class Entity {
    GamePanel gp;
    private double x;
    private double y;
    private int speed;
    private int health;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
