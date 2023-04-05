package state;

import main.GamePanel;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {
    protected GamePanel gp;

    public State(GamePanel gp) {
        this.gp = gp;
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public GamePanel getGp() {
        return gp;
    }
}
