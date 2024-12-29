package gamelogic;

import java.util.ArrayList;
import java.util.Random;

public class GameLevel {
    public final GameID        gameID;
    public final int           rows = 10; 
    public final int           cols = 10; 
    public final LevelItem[][] level;
    public Position            player;
    private int                numSteps;
    private boolean            gameLost;

    public GameLevel(ArrayList<String> gameLevelRows, GameID gameID) {
        this.gameID = gameID;
        level = new LevelItem[rows][cols];
        numSteps = 0;
        gameLost = false; 

        // Initialize level with EMPTY default
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                level[i][j] = LevelItem.EMPTY;
            }
        }

        // Populate level
        for (int i = 0; i < Math.min(rows, gameLevelRows.size()); i++) {
            String s = gameLevelRows.get(i);
            for (int j = 0; j < Math.min(cols, s.length()); j++) {
                switch (s.charAt(j)) {
                    case '#': level[i][j] = LevelItem.WALL; break;
                    case '.': level[i][j] = LevelItem.DESTINATION; break;
                    case '$': level[i][j] = LevelItem.DRAKE; break;
                    default:  level[i][j] = LevelItem.EMPTY; break;
                }
            }
        }
        // player at bottom-left corner
        player = new Position(0, rows - 1);
        if (level[player.y][player.x] == LevelItem.WALL) {
            throw new IllegalArgumentException("Bottomleft corner cannot be a wall");
        }
    }

    public GameLevel(GameLevel gl) { //copy constructor
        gameID = gl.gameID;
        numSteps = gl.numSteps;
        gameLost = gl.gameLost;
        level = new LevelItem[rows][cols];
        player = new Position(0, rows - 1);
        for (int i = 0; i < rows; i++) {
            System.arraycopy(gl.level[i], 0, level[i], 0, cols);
        }
    }

    public boolean isValidPosition(Position p) {
        return (p.x >= 0 && p.y >= 0 && p.x < cols && p.y < rows);
    }

    public boolean isFree(Position p) {
        if (!isValidPosition(p)) return false;
        LevelItem li = level[p.y][p.x];
        return (li == LevelItem.EMPTY || li == LevelItem.DESTINATION);
    }

    public boolean movePlayer(Direction d) {
        Position curr = player;
        Position next = curr.translate(d);
        if (isFree(next)) {
            player = next;
            numSteps++;
            if (collideDrake()) {
                gameLost = true; //check for losing game
            }
            return true;
        }
        return false;
    }

    public void moveDrake() {
    Random random = new Random();
    Position drakePosition = null;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (level[i][j] == LevelItem.DRAKE) {
                drakePosition = new Position(j, i);
                break;
            }
        }
        if (drakePosition != null) break;
    }

    if (drakePosition == null) return;

    // Direction for rand: Left, Right, Up, Down
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    while (true) {
        int direction = random.nextInt(4); //random
        Position next = new Position(drakePosition.x + dx[direction], drakePosition.y + dy[direction]);

        while (isValidPosition(next) && level[next.y][next.x] != LevelItem.WALL) { //next: determines the next position where the drake is about to move

            Position afterNext = new Position(next.x + dx[direction], next.y + dy[direction]);
            if (!isValidPosition(afterNext) || level[afterNext.y][afterNext.x] == LevelItem.WALL) { //after

                level[drakePosition.y][drakePosition.x] = LevelItem.EMPTY; 
                level[next.y][next.x] = LevelItem.DRAKE; 
                drakePosition = next; 
                if (collideDrake()) {
                    gameLost = true; 
                }
                return; 
            }
            next = afterNext;
        }
    }
}


    public boolean collideDrake() {
        int[] dx = {-1, 1, 0, 0}; // scanning around
        int[] dy = {0, 0, -1, 1}; 
        for (int i = 0; i < 4; i++) {
            Position neighbor = new Position(player.x + dx[i], player.y + dy[i]);
            if (isValidPosition(neighbor) && level[neighbor.y][neighbor.x] == LevelItem.DRAKE) {
                return true; 
            }
        }
        return false;
    }

    public void printLevel() { // Console DEBUG
        int x = player.x, y = player.y;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == y && j == x)
                    System.out.print('@');
                else
                    System.out.print(level[i][j].representation);
            }
            System.out.println("");
        }
    }

    public boolean isGameLost() {
        return gameLost; 
    }

    public int getNumSteps() {
        return numSteps;
    }
}
