package main;

import background.Background;
import bullets.Bullet;
import bullets.BulletManager;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = getTileSize() * getMaxScreenCol(); // 768px
    private final int screenHeight = getTileSize() * getMaxScreenRow(); // 576px
    private int fps = 60; // Game FPS
    private Background background = new Background(-getScreenHeight(), this);
    private KeyHandler keyH = new KeyHandler();
    private Thread gameThread;
    private Player player = new Player(this,keyH);
    private BulletManager bulletManager = new BulletManager(this, player.x, player.y);
    private int currFPS;
    private int bulletCounter = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0/ getFps();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                currFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
        background.update();
        bulletManager.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        background.draw(g2d);
        player.draw(g2d);
        bulletManager.draw(g2d);
        addBullets();

        g2d.dispose();
    }

    public void addBullets() {
        bulletCounter++;
        if(bulletCounter > 30) {
            bulletManager.addBullet(new Bullet(this, player.x, player.y+tileSize/2));
            bulletCounter = 0;
        }
    }

    public int getXForCenteredText(String text, Graphics g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return getScreenWidth() /2 - length/2;
    }

    public int getCurrFPS() {
        return currFPS;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getFps() {
        return fps;
    }
}
