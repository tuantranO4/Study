/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author bli
 */
public class Sprite {
    /**
     * The coordinates of the top left corner of the sprite
     */
    protected int x;
    protected int y; //coordinate
    protected int width;
    protected int height; //sprite dimension
    protected Image image; //loaded image object

    public Sprite(int x, int y, int width, int height, Image image) { //constructor
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    } //Graphic obj:  draws the sprite's image at position (x, y) with dimensions width x height
    
    /**
     * Returns true if this sprite collides with the other sprite
     * @param other
     * @return
     */ //JUST A Javadoc COMMENT

    public boolean collides(Sprite other) { //check collision
        Rectangle rect = new Rectangle(x, y, width, height); //separate built-in awt.Rectangle
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);        
        return rect.intersects(otherRect); //checks whether two rectangles overlap (intersect) in a 2D coordinate space
    }

    //get-set blocks
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
