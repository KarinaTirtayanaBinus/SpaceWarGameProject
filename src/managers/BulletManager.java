package managers;

import entity.Entity;
import main.GamePanel;
import objects.Bullet;
import state.Playing;

import java.awt.*;
import java.util.ArrayList;

public class BulletManager {
    private GamePanel gp;
    private Playing playing;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Bullet bullet;

    public BulletManager(GamePanel gp, Playing playing) {
        this.gp = gp;
        this.playing = playing;
    }

    public void update(float xTarget, float yTarget, int targetType) {
        for(int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if(bullet.getY() < -gp.getTileSize() || bullet.getY() > gp.getScreenHeight()+gp.getTileSize()) {
                removeBullet(bullet);
            }
            if(targetType == Bullet.PLAYER && isHitPlayer(bullet.getX(), bullet.getY(), xTarget, yTarget)) {
                removeBullet(bullet);
                playing.setPlayerIsHit(true);
            } else if(targetType == Bullet.ENEMY && isHitEnemy(bullet.getX(), bullet.getY(), xTarget, yTarget)) {
                removeBullet(bullet);
                playing.setEnemyIsHit(true);
            }
            bullet.update();
        }
    }

    public boolean isHitPlayer(float xBullet, float yBullet, float xTarget, float yTarget) {
        if((yBullet >= yTarget) && (xBullet >= xTarget-gp.getTileSize()*3/4 && xBullet <= xTarget+gp.getTileSize()*3/4)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHitEnemy(float xBullet, float yBullet, float xTarget, float yTarget) {
        if((yBullet <= yTarget+50) && (xBullet >= xTarget-20 && xBullet <= xTarget+20)) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics2D g2) {
        for(int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            bullet.draw(g2);
        }
    }

    public void addBullet(Bullet block) {
        bullets.add(block);
    }

    public void removeBullet(Bullet block) {
        bullets.remove(block);
    }

    public void resetAllBullets() {
        bullets.removeAll(bullets);
    }

    public Bullet getBullet() {
        return bullet;
    }
}
