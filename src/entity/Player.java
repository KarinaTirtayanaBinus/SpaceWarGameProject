package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Entity {
    KeyHandler keyH;
    private BufferedImage up, down, left, right, def;
    private String direction;
    private int defCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        setX((gp.getScreenWidth() - gp.getTileSize() *2) / 2);
        setY(gp.getScreenHeight() - gp.getTileSize() *2);
        setSpeed(5);
        direction = "def";
    }

    public void getPlayerImage() {
        try {
            up = ImageIO.read(new FileInputStream("res/player/up.png"));
            down = ImageIO.read(new FileInputStream("res/player/down.png"));
            left = ImageIO.read(new FileInputStream("res/player/left.png"));
            right = ImageIO.read(new FileInputStream("res/player/right.png"));
            def = ImageIO.read(new FileInputStream("res/player/default.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed){
            defPosition();
        }

        if(keyH.upPressed) {
            if(getY() > 0) {
                direction = "up";
                setY(getY() - getSpeed());
            }
        } else if(keyH.downPressed) {
            if(getY() < gp.getScreenHeight() - gp.getTileSize() *2){
                direction = "down";
                setY(getY() + getSpeed());
            }
        } else if(keyH.leftPressed) {
            if(getX() > 0){
                direction = "left";
                setX(getX() - getSpeed());
            }
        } else if(keyH.rightPressed) {
            if(getX() < gp.getScreenWidth() - gp.getTileSize() *2){
                direction = "right";
                setX(getX() + getSpeed());
            }
        }
    }

    public void defPosition() {
        defCounter++;
        if(defCounter > 10){
            direction = "def";
            defCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image;
        switch(direction) {
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
            default:
                image = def;
                break;
        }
        g2d.drawImage(image, (int) getX(), (int) getY(), gp.getTileSize() *2, gp.getTileSize() *2, null);
    }
}
