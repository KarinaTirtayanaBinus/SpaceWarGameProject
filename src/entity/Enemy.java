package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {
    private int enemyType;
    private Rectangle bounds;
    private double speed = 1;
    private int actionLockCounter = 0;

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
        if(getX() < 50) {
            setX(getX()+speed);
        } else{
            actionLockCounter++;
            if(actionLockCounter >= 10) {
                Random random = new Random();
                int i = random.nextInt(100)+1;
//                int j = random.nextInt(50)+50;

                if(i <= 25) {
                    setDirection("up");
                    setY(getY() - speed);
                } else if(i > 25 && i <= 50) {
                    setDirection("down");
                    setY(getY() + speed);
                } else if(i > 50 && i <= 75) {
                    setDirection("left");
                    setX(getX() - speed);
                } else if(i > 75) {
                    setDirection("right");
                    setX(getX() + speed);
                }
//            setY(this.getY()+speed);
                actionLockCounter = 0;

            }
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
