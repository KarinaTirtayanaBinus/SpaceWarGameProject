package managers;

import entity.Enemy;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EnemyManager {
    GamePanel gp;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    public EnemyManager(GamePanel gp) {
        this.gp = gp;
        enemyImgs = new BufferedImage[4];

        getEnemyImgs();
    }

    public void getEnemyImgs() {
        try{
            enemyImgs[0] = ImageIO.read(new FileInputStream("res/enemy/enemy1.png"));
            enemyImgs[1] = ImageIO.read(new FileInputStream("res/enemy/enemy2.png"));
            enemyImgs[2] = ImageIO.read(new FileInputStream("res/enemy/enemy3.png"));
            enemyImgs[3] = ImageIO.read(new FileInputStream("res/enemy/enemy4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        for(int i = 0; i < enemies.size(); i++) {
            Enemy currEnemy = enemies.get(i);
            currEnemy.setAction();
            if(currEnemy.getY() < -gp.getTileSize()*4 || currEnemy.getY() > gp.getScreenHeight()+gp.getTileSize()*4 || currEnemy.getX() < -gp.getTileSize()*4 || currEnemy.getX() > gp.getScreenWidth()+gp.getTileSize()*4) {
                removeEnemy(currEnemy);
            }

        }
    }

    public void draw(Graphics2D g2d) {
        for(int i = 0; i < enemies.size(); i++) {
            drawEnemy(enemies.get(i), g2d);
        }
    }

    public void drawEnemy(Enemy enemy, Graphics2D g2d) {
        g2d.drawImage(enemyImgs[0], (int) enemy.getX(), (int) enemy.getY(), gp.getTileSize()*3/2, gp.getTileSize()*3/2, null);
    }

    public void addEnemy(Enemy newEnemy) {
        enemies.add(newEnemy);
    }

    public void removeEnemy(Enemy delEnemy) {
        enemies.remove(delEnemy);
    }

    public void resetAllEnemies() {
        enemies.removeAll(enemies);
    }
}
