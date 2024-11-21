package gamestate;

import enums.Direction;
import enums.BoardStatus;

public class Player {
    private int score;
    public Player() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public int increaseScore(){
        score++;
        return score;
    }
}
