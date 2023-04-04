package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;

    public BufferedImage up, down, left, right, def;
    public String direction;
    public int defCounter = 0;
}
