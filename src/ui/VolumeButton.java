package ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class VolumeButton extends PauseButton {
    private BufferedImage[] volImgs;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseOver, mousePressed;
    private int buttonX, minX, maxX;
    private float floatValue = 0f;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width/2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH/2;
        buttonX = x + width/2;
        this.x = x;
        this.width = width;
        minX = x + VOLUME_WIDTH/2;
        maxX = x + width - VOLUME_WIDTH/2;

        loadImgs();
    }

    public void loadImgs() {
        try {
            BufferedImage temp = ImageIO.read(new FileInputStream("res/buttons/volumeButton.png"));
            volImgs = new BufferedImage[3];
            for(int i = 0; i < volImgs.length; i++) {
                volImgs[i] = temp.getSubimage(i*VOLUME_WIDTH, 0, VOLUME_WIDTH, VOLUME_HEIGHT);
            }

            slider = temp.getSubimage(3*VOLUME_WIDTH, 0, SLIDER_WIDTH, VOLUME_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(mousePressed) {
            index = 2;
        } else if(mouseOver) {
            index = 1;
        } else {
            index = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(slider, x, y, width, height, null);
        g2d.drawImage(volImgs[index], buttonX - VOLUME_WIDTH/2, y, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x) {
        if(x < minX) {
            buttonX = minX;
        } else if(x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }
        bounds.x = buttonX - VOLUME_WIDTH/2;

        updateFloatValue();
    }

    private void updateFloatValue() {
        float range = maxX - minX;
        float value = buttonX - minX;
        floatValue = value/range;

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

    public float getFloatValue() {
        return floatValue;
    }
}
