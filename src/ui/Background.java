package ui;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Background {
    GamePanel gp;
    public static final int MENU_BG = 0;
    public static final int PLAYING_BG = 1;
    private int x = 0, y;
    private int speed;
    private BufferedImage[] background = new BufferedImage[2];
    private int index;

    public Background(int y, GamePanel gp, int index) {
        this.y = y;
        this.gp = gp;
        this.index = index;

        setDefaultValues();
    }

    public void setDefaultValues(){
        speed = 2;

        try{
            background[0] = ImageIO.read(new FileInputStream("res/background/mainMenu.jpg"));
            background[1] = ImageIO.read(new FileInputStream("res/background/spaceFull.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(background[index], x, y, gp.getScreenWidth(), gp.getScreenHeight() *2, null);
    }

    public void update() {
        y += speed;

        if(y >= 0) {
            y = -gp.getScreenHeight();
        }
    }
}
