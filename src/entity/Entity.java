package entity;

import main.GamePanel;

import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected GamePanel gp;
    protected static final int DEFAULT = 0;
    protected static final int LEFT = 1;
    protected static final int UP = 2;
    protected static final int RIGHT = 3;
    protected static final int DOWN = 4;
    protected float x, y;
    protected float width, height;
    protected Rectangle2D.Float hitBox;
    protected int state;
    protected int currHealth;
    protected float speed;
    protected int moveDir;

    public Entity(GamePanel gp, float x, float y, int width, int height) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        currHealth = 100;
    }

    protected void initHitBox(float x, float y, int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }
}
