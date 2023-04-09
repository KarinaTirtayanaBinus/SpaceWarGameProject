package entity;

import main.GamePanel;

public class Bat extends Enemy {
    public Bat(GamePanel gp, float x, float y) {
        super(gp, x, y, ENEMY_WIDTH*3/2, ENEMY_HEIGHT*3/2, BAT);

        initHitBox(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
    }
}
