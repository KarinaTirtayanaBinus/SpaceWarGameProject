package state;

import entity.*;
import main.GamePanel;
import system.KeyHandler;
import system.Sound;
import managers.BulletManager;
import managers.EnemyManager;
import objects.Bullet;
import ui.Background;

import java.awt.*;

public class Playing{
    private GamePanel gp;
    private KeyHandler keyH;
    private Player player;
    private BulletManager playerBullet;
    private BulletManager enemyBullet;
    private EnemyManager enemyManager;
    private Sound sound;
    private Background background;
    private Pause pauseScreen;
    private int bulletsCounterPlayer = 0;
    private int bulletsCounterEnemy = 0;
    private boolean isPaused = false, added = false;
    private int colX = 1, rowY = 1;
    private int enemyType = 0;
    private boolean reset = false;
    private int bulletIndex = 1;
    private boolean playerIsHit = false;
    private boolean enemyIsHit = false;

    public Playing(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        initClasses();
    }

    private void initClasses() {
        buildPlayer();
        playerBullet = new BulletManager(gp, this);
        enemyBullet = new BulletManager(gp, this);
        enemyManager = new EnemyManager(gp, this);
        sound = new Sound();
        background = new Background(-gp.getScreenHeight(), gp, Background.PLAYING_BG);
        pauseScreen = new Pause(gp, this);
    }

    public void buildPlayer() {
        int playerW = gp.getTileSize()*2;
        int playerH = gp.getTileSize()*2;
        float playerX = gp.getScreenWidth()/2 - playerW/2;
        float playerY = gp.getScreenHeight() - playerH;
        player = new Player(gp, playerX, playerY, playerW, playerH, keyH);
    }

    public void update() {
        if(!isPaused) {
            player.update();
            background.update();
            switch (enemyType) {
                case Enemy.ONE_EYE: {
                    for(int i = 0; i < enemyManager.getOneEyes().size(); i++) {
                        OneEye oneEye = enemyManager.getOneEyes().get(i);
                        playerBullet.update(oneEye.getHitBox().x, oneEye.getHitBox().y, Bullet.ENEMY);
                        if(enemyIsHit) {
                            oneEye.setCurrHealth(oneEye.getCurrHealth()-100);
                            enemyIsHit = false;
                        }
                        if(oneEye.getCurrHealth() == 0) {
                            enemyManager.removeOneEye(oneEye);
                        }
                    }
                    break;
                }
                case Enemy.BAT: {
                    for(int i = 0; i < enemyManager.getBats().size(); i++) {
                        Bat bat = enemyManager.getBats().get(i);
                        playerBullet.update(bat.getHitBox().x, bat.getHitBox().y, Bullet.ENEMY);
                        if(enemyIsHit) {
                            bat.setCurrHealth(bat.getCurrHealth()-100);
                            enemyIsHit = false;
                        }
                        if(bat.getCurrHealth() == 0) {
                            enemyManager.removeBat(bat);
                        }
                    }
                    playerBullet.getBullet().setPlayerBulletSpeed(2);
                    break;
                }
                case Enemy.BOSS: {
                    for(int i = 0; i < enemyManager.getBosses().size(); i++) {
                        Boss boss = enemyManager.getBosses().get(i);
                        playerBullet.update(boss.getHitBox().x, boss.getHitBox().y, Bullet.ENEMY);
                        if(enemyIsHit) {
                            boss.setCurrHealth(boss.getCurrHealth()-5);
                            enemyIsHit = false;
                        }
                        if(boss.getCurrHealth() == 0) {
                            enemyManager.removeBoss(boss);
                        }
                    }
                    playerBullet.getBullet().setPlayerBulletSpeed(10);
                    break;
                }
            }
            enemyBullet.update(player.getX(), player.getY(), Bullet.PLAYER);
            if(playerIsHit) {
                player.setCurrHealth(player.getCurrHealth()-5);
                playerIsHit = false;
            }
            enemyManager.update();
        } else {
            pauseScreen.update();
        }

    }

    public void checkEnemies() {
        if(enemyManager.isEnemyClear() && !reset) {
            enemyType++;
            if(enemyType == 3) {
                gp.setState(GameState.GAME_OVER);
            } else {
                added = false;
            }
        } else if(reset) {
            reset = false;
        }
    }

