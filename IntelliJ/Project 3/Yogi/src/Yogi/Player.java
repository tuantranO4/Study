/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yogi;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author bli
 */
public class Player extends Sprite {
    GameEngine ge;
    int rectx;
    int recty;
    int rectWidth;
    int rectHeight;
    int lives;

    public Player(GameEngine ge) {
        this.ge = ge;
        setStartValue();
        getPlayerImage();
        lives = 3;
        rectWidth = 35;
        rectHeight = 18;
        solidArea = new Rectangle(rectx, recty, rectHeight, rectWidth);
    }

    public Player playerReset() {
        setStartValue();
        solidArea = new Rectangle(rectx, recty, rectHeight, rectWidth);
        lives--;
        return this;
    }

    public void getPlayerImage() {
        try {

            up1 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\upMove1.png"));
            up2 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\upMove2.png"));
            down1 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\downMove1.png"));
            down2 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\downMove2.png"));
            left1 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\leftMove1.png"));
            left2 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\leftMove2.png"));
            right1 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\rightMove1.png"));
            right2 = ImageIO
                    .read(new File("data\\Assets\\Characters\\Character\\rightMove2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStartValue() {
        x = 0;
        y = 0;
        rectx = x + (8);
        recty = y + (33);
        speed = 4;
        direction = "down";
    }

    public boolean collisionDetect(Rectangle[] borders) {
        for (Rectangle border : borders) {
            if (solidArea.intersects(border)) {
                return true;
            }
        }
        return false;
    }

    public void update(int dir, Rectangle[] borders) {
        boolean colided = false;
        switch (dir) {
            case 1:
                if (rectx + speed > ge.tileSize * (ge.screenCol - 1)) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx + speed, recty, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    break;
                }
                direction = "right";
                x += speed;
                rectx += speed;
                break;
            case 2:
                if (rectx - speed < 0) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx - speed, recty, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    break;
                }
                direction = "left";
                x -= speed;
                rectx -= speed;
                break;
            case 3:
                if (recty - rectHeight + speed > ge.tileSize * (ge.screenRow - 1)) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx, recty + speed, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    break;
                }
                direction = "down";
                y += speed;
                recty += speed;
                break;
            case 4:
                if (recty - speed < 0) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx, recty - speed, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    break;
                }
                direction = "up";
                y -= speed;
                recty -= speed;
                break;
        }
        spriteFrame++;
        if (spriteFrame > 20) {
            if (spriteNum == 1) {
                spriteNum++;
            } else if (spriteNum == 2) {
                spriteNum--;
            }
            spriteFrame = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        solidArea = new Rectangle(rectx, recty, rectWidth, rectHeight);
        g2.setColor(Color.white);
        g2.fillRect(rectx, recty, rectWidth, rectHeight);
        g2.drawImage(image, x, y, ge.tileSize, ge.tileSize, null);
    }
}
