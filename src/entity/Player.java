package entity;

import main.GamePanel;
import system.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Entity {
    private GamePanel gp;
    private KeyHandler keyH;
    private int defCounter = 0;
    private BufferedImage[] playerImgs;
    private String[] imgsName = {"default", "left", "up", "right", "down"};

    public Player(GamePanel gp, float x, float y, int width, int height, KeyHandler keyH) {
        super(gp, x, y, width, height);
        this.keyH = keyH;
        this.gp = gp;
        speed = 5;

        getPlayerImage();
    }

    private void getPlayerImage() {
        playerImgs = new BufferedImage[5];
        try {
            for(int i = 0; i < imgsName.length; i++) {
                playerImgs[i] = ImageIO.read(new FileInputStream("res/player/" + imgsName[i] + ".png"));
            }
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
                moveDir = UP;
                y -= speed;
            }
        } else if(keyH.downPressed) {
            if(y < gp.getScreenHeight() - gp.getTileSize() *2){
                moveDir = DOWN;
                y += speed;
            }
        } else if(keyH.leftPressed) {
            if(x > 0){
                moveDir = LEFT;
                x -= speed;
            }
        } else if(keyH.rightPressed) {
            if(x < gp.getScreenWidth() - gp.getTileSize() *2){
                moveDir = RIGHT;
                x += speed;
            }
        }
    }

    public void defPosition() {
        defCounter++;
        if(defCounter > 10){
            moveDir = DEFAULT;
            defCounter = 0;
        }
    }

    public void resetPosition() {
        x = (gp.getScreenWidth() - gp.getTileSize() *2) / 2;
        y = (gp.getScreenHeight() - gp.getTileSize() *2);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(playerImgs[moveDir], (int) x, (int) y, (int) width, (int) height, null);
    }
}
