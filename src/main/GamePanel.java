package main;

import state.GameState;
import state.Playing;
import ui.GameBackground;
import objects.Bullet;
import managers.BulletManager;
import entity.Enemy;
import entity.Player;
import managers.EnemyManager;
import state.MainMenu;
import state.Pause;

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
    private int fps = 60;

    // GAME STATE
    private GameState state;

    // GAME SCREEN
    private MainMenu menuScreen = new MainMenu(this);
    private Playing playingScreen;

    // SYSTEM
    private KeyHandler keyH = new KeyHandler(this);
    private Thread gameThread;
    private Sound sound = new Sound();

    private int currFPS;
    private int bulletsCounter = 0;
    private int enemiesCounter = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.addMouseMotionListener(keyH);
        this.setFocusable(true);

        startGameThread();
        setupGame();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame() {
        state = GameState.MENU;
        playingScreen = new Playing(this, keyH, state);
        sound.playSong(Sound.MENU);
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
        switch (state) {
            case MENU: {
                menuScreen.update();
                break;
            }
            case PLAYING: {
                playingScreen.update();
                break;
            }
            case QUIT: {
                System.exit(0);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        switch (state) {
            case MENU: {
                menuScreen.drawMenu(g2d);
                break;
            }
            case PLAYING: {
                playingScreen.draw(g2d);
                break;
            }
        }

        g2d.dispose();
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

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public MainMenu getMenuScreen() {
        return menuScreen;
    }

    public Sound getSound() {
        return sound;
    }

    public Playing getPlayingScreen() {
        return playingScreen;
    }
}
