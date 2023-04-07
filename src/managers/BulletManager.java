package managers;

import main.GamePanel;
import objects.Bullet;

import java.awt.*;
import java.util.ArrayList;

public class BulletManager {
    GamePanel gp;
    private int x, y;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    Bullet bullet;

    public BulletManager(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;

        addBullet(new Bullet(gp, x, y));
    }

    public void update() {
        for(int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if(bullet.getY() < -gp.getTileSize()) {
                removeBullet(bullet);
            }
            bullet.update();
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
}
