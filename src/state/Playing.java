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
    private BulletManager playerBullet, enemyBullet;
    private EnemyManager enemyManager;
    private Sound sound;
    private Background background;
    private Pause pauseScreen;
    private GameOver gameOverScreen;
    private int bulletsCounterPlayer = 0, bulletsCounterEnemy = 0;
    private boolean isPaused = false, added = false;
    private int enemyType = 0;
    private boolean reset = false;
    private int bulletIndex = 1;
    private boolean playerIsHit = false, enemyIsHit = false;
    private boolean gameOver = false;
    private int gameScore = 0;

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
        gameOverScreen = new GameOver(gp, this);
    }

    public void buildPlayer() {
        int playerW = gp.getTileSize()*2;
        int playerH = gp.getTileSize()*2;
        float playerX = gp.getScreenWidth()/2 - playerW/2;
        float playerY = gp.getScreenHeight() - playerH;
        player = new Player(gp, playerX, playerY, playerW, playerH, keyH);
    }

    public void update() {
        if(gameOver){
            gameOverScreen.update();
        } else if(!isPaused) {
            player.update();
            background.update();
            enemyBullet.update(player.getX(), player.getY(), Bullet.PLAYER, 0);
            int j = 0;
            switch (enemyType) {
                case Enemy.ONE_EYE: {
                    for(int i = 0; i < enemyManager.getOneEyes().size(); i++) {
                        OneEye oneEye = enemyManager.getOneEyes().get(i);
                        playerBullet.update(oneEye.getHitBox().x, oneEye.getHitBox().y, Bullet.ENEMY, j);
                        if(enemyIsHit) {
                            oneEye.setCurrHealth(oneEye.getCurrHealth()-34);
                            enemyIsHit = false;
                        }
                        if(oneEye.getCurrHealth() <= 0) {
                            enemyManager.removeOneEye(oneEye);
                            gameScore += 20;
                        }
                        j++;
                    }
                    break;
                }
                case Enemy.BAT: {
                    for(int i = 0; i < enemyManager.getBats().size(); i++) {
                        Bat bat = enemyManager.getBats().get(i);
                        playerBullet.update(bat.getHitBox().x, bat.getHitBox().y, Bullet.ENEMY, j);
                        if(enemyIsHit) {
                            bat.setCurrHealth(bat.getCurrHealth()-20);
                            enemyIsHit = false;
                        }
                        if(bat.getCurrHealth() <= 0) {
                            enemyManager.removeBat(bat);
                            gameScore += 64;
                        }
                        j++;
                    }
                    break;
                }
                case Enemy.BOSS: {
                    for(int i = 0; i < enemyManager.getBosses().size(); i++) {
                        Boss boss = enemyManager.getBosses().get(i);
                        playerBullet.update(boss.getHitBox().x, boss.getHitBox().y, Bullet.ENEMY, 0);
                        if(enemyIsHit) {
                            boss.setCurrHealth(boss.getCurrHealth()-10);
                            enemyIsHit = false;
                        }
                        if(boss.getCurrHealth() <= 0) {
                            enemyManager.removeBoss(boss);
                            gameScore += 500;
                        }
                    }
                    break;
                }
            }
            if(playerIsHit) {
                player.changeHealth(-2);
                playerIsHit = false;
            }
            if(player.getCurrHealth() == 0) {
                gameOver = true;
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
                gameOver = true;
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

        gameOver = false;
        isPaused = false;
        reset = true;
        enemyType = 0;
        player.setCurrHealth(100);
        added = false;
        addEnemies(enemyType);
        gameScore = 0;
    }

    public void draw(Graphics2D g2d) {
        background.draw(g2d);
        playerBullet.draw(g2d);
        enemyBullet.draw(g2d);
        enemyManager.draw(g2d);
        player.draw(g2d);
        addEnemies(enemyType);

        g2d.setFont(new Font("OCR A Extended", Font.ITALIC, 36));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + gameScore, gp.getXForCenteredText("Score: " + gameScore, g2d), 40);

        if(gameOver) {
            gameOverScreen.draw(g2d);
        } else if(isPaused) {
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

    public Background getBackground() {
        return background;
    }

    public GameOver getGameOverScreen() {
        return gameOverScreen;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getGameScore() {
        return gameScore;
    }

}
