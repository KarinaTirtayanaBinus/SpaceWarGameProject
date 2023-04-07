package state;

import entity.Enemy;
import entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.Sound;
import managers.BulletManager;
import managers.EnemyManager;
import objects.Bullet;
import ui.Background;

import java.awt.*;

public class Playing{
    private GamePanel gp;
    private KeyHandler keyH;
    private Player player;
    private BulletManager bulletManager;
    private EnemyManager enemyManager;
    private Sound sound;
    private Background background;
    private Pause pauseScreen;
    private int bulletsCounter = 0, enemiesCounter = 0;
    private boolean isPaused = false;

    public Playing(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        player = new Player(gp, keyH);
        bulletManager = new BulletManager(gp, (int) player.getX(), (int) player.getY());
        enemyManager = new EnemyManager(gp);
        sound = new Sound();
        background = new Background(-gp.getScreenHeight(), gp, Background.PLAYING_BG);
        pauseScreen = new Pause(gp, this);
    }

    public void update() {
        if(!isPaused) {
            player.update();
            background.update();
            bulletManager.update();
            enemyManager.update();
        } else {
            pauseScreen.update();
        }
    }

    public void reset() {
        isPaused = false;
        enemyManager.resetAllEnemies();
        bulletManager.resetAllBullets();
        player.resetPosition();
        gp.getSound().stopSong();
    }

    public void draw(Graphics2D g2d) {
        background.draw(g2d);
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

    public Sound getSound() {
        return sound;
    }
}
