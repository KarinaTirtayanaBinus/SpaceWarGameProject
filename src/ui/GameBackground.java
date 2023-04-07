package ui;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class GameBackground {
    GamePanel gp;
    private int x = 0, y;
    private int speed;
    private BufferedImage background;

    public GameBackground(int y, GamePanel gp) {
        this.y = y;
        this.gp = gp;

        setDefaultValues();
    }

    public void setDefaultValues(){
        speed = 2;

        try{
            background = ImageIO.read(new FileInputStream("res/background/spaceFull.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(background, x, y, gp.getScreenWidth(), gp.getScreenHeight() *2, null);
        g2d.setFont(new Font("OCR A Extended", Font.ITALIC, 36));
        g2d.setColor(Color.white);
        g2d.drawString("FPS: " + gp.getCurrFPS(), gp.getXForCenteredText("FPS: " + gp.getCurrFPS(), g2d), 50);
    }

    public void update() {
        y += speed;

        if(y >= 0) {
            y = -gp.getScreenHeight();
        }
    }
}
