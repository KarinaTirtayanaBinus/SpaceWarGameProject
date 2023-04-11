package state;

import main.GamePanel;
import ui.AudioOptions;
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

public class OptionMenu extends State implements StateMethods{
    private AudioOptions audioOptions;
    private BufferedImage optionsBgImg;
    private Background background;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuBtn;

    public OptionMenu(GamePanel gp) {
        super(gp);
        audioOptions = gp.getAudioOptions();
        background = gp.getMenuScreen().getBackground();

        loadImgs();
        loadButtons();
    }

    public void loadImgs() {
        try {
            optionsBgImg = ImageIO.read(new FileInputStream("res/background/optionBg.png"));
            bgW = gp.getTileSize()*9;
            bgH = gp.getTileSize()*9;
            bgX = gp.getScreenWidth()/2 - bgW/2;
            bgY = gp.getScreenHeight()/2 - bgH/2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadButtons() {
        int menuX = gp.getScreenWidth()/2 - gp.getTileSize()/2;
        int menuY = gp.getScreenHeight()/2 + gp.getTileSize()*7/3;
        menuBtn = new UrmButton(menuX, menuY, PauseButton.URM_SIZE, PauseButton.URM_SIZE, 2);
    }

    @Override
    public void update() {
        background.update();
        menuBtn.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        background.draw(g2d);
        g2d.drawImage(optionsBgImg, bgX, bgY, bgW, bgH, null);

        menuBtn.draw(g2d);
        audioOptions.draw(g2d);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e, menuBtn)) {
            menuBtn.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e, menuBtn)) {
            if(menuBtn.isMousePressed()){
                if(gp.getState() == GameState.SETTING) {
                    gp.setState(GameState.MENU);
                }
            }
        } else {
            audioOptions.mouseReleased(e);
        }

        menuBtn.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuBtn.setMouseOver(false);

        if(isIn(e, menuBtn)) {
            menuBtn.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: {
                gp.setState(GameState.MENU);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
