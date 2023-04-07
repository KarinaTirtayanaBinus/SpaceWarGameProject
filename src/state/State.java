package state;

import main.GamePanel;
import main.Sound;
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

    public void setGameState(GameState state) {
        switch (state) {
            case MENU: {
                gp.getSound().playSong(Sound.MENU);
            }
            case PLAYING: {
                gp.getSound().playSong(Sound.PLAYING);
            }
        }
        gp.setState(state);
    }

    public GamePanel getGp() {
        return gp;
    }
}
