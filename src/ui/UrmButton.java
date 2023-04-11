package ui;

import system.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class UrmButton extends PauseButton {
    private BufferedImage[] urmImgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;
    private Sound sound;
    private int hoverTrigger = 0, clickTrigger = 0;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        sound = new Sound();

        loadImgs();
    }

    public void loadImgs() {
        try {
            BufferedImage temp = ImageIO.read(new FileInputStream("res/buttons/urmBoxes.png"));
            urmImgs = new BufferedImage[3];

            for(int i = 0; i < urmImgs.length; i++) {
                urmImgs[i] = temp.getSubimage(i*URM_SIZE, rowIndex*URM_SIZE, URM_SIZE, URM_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(mousePressed) {
            if(clickTrigger == 0) {
                sound.playEffect(2);
                clickTrigger = 1;
            }
            index = 2;
        } else if(mouseOver) {
            if(hoverTrigger == 0) {
                sound.playEffect(1);
                hoverTrigger = 1;
            }
            index = 1;
        } else {
            index = 0;
            hoverTrigger = 0;
            clickTrigger = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(urmImgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
