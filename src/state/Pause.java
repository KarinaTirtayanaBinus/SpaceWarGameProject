package state;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Pause {
    private GamePanel gp;
    private BufferedImage bgImg;
    private int bgX, bgY, bgW, bgH;
    private BufferedImage menuBtn;

    public Pause(GamePanel gp) {
        this.gp = gp;

        loadBackground();
    }

    public void loadBackground() {
        try{
            menuBtn = ImageIO.read(new FileInputStream("res/buttons/playBox.png"));
            bgImg = ImageIO.read(new FileInputStream("res/background/pauseMenu.png"));
            bgW = gp.getTileSize()*9;
            bgH = gp.getTileSize()*9;
            bgX = gp.getScreenWidth()/2 - bgW/2;
            bgY = gp.getScreenHeight()/2 - bgH/2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(bgImg, bgX, bgY, bgW, bgH, null);
        g2d.drawImage(menuBtn, 100, 100, gp.getTileSize(), gp.getTileSize(), null);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
