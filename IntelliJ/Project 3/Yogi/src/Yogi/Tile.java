/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yogi;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 *
 * @author bli
 */
public class Tile implements Cloneable {
    public Image image;
    public boolean collsion = false;
    public int x, y, width, height;
    public int tileNum;
    public boolean collectable = false;

    public boolean collides(Player other) {
        if (collsion) {
            Rectangle rect = new Rectangle(x, y, width, height);
            Rectangle otherRect = other.solidArea;
            return rect.intersects(otherRect);
        }
        return false;
    }

    public Rectangle getBorder() {
        if (!collsion) {
            return new Rectangle(-1, -1, 0, 0);
        }
        return new Rectangle(x, y, width, height);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Tile clone = (Tile) super.clone();
        clone.image = image;
        return clone;
    }
}
