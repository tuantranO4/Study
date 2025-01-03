package org.GameLogic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speed;
    public String direction;

    public int spriteFrame = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
}
