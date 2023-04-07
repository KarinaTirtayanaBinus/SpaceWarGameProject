package state;

import entity.Enemy;
import entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.Sound;
import managers.BulletManager;
import managers.EnemyManager;
import objects.Bullet;
import ui.GameBackground;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Playing {
    private GamePanel gp;
    private KeyHandler keyH;
    private Player player;
    private BulletManager bulletManager;
    private EnemyManager enemyManager;
    private Sound sound;
    private GameBackground gameBackground;
    private Pause pauseScreen;
    private int bulletsCounter = 0, enemiesCounter = 0;
    private boolean isPaused = false;
    private GameState state;

    public Playing(GamePanel gp, KeyHandler keyH, GameState state) {
        this.gp = gp;
        this.keyH = keyH;
        player = new Player(gp, keyH);
        bulletManager = new BulletManager(gp, (int) player.getX(), (int) player.getY());
        enemyManager = new EnemyManager(gp);
        sound = new Sound();
        gameBackground = new GameBackground(-gp.getScreenHeight(), gp);
        pauseScreen = new Pause(gp);
        this.state = state;
    }

    public void update() {
        if(!isPaused) {
            player.update();
            gameBackground.update();
            bulletManager.update();
            enemyManager.update();
        } else {
            pauseScreen.update();
        }
    }

    public void draw(Graphics2D g2d) {
        gameBackground.draw(g2d);
        bulletManager.draw(g2d);
        enemyManager.draw(g2d);
        player.draw(g2d);

        if(isPaused) {
            pauseScreen.draw(g2d);
        } else {
            addBullets();
            addEnemies();
        }
    }

    public void addBullets() {
        bulletsCounter++;
        if(bulletsCounter > 30) {
            bulletManager.addBullet(new Bullet(gp, (int) player.getX(), (int) player.getY() +gp.getTileSize()/2));
            sound.playEffect(Sound.FIRE);
            bulletsCounter = 0;
        }
    }

    public void addEnemies() {
        enemiesCounter++;
        if(enemiesCounter > 50) {
            enemyManager.addEnemy(new Enemy(-gp.getTileSize()*2, 100, 0, gp));
            enemiesCounter = 0;
        }
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
}
