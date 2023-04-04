package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = (gp.getScreenWidth() - gp.getTileSize() *2) / 2;
        y = gp.getScreenHeight() - gp.getTileSize() *2;
        speed = 6;
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
            if(y > 0) {
                direction = "up";
                y -= speed;
            }
        } else if(keyH.downPressed) {
            if(y < gp.getScreenHeight() - gp.getTileSize() *2){
                direction = "down";
                y += speed;
            }
        } else if(keyH.leftPressed) {
            if(x > 0){
                direction = "left";
                x -= speed;
            }
        } else if(keyH.rightPressed) {
            if(x < gp.getScreenWidth() - gp.getTileSize() *2){
                direction = "right";
                x += speed;
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
        g2d.drawImage(image, x, y, gp.getTileSize() *2, gp.getTileSize() *2, null);
    }
}
