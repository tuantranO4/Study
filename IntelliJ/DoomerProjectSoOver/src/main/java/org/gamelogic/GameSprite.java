package org.gamelogic;

import java.awt.*;

public class GameSprite extends Sprite {
    private Direction currentDirection = null;
    private int speed = 4;

    public GameSprite(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    public void move() {
        if (currentDirection != null) {
            x += currentDirection.x * speed;
            y += currentDirection.y * speed;
        }
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void stopMoving() {
        this.currentDirection = null;
    }
}
