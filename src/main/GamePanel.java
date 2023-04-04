package main;

import background.Background;
import objects.Bullet;
import managers.BulletManager;
import entity.Enemy;
import entity.Player;
import managers.EnemyManager;

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
    private int gameState;
    private final int playState = 1;
    private final int pauseState = 2;
    private Background background = new Background(-getScreenHeight(), this);
    private KeyHandler keyH = new KeyHandler(this);
    private Thread gameThread;
    private Player player = new Player(this,keyH);
    private BulletManager bulletManager = new BulletManager(this, (int) player.getX(), (int) player.getY());
    private EnemyManager enemyManager = new EnemyManager(this);
    private int currFPS;
    private int bulletsCounter = 0;
    private int enemiesCounter = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        startGameThread();
        setupGame();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame() {
        gameState = 1;
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
        switch (gameState) {
            case 1:{
                player.update();
                background.update();
                bulletManager.update();
                enemyManager.update();
                break;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        background.draw(g2d);
        bulletManager.draw(g2d);
        enemyManager.draw(g2d);
        player.draw(g2d);

        if(gameState == pauseState) {
            drawPause(g2d);
        } else {
            addBullets();
            addEnemies();
        }

        g2d.dispose();
    }

    public void drawPause(Graphics2D g2d) {
        String text = "PAUSED";
        int x, y = screenHeight/2;
        g2d.drawString(text, getXForCenteredText(text, g2d), y);
    }

    public void addBullets() {
        bulletsCounter++;
        if(bulletsCounter > 30) {
            bulletManager.addBullet(new Bullet(this, (int) player.getX(), (int) player.getY() +tileSize/2));
            bulletsCounter = 0;
        }
    }

    public void addEnemies() {
        enemiesCounter++;
        if(enemiesCounter > 50) {
            enemyManager.addEnemy(new Enemy(-tileSize*2, 100, 0, this));
            enemiesCounter = 0;
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

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getPlayState() {
        return playState;
    }

    public int getPauseState() {
        return pauseState;
    }

}
