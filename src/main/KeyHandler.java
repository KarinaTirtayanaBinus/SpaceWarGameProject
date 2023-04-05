package main;

import java.awt.event.*;

public class KeyHandler implements KeyListener, MouseListener, MouseMotionListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
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
                if(gp.getState() == GameState.PLAYING) {
                    gp.setState(GameState.PAUSE);
                } else if(gp.getState() == GameState.PAUSE) {
                    gp.setState(GameState.PLAYING);
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
