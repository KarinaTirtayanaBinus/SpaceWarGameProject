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

    // Status Bar UI
    private BufferedImage statusBarImg;
    private int statusBarWidth = 192;
    private int statusBarHeight = 58;
    private int statusBarX = 10;
    private int statusBarY = 10;

    private int healthBarWidth = 150;
    private int healthBarHeight = 4;
    private int healthBarXStart = 34;
    private int healthBarYStart = 14;

    private int maxHealth = 100;
    private int healthWidth = healthBarWidth;

    public Player(GamePanel gp, float x, float y, int width, int height, KeyHandler keyH) {
        super(gp, x, y, width, height);
        this.keyH = keyH;
        this.gp = gp;
        speed = 5;

        initHitBox(x, y, width, height);
        getPlayerImage();
    }

    private void getPlayerImage() {
        playerImgs = new BufferedImage[5];
        try {
            for(int i = 0; i < imgsName.length; i++) {
                playerImgs[i] = ImageIO.read(new FileInputStream("res/player/" + imgsName[i] + ".png"));
            }
            statusBarImg = ImageIO.read(new FileInputStream("res/player/statusBarImg.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        updateHealthBar();

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

    private void updateHealthBar() {
        healthWidth = (int) ((currHealth/ (float) maxHealth) * healthBarWidth);
    }

    public void changeHealth(int value) {
        currHealth += value;

        if(currHealth <= 0) {
            currHealth = 0;
        } else if(currHealth >= maxHealth) {
            currHealth = maxHealth;
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

        drawUI(g2d);
    }

    public void drawUI(Graphics2D g2d) {
        g2d.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g2d.setColor(Color.red);
        g2d.fillRect(healthBarXStart+statusBarX, healthBarYStart+statusBarY, healthWidth, healthBarHeight);
    }
}
