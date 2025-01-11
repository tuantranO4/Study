/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yogi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author bli
 */
public class Sprite {
    /**
     * The coordinates of the top left corner of the sprite
     */
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteFrame = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
}
