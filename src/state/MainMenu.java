package state;

import main.GamePanel;
import main.Sound;
import ui.Background;
import ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainMenu extends State implements StateMethods {
    private MenuButton[] buttons = new MenuButton[3];
    private Background background;
    private int yBg;
    private int speedBg = 1;

    public MainMenu(GamePanel gp) {
        super(gp);
        yBg = -gp.getScreenHeight()/2;
        background = new Background(-gp.getScreenHeight(), gp, Background.MENU_BG);

        loadMenuImgs();
    }

    public void loadMenuImgs() {
        buttons[0] = new MenuButton(gp.getScreenWidth()/2 - MenuButton.BTN_WIDTH/2, gp.getScreenHeight()/2-gp.getTileSize(), 0, GameState.PLAYING, gp);
        buttons[1] = new MenuButton(gp.getScreenWidth()/2 - MenuButton.BTN_WIDTH/2, gp.getScreenHeight()/2+gp.getTileSize(), 1, GameState.SETTING, gp);
        buttons[2] = new MenuButton(gp.getScreenWidth()/2 - MenuButton.BTN_WIDTH/2, gp.getScreenHeight()/2+gp.getTileSize()*3, 2, GameState.QUIT, gp);
    }

    public void draw(Graphics2D g2d) {
        background.draw(g2d);

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
        background.update();

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

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public Background getBackground() {
        return background;
    }
}
