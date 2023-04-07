package state;

import main.GamePanel;
import ui.PauseButton;
import ui.SoundButton;

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
    private SoundButton musicButton, sfxButton;

    public Pause(GamePanel gp) {
        this.gp = gp;

        loadBackground();
        createSoundButtons();
    }

    public void loadBackground() {
        try{
            bgImg = ImageIO.read(new FileInputStream("res/background/pauseMenu.png"));
            bgW = gp.getTileSize()*9;
            bgH = gp.getTileSize()*9;
            bgX = gp.getScreenWidth()/2 - bgW/2;
            bgY = gp.getScreenHeight()/2 - bgH/2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createSoundButtons() {
        int soundX = gp.getScreenWidth()/2 + gp.getTileSize()*3/2;
        int musicY = gp.getScreenHeight()/2 - gp.getTileSize()*4/3;
        int sfxY = gp.getScreenHeight()/2 - gp.getTileSize()/5;

        musicButton = new SoundButton(soundX, musicY, PauseButton.SOUND_SIZE, PauseButton.SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, PauseButton.SOUND_SIZE, PauseButton.SOUND_SIZE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(bgImg, bgX, bgY, bgW, bgH, null);

        musicButton.draw(g2d);
        sfxButton.draw(g2d);
    }

    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if(isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, musicButton)) {
            if(musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if(isIn(e, sfxButton)) {
            if(sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        }
        musicButton.resetBools();
        sfxButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if(isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if(isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        }
    }
}
