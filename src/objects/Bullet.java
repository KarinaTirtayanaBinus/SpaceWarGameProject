package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Bullet {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int BOSS = 2;
    private GamePanel gp;
    private int x;
    private int y;
    private int playerBulletSpeed = 1;
    private int enemyBulletSpeed = 6;
    private BufferedImage bulletImg, bossBulletImg;
    private int user;

    public Bullet(GamePanel gp, int x, int y, int user) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.user = user;

        getBulletImg();
    }

    public void getBulletImg() {
        try{
            bulletImg = ImageIO.read(new FileInputStream("res/bullets/bulletStrip3.png"));
            bossBulletImg = ImageIO.read(new FileInputStream("res/bullets/bulletStrip1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        switch (user) {
            case PLAYER: {
                g2d.drawImage(bulletImg.getSubimage(0, 0, 25, 30), x+ gp.getTileSize() /2, y-gp.getTileSize(), gp.getTileSize(), gp.getTileSize()+gp.getTileSize()/2, null);
                break;
            }
            case ENEMY: {
                g2d.drawImage(bulletImg.getSubimage(0, 25, 25, 30), x+ gp.getTileSize() /2, y-gp.getTileSize(), gp.getTileSize(), gp.getTileSize()+gp.getTileSize()/2, null);
                break;
            }
            case BOSS: {
                g2d.drawImage(bossBulletImg.getSubimage(46, 0, 26, 39), x+ gp.getTileSize() /2, y-gp.getTileSize(), gp.getTileSize(), gp.getTileSize()+gp.getTileSize()/2, null);
            }
        }
    }

    public void update() {
        switch (user) {
            case PLAYER: {
                y -= playerBulletSpeed;
                break;
            }
            case ENEMY, BOSS: {
                y += enemyBulletSpeed;
                break;
            }
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setPlayerBulletSpeed(int playerBulletSpeed) {
        this.playerBulletSpeed = playerBulletSpeed;
    }

    public int getPlayerBulletSpeed() {
        return playerBulletSpeed;
    }
}
