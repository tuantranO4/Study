package GameEngine;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PlayerSprite extends Sprite {
    GameEngine ge;
    int rectX;
    int rectY;
    int rectWidth;
    int rectHeight; //collision dim
    int live;
    Image image=null;

    public PlayerSprite(GameEngine ge) {
        this.ge = ge;
        setPlImg();
        startPlayer();
        int live =1; //live once!!!
        rectWidth = 30;
        rectHeight = 20;
        rect = new Rectangle(rectX, rectY, rectHeight, rectWidth);
    }

    public PlayerSprite playerReset() {
        startPlayer();
        rect = new Rectangle(rectX, rectY, rectHeight, rectWidth);
        live--;
        return this;
    }

    public void startPlayer() {
        x = 0;
        y = 9;
        rectX = x + (5);
        rectY = y + (30);
        speed = 3;
        direction = "up";
    }


    public void setPlImg() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/main/resources/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image player debug msg.");
        }
    }

    public boolean collision(Rectangle[] rects) {
        for (Rectangle otherrect : rects) {
            if (rect.intersects(otherrect)) {
                return true;
            }
        }
        return false;
    }

    public void update(int dir, Rectangle[] borders) {
        boolean collision = false;
        switch (dir) {
            case 1: //r
                if (rectX + speed > ge.tileSize * (ge.screenCol - 1)) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX + speed, rectY, rectWidth, rectHeight))) {
                        collision = true;
                    }
                }
                if (collision) {
                    break;
                }
                direction = "right";
                x += speed;
                rectX += speed;
                break;
            case 2: //l
                if (rectX - speed < 0) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX - speed, rectY, rectWidth, rectHeight))) {
                        collision = true;
                    }
                }
                if (collision) {
                    break;
                }
                direction = "left";
                x -= speed;
                rectX -= speed;
                break;
            case 3: //d
                if (rectY - rectHeight + speed > ge.tileSize * (ge.screenRow - 1)) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX, rectY + speed, rectWidth, rectHeight))) {
                        collision = true;
                    }
                }
                if (collision) {
                    break;
                }
                direction = "down";
                y += speed;
                rectY += speed;
                break;
            case 4: //u
                if (rectY - speed < 0) {
                    break;
                }
                for (Rectangle border : borders) {
                    if (border.intersects(new Rectangle(rectX, rectY - speed, rectWidth, rectHeight))) {
                        collision = true;
                    }
                }
                if (collision) {
                    break;
                }
                direction = "up";
                y -= speed;
                rectY -= speed;
                break;
        }
    }

    public void draw(Graphics2D g2) {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/main/resources/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image player debug msg.");
        }

        rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);

        g2.setColor(Color.black);
        g2.drawRect(rectX, rectY, rectWidth, rectHeight);

        if (image != null) {
            g2.drawImage(image, x, y, ge.tileSize, ge.tileSize, null);
        } else {
            g2.setColor(Color.red);
            g2.fillRect(x, y, ge.tileSize, ge.tileSize);
        }
    }

}
