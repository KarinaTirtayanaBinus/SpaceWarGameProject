package bullets;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Bullet {
    GamePanel gp;
    private int x;
    private int y;
    private int speed;
    private BufferedImage bulletImg;

    public Bullet(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;

        setDefaultValues();
    }

    public void setDefaultValues() {
        speed = 6;

        try{
            bulletImg = ImageIO.read(new FileInputStream("res/bullets/bulletStrip3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(bulletImg.getSubimage(0, 0, 25, 30), x+ gp.getTileSize() /2, y, gp.getTileSize(), gp.getTileSize()+gp.getTileSize()/2, null);
    }

    public void update() {
        y -= speed;
    }

    public int getY() {
        return y;
    }
}
