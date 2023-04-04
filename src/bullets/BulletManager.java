package bullets;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class BulletManager {
    GamePanel gp;
    private int x, y;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    Bullet tempBullet;

    public BulletManager(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;

        addBullet(new Bullet(gp, x, y));
    }

    public void update() {
        for(int i = 0; i < bullets.size(); i++) {
            tempBullet = bullets.get(i);
            if(tempBullet.getY() < -gp.getTileSize()) {
                removeBullet(tempBullet);
            }
            tempBullet.update();
        }
    }

    public void draw(Graphics2D g2) {
        for(int i = 0; i < bullets.size(); i++) {
            tempBullet = bullets.get(i);
            tempBullet.draw(g2);
        }
    }

    public void addBullet(Bullet block) {
        bullets.add(block);
    }

    public void removeBullet(Bullet block) {
        bullets.remove(block);
    }
}
