package managers;

import entity.Bat;
import entity.Boss;
import entity.OneEye;
import main.GamePanel;
import state.Playing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EnemyManager {
    private GamePanel gp;
    private Playing playing;
    private BufferedImage[] oneEyeArr;
    private BufferedImage[] batArr;
    private BufferedImage[] bossArr;
    private ArrayList<OneEye> oneEyes = new ArrayList<>();
    private ArrayList<Bat> bats = new ArrayList<>();
    private ArrayList<Boss> bosses = new ArrayList<>();

    public EnemyManager(GamePanel gp, Playing playing) {
        this.playing = playing;
        this.gp = gp;

        loadEnemyImgs();
    }

    private void loadEnemyImgs() {
        oneEyeArr = new BufferedImage[5];
        batArr = new BufferedImage[5];
        bossArr = new BufferedImage[4];

        try{
            BufferedImage temp = ImageIO.read(new FileInputStream("res/enemy/oneEyeSprite.png"));
            for(int i = 0; i < oneEyeArr.length; i++) {
                oneEyeArr[i] = temp.getSubimage(10+i*OneEye.ENEMY_WIDTH, 0, OneEye.ENEMY_WIDTH, OneEye.ENEMY_HEIGHT);
            }

            temp = ImageIO.read(new FileInputStream("res/enemy/batSprite.png"));
            for(int i = 0; i < batArr.length; i++) {
                batArr[i] = temp.getSubimage(i*Bat.ENEMY_WIDTH, 0, Bat.ENEMY_WIDTH, Bat.ENEMY_HEIGHT);
            }

            temp = ImageIO.read(new FileInputStream("res/enemy/bossSprite.png"));
            for(int i = 0; i < bossArr.length; i++) {
                bossArr[i] = temp.getSubimage(i* Boss.BOSS_DEFAULT_WIDTH, 0, Boss.BOSS_DEFAULT_WIDTH, Boss.BOSS_DEFAULT_HEIGHT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        for(OneEye oneEye: oneEyes) {
            oneEye.update();
        }

        for(Bat bat: bats) {
            bat.update();
        }

        for(Boss boss: bosses) {
            boss.update();
        }
    }

    public void draw(Graphics2D g2d) {
        drawOneEye(g2d);
        drawBat(g2d);
        drawBoss(g2d);
    }

    public void drawOneEye(Graphics2D g2d) {
        for(OneEye oneEye: oneEyes) {
            g2d.drawImage(oneEyeArr[oneEye.getAniIndex()], (int) oneEye.getHitBox().x, (int) oneEye.getHitBox().y, OneEye.ENEMY_WIDTH, OneEye.ENEMY_HEIGHT, null);
        }
    }

    public void drawBat(Graphics2D g2d) {
        for(Bat bat: bats) {
            g2d.drawImage(batArr[bat.getAniIndex()], (int) bat.getHitBox().x, (int) bat.getHitBox().y, Bat.ENEMY_WIDTH*3/2, Bat.ENEMY_HEIGHT*3/2, null);
        }
    }

    public void drawBoss(Graphics2D g2d) {
        for(Boss boss: bosses) {
            g2d.drawImage(bossArr[boss.getAniIndex()], (int) boss.getHitBox().x, (int) boss.getHitBox().y, Boss.BOSS_WIDTH, Boss.BOSS_HEIGHT, null);
        }
    }

    public void addOneEye(float x, float y) {
        oneEyes.add(new OneEye(gp, x, y));
    }

    public void removeOneEye(OneEye oneEye) {
        oneEyes.remove(oneEye);
    }

    public void addBat(float x, float y) {
        bats.add(new Bat(gp, x, y));
    }

    public void removeBat(Bat bat) {
        bats.remove(bat);
    }

    public void addBoss(float x, float y) {
        bosses.add(new Boss(gp, x, y));
    }

    public void removeBoss(Boss boss) {
        bosses.remove(boss);
    }

    public void resetAllEnemies() {
        oneEyes.removeAll(oneEyes);
        bats.removeAll(bats);
    }

    public boolean isEnemyClear() {
        if(oneEyes.size() == 0 && bats.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<OneEye> getOneEyes() {
        return oneEyes;
    }

    public ArrayList<Bat> getBats() {
        return bats;
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }
}
