package ui;

import main.GamePanel;
import main.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class MenuButton {
    private GamePanel gp;
    private int x, y, index, row;
    private GameState state;
    private BufferedImage[] buttons;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    public static final int BTN_WIDTH = 300;
    public static final int BTN_HEIGHT = 70;
    private String[][] imgsName = {{"playButton.jpg", "playButtonHover.jpg", "playButtonClicked.jpg"},
            {"optionButton.jpg", "optionButtonHover.jpg", "optionButtonClicked.jpg"},
            {"quitButton.jpg", "quitButtonHover.jpg", "quitButtonClicked.jpg"}};

    public MenuButton(int x, int y, int row, GameState state, GamePanel gp) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.state = state;
        this.row = row;

        loadButtonImgs();
        initBounds();
        resetBools();
    }

    public void loadButtonImgs() {
        buttons = new BufferedImage[3];
        try{
            for(int i = 0; i < buttons.length; i++) {
                buttons[i] = ImageIO.read(new FileInputStream("res/buttons/" + imgsName[row][i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initBounds() {
        bounds = new Rectangle(x, y, BTN_WIDTH, BTN_HEIGHT);
    }

    public void update() {
        if(mousePressed) {
            index = 2;
        } else if(mouseOver) {
            index = 1;
        } else {
            index = 0;
        }
    }

    public void applyGameState() {
        gp.setState(state);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(buttons[index], x, y, BTN_WIDTH, BTN_HEIGHT, null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
