package org.gamelogic;

public enum Direction {
    DOWN(0, 1), LEFT(-1, 0), UP(0, -1), RIGHT(1, 0);
    public final int x, y;
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
}

