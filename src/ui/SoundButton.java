package ui;

import system.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImgs;
    private Sound sound;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;
    private int hoverTrigger = 0, clickTrigger = 0;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        sound = new Sound();

        loadSongImgs();
    }

    private void loadSongImgs() {
        try{
            BufferedImage temp = ImageIO.read(new FileInputStream("res/buttons/soundBoxes.png"));
            soundImgs = new BufferedImage[2][3];

            for(int i = 0; i < soundImgs.length; i++) {
                for(int j = 0; j < soundImgs[i].length; j++) {
                    soundImgs[i][j] = temp.getSubimage(j*SOUND_SIZE, i*SOUND_SIZE, SOUND_SIZE, SOUND_SIZE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        if(mousePressed) {
            if(clickTrigger == 0) {
                sound.playEffect(2);
                clickTrigger = 1;
            }
            colIndex = 2;
        } else if(mouseOver) {
            if(hoverTrigger == 0) {
                sound.playEffect(1);
                hoverTrigger = 1;
            }
            colIndex = 1;
        }
        else {
            colIndex = 0;
            hoverTrigger = 0;
            clickTrigger = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
