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
        setDirection("def");
    }

    public void getPlayerImage() {
        try {
            setUp(ImageIO.read(new FileInputStream("res/player/up.png")));
            setDown(ImageIO.read(new FileInputStream("res/player/down.png")));
            setLeft(ImageIO.read(new FileInputStream("res/player/left.png")));
            setRight(ImageIO.read(new FileInputStream("res/player/right.png")));
            setDef(ImageIO.read(new FileInputStream("res/player/default.png")));
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
                setDirection("up");
                setY(getY() - getSpeed());
            }
        } else if(keyH.downPressed) {
            if(getY() < gp.getScreenHeight() - gp.getTileSize() *2){
                setDirection("down");
                setY(getY() + getSpeed());
            }
        } else if(keyH.leftPressed) {
            if(getX() > 0){
                setDirection("left");
                setX(getX() - getSpeed());
            }
        } else if(keyH.rightPressed) {
            if(getX() < gp.getScreenWidth() - gp.getTileSize() *2){
                setDirection("right");
                setX(getX() + getSpeed());
            }
        }
    }

    public void defPosition() {
        defCounter++;
        if(defCounter > 10){
            setDirection("def");
            defCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image;
        switch(getDirection()) {
            case "up":
                image = getUp();
                break;
            case "down":
                image = getDown();
                break;
            case "left":
                image = getLeft();
                break;
            case "right":
                image = getRight();
                break;
            default:
                image = getDef();
                break;
        }
        g2d.drawImage(image, (int) getX(), (int) getY(), gp.getTileSize() *2, gp.getTileSize() *2, null);
    }
}
