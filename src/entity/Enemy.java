package entity;

import main.GamePanel;

public abstract class Enemy extends Entity {
    // CONSTANTS
    public static final int ONE_EYE = 0;
    public static final int BAT = 1;
    public static final int BOSS = 2;
    public static final int IDLE = 0;
    public static final int RUNNING = 1;
    public static final int ATTACK = 2;
    public static final int DEAD = 3;
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 65;
    public static final int BOSS_DEFAULT_WIDTH = 55;
    public static final int BOSS_DEFAULT_HEIGHT = 89;
    public static final int BOSS_WIDTH = BOSS_DEFAULT_WIDTH*2;
    public static final int BOSS_HEIGHT = BOSS_DEFAULT_HEIGHT*2;

    // ATTRIBUTES
    protected int enemyType;
    protected int enemyState;
    protected boolean firstUpdate = true;
    protected boolean outsideScreen = false;
    protected boolean active = true;
    protected boolean attackChecked;
    protected int attackBoxOffsetX;
    private int aniTick, aniIndex, aniSpeed = 25;
    private float gravity = 0.04f;

    public Enemy(GamePanel gp, float x, float y, int width, int height, int enemyType) {
        super(gp, x, y, width, height);
        this.enemyType = enemyType;
        speed = 1;
        moveDir = RIGHT;

        initHitBox(x, y, width, height);
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= 4) {
                aniIndex = 0;
            }
        }
    }

    public void update() {
        updateMove();
        updateAnimationTick();
    }

    private void updateMove() {
        switch (enemyState) {
            case IDLE: {
                enemyState = RUNNING;
            }
            case RUNNING: {
                if(moveDir == LEFT) {
                    hitBox.x -= speed;
                } else {
                    hitBox.x += speed;
                }
                changeMoveDir();
                break;
            }
        }
    }

    private void changeMoveDir() {
        if(moveDir == RIGHT && hitBox.x > x+gp.getTileSize()) {
            moveDir = LEFT;
        } else if(moveDir == LEFT && hitBox.x < x-gp.getTileSize()) {
            moveDir = RIGHT;
        }
    }

    public int getEnemyState() {
        return enemyState;
    }

    public int getAniIndex() {
        return aniIndex;
    }
}
