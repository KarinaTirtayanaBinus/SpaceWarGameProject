package state;

import main.GamePanel;

import java.awt.*;

public class Pause {
    GamePanel gp;

    public Pause(GamePanel gp) {
        this.gp = gp;
    }

    public void drawPause(Graphics2D g2d) {
        String text = "PAUSED";

        g2d.drawString(text, gp.getXForCenteredText(text, g2d), gp.getScreenHeight()/2);
    }
}
