package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {
    private int enemyType;
    private Rectangle bounds;
    private double speed = 1;
    private int actionLockCounter = 0;
    private int actionLockIndex = 1;

    public Enemy(int x, int y, int enemyType, GamePanel gp) {
        super(gp);

        setX(x);
        setY(y);
        this.enemyType = enemyType;
        bounds = new Rectangle(x, y, gp.getTileSize(), gp.getTileSize());
    }

    public void move() {
        setX(this.getX()+speed);
        setY(this.getY()+speed);
    }

    public void setAction() {
        actionLockCounter++;
        if(getX() < 50) {
            setX(getX()+speed);
        } else if(getX() >= 50*actionLockIndex && getX() <= gp.getScreenWidth()-50*actionLockIndex && actionLockCounter >= 120){
            actionLockIndex++;
            actionLockCounter = 0;
        } else{
            setX(getX()+speed);
        }

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
