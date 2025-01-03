package GameEngine;

import java.awt.*;

public class Unit {
    public Image img;
    public boolean collision = false;
    public int x, y, width, height;
    public int unitNum;


    public boolean collides(PlayerSprite other) {
        if (collision) {
            Rectangle rect = new Rectangle(x, y, width, height);
            Rectangle otherRect = other.rect;
            return rect.intersects(otherRect);
        }
        return false;
    }

    public Rectangle getBorder() {
        if (!collision) {
            return new Rectangle(-1, -1, 0, 0);
        }
        return new Rectangle(x, y, width, height);
    }
}
