package state;

import main.GamePanel;
import system.Sound;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Pause {
    private GamePanel gp;
    private AudioOptions audioOptions;
    private Playing playing;
    private BufferedImage bgImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuBtn, restartBtn, unpauseBtn;


    public Pause(GamePanel gp, Playing playing) {
        this.gp = gp;
        this.playing = playing;
        audioOptions = gp.getAudioOptions();

        loadBackground();
        createUrmButtons();

    }

    private void loadBackground() {
        try{
            bgImg = ImageIO.read(new FileInputStream("res/background/pauseBg.png"));
            bgW = gp.getTileSize()*9;
            bgH = gp.getTileSize()*9;
            bgX = gp.getScreenWidth()/2 - bgW/2;
            bgY = gp.getScreenHeight()/2 - bgH/2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createUrmButtons() {
        int unpauseX = gp.getScreenWidth()/2 - PauseButton.URM_SIZE - gp.getTileSize();
        int restartX = gp.getScreenWidth()/2 - PauseButton.URM_SIZE/2;
        int menuX = gp.getScreenWidth()/2 + gp.getTileSize();
        int btnY = gp.getScreenHeight()/2 + gp.getTileSize()*5/2;

        unpauseBtn = new UrmButton(unpauseX, btnY, PauseButton.URM_SIZE, PauseButton.URM_SIZE, 0);
        restartBtn = new UrmButton(restartX, btnY, PauseButton.URM_SIZE, PauseButton.URM_SIZE, 1);
        menuBtn = new UrmButton(menuX, btnY, PauseButton.URM_SIZE, PauseButton.URM_SIZE, 2);
    }

    public void update() {
        // URM Buttons
        unpauseBtn.update();
        restartBtn.update();
        menuBtn.update();

        // Music and Sound Effects
        audioOptions.update();
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(bgImg, bgX, bgY, bgW, bgH, null);

        // URM Buttons
        unpauseBtn.draw(g2d);
        restartBtn.draw(g2d);
        menuBtn.draw(g2d);

        // Music and Sound Effects
        audioOptions.draw(g2d);
    }

    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, unpauseBtn)) {
            unpauseBtn.setMousePressed(true);
        } else if(isIn(e, restartBtn)) {
            restartBtn.setMousePressed(true);
        } else if(isIn(e, menuBtn)) {
            menuBtn.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, unpauseBtn)) {
            if(unpauseBtn.isMousePressed()) {
                playing.unpauseGame();
            }
        } else if(isIn(e, restartBtn)) {
            if(restartBtn.isMousePressed()) {
                playing.reset();
                gp.getSound().playSong(Sound.PLAYING);
            }
        } else if(isIn(e, menuBtn)) {
            if(menuBtn.isMousePressed()) {
                gp.setState(GameState.MENU);
                playing.unpauseGame();
                playing.reset();
                gp.getSound().playSong(Sound.MENU);
            }
        } else {
            audioOptions.mouseReleased(e);
        }

        unpauseBtn.resetBools();
        restartBtn.resetBools();
        menuBtn.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        unpauseBtn.setMouseOver(false);
        restartBtn.setMouseOver(false);
        menuBtn.setMouseOver(false);

        if(isIn(e, unpauseBtn)) {
            unpauseBtn.setMouseOver(true);
        } else if(isIn(e, restartBtn)) {
            restartBtn.setMouseOver(true);
        } else if(isIn(e, menuBtn)) {
            menuBtn.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }
}
