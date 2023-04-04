package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;

public abstract class Entity {
    GamePanel gp;
    private double x;
    private double y;
    private int speed;
    private int health;
    private BufferedImage up, down, left, right, def;
    private String direction;

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

    public BufferedImage getUp() {
        return up;
    }

    public void setUp(BufferedImage up) {
        this.up = up;
    }

    public BufferedImage getDown() {
        return down;
    }

    public void setDown(BufferedImage down) {
        this.down = down;
    }

    public BufferedImage getLeft() {
        return left;
    }

    public void setLeft(BufferedImage left) {
        this.left = left;
    }

    public BufferedImage getRight() {
        return right;
    }

    public void setRight(BufferedImage right) {
        this.right = right;
    }

    public BufferedImage getDef() {
        return def;
    }

    public void setDef(BufferedImage def) {
        this.def = def;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
