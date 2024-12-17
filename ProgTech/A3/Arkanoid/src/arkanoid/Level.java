/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.ImageIcon;

/**
 *
 * @author bli
 */
public class Level {

    // each brick is 40x20, so there can be at most 20 bricks side by side
    // the last 10 rows will be empty, so there can be at most 20 rows of bricks
    private final int BRICK_WIDTH = 40;
    private final int BRICK_HEIGHT = 20;
    ArrayList<Brick> bricks; //array list of brick object (aka sprite enheritance)

    public Level(String levelPath) throws IOException {
        loadLevel(levelPath);
    } //struct

    public void loadLevel(String levelPath) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(levelPath)); //open file tor read
        bricks = new ArrayList<>(); //ArrayList to store all the Brick objects for this level

        int y = 0; // Tracks the row number (used for vertical positioning of bricks)

        String line;
        while ((line = br.readLine()) != null) { //if the file is null, loop end

            int x = 0; //Tracks the column number (used for horizontal positioning of bricks)

            for (char blockType : line.toCharArray()) {  //"1111" -> toCharArray() gives ['1', '1', '1', '1']
                if (Character.isDigit(blockType)) { //eg: read brick type 1 2 3
                    Image image = new ImageIcon("data/images/brick0" + blockType + ".png").getImage(); //load image eg: data/images/brick02.png
                    bricks.add(new Brick(x * BRICK_WIDTH, y * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT, image)); //set x,y position for brick (fixed wid,hei)
                }
                x++;

            }
            y++;
        }
    } //read a text file that specifies the layout of bricks for this level

    public boolean collides(Ball ball) { //method is invoked repeatedly in the game loop
        Brick collidedWith = null; //init collidedWith
        for (Brick brick : bricks) {
            if (ball.collides(brick)) {
                collidedWith = brick; //check collision with collidedWith flag
                break;
            }
        }
        if (collidedWith != null) {
            bricks.remove(collidedWith); //if collide, remove from the ArrayList (arkanoid, duh)
            return true;
        } else {
            return false;
        } //not
    }
    
    public boolean isOver() {
        return bricks.isEmpty();
    } //if arrayList is empty -> end game

    public void draw(Graphics g) {
        for (Brick brick : bricks) {
            brick.draw(g);
        }
    }

}
