package entity;

import main.GamePanel;

public class OneEye extends Enemy {

    public OneEye(GamePanel gp, float x, float y) {
        super(gp, x, y, ENEMY_WIDTH, ENEMY_HEIGHT, ONE_EYE);

        initHitBox(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
    }


}
