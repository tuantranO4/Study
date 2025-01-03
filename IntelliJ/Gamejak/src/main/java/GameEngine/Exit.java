package GameEngine;

import java.awt.*;

public class Exit extends Unit{
    public Image img;
    public boolean collision = true; // Always enable collision detection
    public boolean exit = false;
    public int x, y, width, height;
    public int unitNum;
    private Rectangle rect;

    public Exit() {
        rect = new Rectangle();
    }

    public boolean collides(PlayerSprite player) {
        updateRect();
        return rect.intersects(player.rect);
    }

    public boolean collidesWithDrake(Rectangle drakeRect) {
        updateRect();
        return rect.intersects(drakeRect);
    }

    private void updateRect() {
        rect.setBounds(x, y, width, height);
    }

    public Rectangle getBorder() {
        updateRect();
        return rect;
    }
}