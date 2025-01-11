package Yogi;

import java.io.File;
import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

import org.w3c.dom.css.Rect;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Enemy extends Sprite {
    GameEngine ge;
    int rectx;
    int recty;
    int rectWidth;
    int rectHeight;
    Rectangle lookArea = new Rectangle(0, 0, 0, 0);

    public Enemy(GameEngine ge, int startX, int startY, String direction) {
        this.ge = ge;
        rectWidth = ge.tileSize;
        rectHeight = ge.tileSize;
        getEnemyImage();
        setStartValue(startX, startY, direction);
        solidArea = new Rectangle(rectx, recty, rectHeight, rectWidth);
    }

    public void getEnemyImage() {
        try {
            up1 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_up_1.png"));
            up2 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_up_2.png"));
            down1 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_down_1.png"));
            down2 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_down_2.png"));
            left1 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_left_1.png"));
            left2 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_left_2.png"));
            right1 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_right_1.png"));
            right2 = ImageIO.read(new File("data\\Assets\\Characters\\Enemy\\oldman_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStartValue(int startX, int startY, String direction) {
        x = startX;
        y = startY;
        rectx = x;
        recty = y;
        speed = 1;
        this.direction = direction;
        switch (direction) {
            case "up":
                lookArea = new Rectangle(x, y - ge.tileSize, rectWidth, rectHeight * 2);
                break;
            case "down":
                lookArea = new Rectangle(x, y, rectWidth, rectHeight * 2);
                break;
            case "left":
                lookArea = new Rectangle(x - ge.tileSize, y, rectWidth * 2, rectHeight);
                break;
            case "right":
                lookArea = new Rectangle(x, y, rectWidth * 2, rectHeight);
                break;
        }

    }

    public void colided() {

    }

    public void update(Rectangle[] borders) {
        boolean colided = false;
        switch (this.direction) {
            case "right":
                if (x + speed > ge.tileSize * (ge.screenCol - 1)) {
                    direction = "left";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx + speed, recty, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "left";
                    break;
                }
                x += speed;
                rectx += speed;
                lookArea = new Rectangle(x, y, rectWidth * 2, rectHeight);
                break;
            case "left":
                if (x - speed < 0) {
                    direction = "right";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx - speed, recty, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "right";
                    break;
                }
                x -= speed;
                rectx -= speed;
                lookArea = new Rectangle(x - ge.tileSize, y, rectWidth * 2, rectHeight);
                break;
            case "down":
                if (y + speed > ge.tileSize * (ge.screenRow - 1)) {
                    direction = "up";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx, recty + speed, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "up";
                    break;
                }
                y += speed;
                recty += speed;
                lookArea = new Rectangle(x, y, rectWidth, rectHeight * 2);
                break;
            case "up":
                if (y - speed < 0) {
                    direction = "down";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectx, recty - speed, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "down";
                    break;
                }
                y -= speed;
                recty -= speed;
                lookArea = new Rectangle(x, y - ge.tileSize, rectWidth, rectHeight * 2);
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

    public Rectangle getLookRectangle() {
        return lookArea;
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
        // g2.fillRect(rectx, recty, rectWidth, rectHeight);
        // g2.fillRect((int) lookArea.getX(), (int) lookArea.getY(), (int)
        // lookArea.getWidth(),
        // (int) lookArea.getHeight());

        g2.drawImage(image, x, y, ge.tileSize, ge.tileSize, null);

    }
}
