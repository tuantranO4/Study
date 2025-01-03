package GameEngine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class DragonSprite extends Sprite {
    GameEngine ge;
    int rectX;
    int rectY;
    int rectWidth;
    int rectHeight;
    Image image=null;
    Rectangle deadZone = new Rectangle(0, 0, 1, 1);

    public DragonSprite(GameEngine ge, int startX, int startY, String direction) {
        this.ge = ge;
        rectWidth = ge.tileSize;
        rectHeight = ge.tileSize;
        setDrakeImg();
        startDrake(startX, startY, direction);
        rect = new Rectangle(rectX, rectY, rectHeight, rectWidth);
    }

    public Rectangle getDZ() {
        return deadZone;
    }

    public void setDrakeImg() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/main/resources/dragon.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image player debug msg.");
        }
    }

    public void startDrake(int startX, int startY, String dir) {
        x = startX;
        y = startY;
        rectX = x;
        rectY = y;
        speed = 1;
        this.direction = dir;
        switch (dir) {
            case "up":
                deadZone = new Rectangle(x, y - ge.tileSize, rectWidth * 2, rectHeight * 2);
                break;
            case "down":
                deadZone = new Rectangle(x, y, rectWidth * 2, rectHeight * 2);
                break;
            case "left":
                deadZone = new Rectangle(x - ge.tileSize, y, rectWidth * 2, rectHeight * 2);
                break;
            case "right":
                deadZone = new Rectangle(x, y, rectWidth * 2, rectHeight * 2);
                break;
        }
    }

    public void checkCollisionDrake(Rectangle[] borders) {
        boolean colided = false;
        switch (this.direction) {
            case "right":
                if (x + speed > ge.tileSize * (ge.screenCol - 1)) {
                    direction = "left";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX + speed, rectY, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "left";
                    break;
                }
                x += speed;
                rectX += speed;
                deadZone = new Rectangle(x, y, rectWidth * 2, rectHeight);
                break;
            case "left":
                if (x - speed < 0) {
                    direction = "right";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX - speed, rectY, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "right";
                    break;
                }
                x -= speed;
                rectX -= speed;
                deadZone = new Rectangle(x - ge.tileSize, y, rectWidth * 2, rectHeight);
                break;
            case "down":
                if (y + speed > ge.tileSize * (ge.screenRow - 1)) {
                    direction = "up";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX, rectY + speed, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "up";
                    break;
                }
                y += speed;
                rectY += speed;
                deadZone = new Rectangle(x, y, rectWidth, rectHeight * 2);
                break;
            case "up":
                if (y - speed < 0) {
                    direction = "down";
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX, rectY - speed, rectWidth, rectHeight))) {
                        colided = true;
                    }
                }
                if (colided) {
                    direction = "down";
                    break;
                }
                y -= speed;
                rectY -= speed;
                deadZone = new Rectangle(x, y - ge.tileSize, rectWidth, rectHeight * 2);
                break;
        }
    }
    public void draw(Graphics2D g2) {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/main/resources/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image drake debug msg.");
        }

        rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
        g2.setColor(Color.red);
        g2.drawRect(rectX, rectY, rectWidth, rectHeight);
        if (image != null) {
            g2.drawImage(image, x, y, ge.tileSize, ge.tileSize, null);
        } else {
            g2.setColor(Color.red);
            g2.fillRect(x, y, ge.tileSize, ge.tileSize);
        }
    }
}
