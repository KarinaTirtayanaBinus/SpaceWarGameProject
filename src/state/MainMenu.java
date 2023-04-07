package state;

import main.GamePanel;
import main.Sound;
import ui.MenuButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class MainMenu extends State implements MouseListener, MouseMotionListener {
    BufferedImage backgroundMenu;
    private MenuButton[] buttons = new MenuButton[3];
    private int yBg;
    private int speedBg = 1;

    public MainMenu(GamePanel gp) {
        super(gp);
        yBg = -gp.getScreenHeight()/2;

        loadMenuImgs();
    }

    public void loadMenuImgs() {
        try{
            backgroundMenu = ImageIO.read(new FileInputStream("res/background/mainMenu.jpg"));
            buttons[0] = new MenuButton(gp.getScreenWidth()/2 - MenuButton.BTN_WIDTH/2, gp.getScreenHeight()/2-gp.getTileSize(), 0, GameState.PLAYING, gp);
            buttons[1] = new MenuButton(gp.getScreenWidth()/2 - MenuButton.BTN_WIDTH/2, gp.getScreenHeight()/2+gp.getTileSize(), 1, GameState.SETTING, gp);
            buttons[2] = new MenuButton(gp.getScreenWidth()/2 - MenuButton.BTN_WIDTH/2, gp.getScreenHeight()/2+gp.getTileSize()*3, 2, GameState.QUIT, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawMenu(Graphics2D g2d) {
        g2d.drawImage(backgroundMenu, 0, yBg, null);

        g2d.setFont(new Font("Forte", Font.BOLD, 84));
        g2d.setColor(Color.gray);
        String gameTitle = "SPACE WAR";
        g2d.drawString(gameTitle, gp.getXForCenteredText(gameTitle, g2d)+2, gp.getScreenHeight()/2 - gp.getTileSize()*3+2);

        g2d.setColor(new Color(255, 145, 55, 194));
        g2d.drawString(gameTitle, gp.getXForCenteredText(gameTitle, g2d), gp.getScreenHeight()/2 - gp.getTileSize()*3);

        for(MenuButton mb: buttons) {
            mb.draw(g2d);
        }
    }

    public void resetButtons() {
        for(MenuButton mb: buttons) {
            mb.resetBools();
        }
    }

    public void update() {
        yBg += speedBg;

        if(yBg >= 0) {
            yBg = -gp.getScreenHeight()/2;
        }

        for(MenuButton mb: buttons) {
            mb.update();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb: buttons) {
            if(isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb: buttons) {
            if(isIn(e, mb)) {
                if(mb.isMousePressed()){
                    mb.applyGameState();
                }
                if(mb.getState() == GameState.PLAYING) {
                    gp.getSound().playSong(Sound.PLAYING);
                }
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb: buttons) {
            mb.setMouseOver(false);
        }
        for(MenuButton mb: buttons) {
            if(isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }
}
