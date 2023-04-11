package state;

import main.GamePanel;
import system.Sound;
import ui.Background;
import ui.PauseButton;
import ui.UrmButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class GameOver extends State implements StateMethods{
    private Playing playing;
    private UrmButton menuBtn, restartBtn;
    private Background background;
    private BufferedImage gameOverBg;
    private int bgX, bgY, bgW, bgH;

    public GameOver(GamePanel gp, Playing playing) {
        super(gp);
        this.playing = playing;
        background = playing.getBackground();

        loadImgs();
        loadButtons();
    }

    private void loadImgs() {
        try {
            gameOverBg = ImageIO.read(new FileInputStream("res/background/gameOverBg.png"));
            bgW = gp.getTileSize()*9;
            bgH = gp.getTileSize()*9;
            bgX = gp.getScreenWidth()/2 - bgW/2;
            bgY = gp.getScreenHeight()/2 - bgH/2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadButtons() {
        int restartX = gp.getScreenWidth()/2 - gp.getTileSize()*2-15;
        int menuX = gp.getScreenWidth()/2 + gp.getTileSize()+10;
        int urmY = gp.getScreenHeight()/2 + gp.getTileSize();
        menuBtn = new UrmButton(menuX, urmY, PauseButton.URM_SIZE, PauseButton.URM_SIZE, 2);
        restartBtn = new UrmButton(restartX, urmY, PauseButton.URM_SIZE, PauseButton.URM_SIZE, 1);
    }

    @Override
    public void update() {
//        background.update();
        menuBtn.update();
        restartBtn.update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        background.draw(g2d);
        g2d.drawImage(gameOverBg, bgX, bgY, bgW, bgH, null);

        menuBtn.draw(g2d);
        restartBtn.draw(g2d);
    }

    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e, menuBtn)) {
            menuBtn.setMousePressed(true);
        } else if(isIn(e, restartBtn)) {
            restartBtn.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e, menuBtn)) {
            menuBtn.setMousePressed(false);
            gp.setState(GameState.MENU);
            playing.reset();
            gp.getSound().playSong(Sound.MENU);
        } else if(isIn(e, restartBtn)) {
            restartBtn.setMousePressed(false);
            playing.reset();
            gp.getSound().playSong(Sound.PLAYING);
        }

        menuBtn.resetBools();
        restartBtn.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuBtn.setMouseOver(false);
        restartBtn.setMouseOver(false);

        if(isIn(e, menuBtn)) {
            menuBtn.setMouseOver(true);
        } else if(isIn(e, restartBtn)) {
            restartBtn.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