    public void reset() {
        enemyManager.resetAllEnemies();
        playerBullet.resetAllBullets();
        enemyBullet.resetAllBullets();
        player.resetPosition();
        gp.getSound().stopSong();

        isPaused = false;
        reset = true;
        enemyType = 0;
        added = false;
        addEnemies(enemyType);
    }

    public void draw(Graphics2D g2d) {
        background.draw(g2d);
        playerBullet.draw(g2d);
        enemyBullet.draw(g2d);
        enemyManager.draw(g2d);
        player.draw(g2d);
        addEnemies(enemyType);

        if(isPaused) {
            pauseScreen.draw(g2d);
        } else {
            addBulletsPlayer();
            addBulletsEnemy();
        }
    }

    public void addBulletsPlayer() {
        bulletsCounterPlayer++;
        if(bulletsCounterPlayer > 30) {
            playerBullet.addBullet(new Bullet(gp, (int) player.getX(), (int) player.getY() +gp.getTileSize()/2, Bullet.PLAYER));
            sound.playEffect(Sound.FIRE);
            bulletsCounterPlayer = 0;
        }
    }

    public void addBulletsEnemy() {
        bulletsCounterEnemy++;
        if(bulletsCounterEnemy > 50) {
            int i = 1;
            if(bulletIndex == -1) {
                bulletIndex = 1;
            }
            switch (enemyType) {
                case Enemy.ONE_EYE: {
                    for(int j = 0; j < enemyManager.getOneEyes().size(); j++) {
                        OneEye oneEye = enemyManager.getOneEyes().get(j);
                        if(i%2 == bulletIndex && oneEye != null) {
                            enemyBullet.addBullet(new Bullet(gp, (int) oneEye.getHitBox().x, (int) oneEye.getHitBox().y+gp.getTileSize(), Bullet.ENEMY));
                        }
                        i++;
                    }
//                    for(OneEye oneEye: enemyManager.getOneEyes()) {
//                    }
                    break;
                }
                case Enemy.BAT: {
                    for(Bat bat: enemyManager.getBats()) {
                        if(i%2 == bulletIndex) {
                            enemyBullet.addBullet(new Bullet(gp, (int) bat.getHitBox().x+gp.getTileSize()*2/3, (int) bat.getHitBox().y+gp.getTileSize()*3/2, Bullet.ENEMY));
                        }
                        i++;
                    }
                    break;
                }
                case Enemy.BOSS: {
                    for(Boss boss: enemyManager.getBosses()) {
                        enemyBullet.addBullet(new Bullet(gp, (int) boss.getHitBox().x, (int) boss.getHitBox().y+gp.getTileSize(), Bullet.BOSS));
                    }
                    break;
                }
            }

            bulletIndex--;
            bulletsCounterEnemy = 0;
        }
    }

    public void addEnemies(int enemyType) {
        switch (enemyType) {
            case Enemy.ONE_EYE: {
                if(!added) {
                    for(int i = 1; i <= 5; i++) {
                        for(int j = 1; j <= 5; j++) {
                            if(j == i || j == 6-i) {
                                enemyManager.addOneEye((gp.getTileSize()*7/3) * j, 25 * i);
                            }
                        }
                    }
                    added = true;
                }
                break;
            }
            case Enemy.BAT: {
                if(!added) {
                    for(int i = 1; i <= 3; i++) {
                        for(int j = i; j <= 5; j++) {
                            if(j == i || j == 6-i) {
                                enemyManager.addBat((gp.getTileSize()*7/3) * j, 35 * i);
                            }
                        }
                    }
                    added = true;
                }
                break;
            }
            case Enemy.BOSS: {
                if(!added) {
                    enemyManager.addBoss(gp.getScreenWidth()/2 - gp.getTileSize(), gp.getScreenHeight()/2 - gp.getTileSize()*3);
                    added = true;
                }
                break;
            }
        }

        checkEnemies();
    }

    public void unpauseGame() {
        isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public Pause getPauseScreen() {
        return pauseScreen;
    }

    public void setPlayerIsHit(boolean playerIsHit) {
        this.playerIsHit = playerIsHit;
    }

    public void setEnemyIsHit(boolean enemyIsHit) {
        this.enemyIsHit = enemyIsHit;
    }
}
