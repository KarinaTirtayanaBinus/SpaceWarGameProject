package entity;

import main.GamePanel;

public class Boss extends Enemy {
    public Boss(GamePanel gp, float x, float y) {
        super(gp, x, y, BOSS_WIDTH , BOSS_HEIGHT, BOSS);

        initHitBox(x, y, BOSS_WIDTH, BOSS_HEIGHT);
    }
}
