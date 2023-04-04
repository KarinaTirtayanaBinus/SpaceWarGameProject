package entity;

import main.GamePanel;

import java.awt.*;

public class Enemy extends Entity {
    private int enemyType;
    private Rectangle bounds;
    private double speed = 1;

    public Enemy(int x, int y, int enemyType, GamePanel gp) {
        super(gp);

        this.setX(x);
        this.setY(y);
        this.enemyType = enemyType;
        bounds = new Rectangle(x, y, gp.getTileSize(), gp.getTileSize());
    }

    public void move() {
        this.setX(this.getX()+speed);
        this.setY(this.getY()+speed);
    }

    public int getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(int enemyType) {
        this.enemyType = enemyType;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
