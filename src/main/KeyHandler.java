package main;

import state.GameState;
import state.Playing;

import java.awt.event.*;

public class KeyHandler implements KeyListener, MouseListener, MouseMotionListener {
    private GamePanel gp;
    private Playing playing;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public KeyHandler(Playing playing) {
        this.playing = playing;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W: {
                upPressed = true;
                break;
            }
            case KeyEvent.VK_S: {
                downPressed = true;
                break;
            }
            case KeyEvent.VK_A: {
                leftPressed = true;
                break;
            }
            case KeyEvent.VK_D: {
                rightPressed = true;
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                if(!gp.getPlayingScreen().isPaused()) {
                    gp.getPlayingScreen().setPaused(true);
                } else {
                    gp.getPlayingScreen().setPaused(false);
                }
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W: {
                upPressed = false;
                break;
            }
            case KeyEvent.VK_S: {
                downPressed = false;
                break;
            }
            case KeyEvent.VK_A: {
                leftPressed = false;
                break;
            }
            case KeyEvent.VK_D: {
                rightPressed = false;
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (gp.getState()) {
            case MENU: {
                gp.getMenuScreen().mousePressed(e);
                break;
            }
            case PLAYING: {

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (gp.getState()) {
            case MENU: {
                gp.getMenuScreen().mouseReleased(e);
            }
        }
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
        switch (gp.getState()) {
            case MENU: {
                gp.getMenuScreen().mouseMoved(e);
                break;
            }
            case PLAYING: {
                break;
            }
        }
    }
}
