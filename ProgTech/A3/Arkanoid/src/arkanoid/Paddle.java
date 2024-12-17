/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.awt.Image;

/**
 *
 * @author bli
 */
public class Paddle extends Sprite { //inhertance of Sprite.java

    private double velx;

    public Paddle(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    } //remained

    public void move() { //new method of paddle
        if ((velx < 0 && x > 0) || (velx > 0 && x + width <= 800)) {
            x += velx; //x+velocity
        }
    }
    //get-set velx (velocity)
    public double getVelx() {
        return velx;
    }

    public void setVelx(double velx) {
        this.velx = velx;
    }
}
