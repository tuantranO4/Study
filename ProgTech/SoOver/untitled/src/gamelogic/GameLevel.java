package gamelogic;

import java.util.ArrayList;
import java.util.Random;

public class GameLevel {
    public final GameID gameID;
    public final int rows = 10;
    public final int cols = 10;
    public final LevelItem[][] level;
    public final boolean[][] visible;
    public Position player;
    private int numSteps;
    private boolean gameLost;
    private static final int VISIBILITY_RANGE = 3;

    public GameLevel(ArrayList<String> gameLevelRows, GameID gameID) {
        this.gameID = gameID;
        level = new LevelItem[rows][cols];
        visible = new boolean[rows][cols];
        numSteps = 0;
        gameLost = false;

        // Initialize level with EMPTY default
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                level[i][j] = LevelItem.EMPTY;
                visible[i][j] = false;
            }
        }
        // Populate level (walls and destinations only)
        for (int i = 0; i < Math.min(rows, gameLevelRows.size()); i++) {
            String s = gameLevelRows.get(i);
            for (int j = 0; j < Math.min(cols, s.length()); j++) {
                switch (s.charAt(j)) {
                    case '#': 
                        level[i][j] = LevelItem.WALL;
                        break;
                    case '.': 
                        level[i][j] = LevelItem.DESTINATION;
                        break;
                    default:  
                        level[i][j] = LevelItem.EMPTY;
                        break;
                }
            }
        }
        // Player starts at bottom-left
        player = new Position(0, rows - 1);
        if (level[player.y][player.x] == LevelItem.WALL) {
            throw new IllegalArgumentException("Bottom-left corner cannot be a wall");
        }
        placeRandomDrake();
        updateVisibility();
    }

    private void placeRandomDrake() {
        Random random = new Random();
        while (true) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            // check if position is valid for drake:
            // - empty space (!wall || !destination)
            // - not on player position
            // - not next to player
            if (level[y][x] == LevelItem.EMPTY && !(x == player.x && y == player.y) && !isAdjacentToPlayer(new Position(x, y))) {
                    level[y][x] = LevelItem.DRAKE;
                    return;
            }
        }
    }

    private boolean isAdjacentToPlayer(Position p) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i <= 4; i++) {
            int newX = p.x + dx[i];
            int newY = p.y + dy[i];
            if (newX == player.x && newY == player.y) {
                return true;
            }
        }
        return false;
    }

    public GameLevel(GameLevel gl) { //copy constructor
        this.gameID = gl.gameID;
        this.numSteps = gl.numSteps;
        this.gameLost = gl.gameLost;
        this.level = new LevelItem[rows][cols];
        this.visible = new boolean[rows][cols];
        this.player = new Position(gl.player.x, gl.player.y); // Copy player position correctly

        // Copy level array
        for (int i = 0; i < rows; i++) {
            System.arraycopy(gl.level[i], 0, level[i], 0, cols);
        }
        // Copy visibility array
        for (int i = 0; i < rows; i++) {
            System.arraycopy(gl.visible[i], 0, visible[i], 0, cols);
        }
    }

    private void updateVisibility() {
        // Reset visibility
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visible[i][j] = false;
            }
        }
        
        // Make cells within VISIBILITY_RANGE visible
        for (int dy = -VISIBILITY_RANGE; dy <= VISIBILITY_RANGE; dy++) {
            for (int dx = -VISIBILITY_RANGE; dx <= VISIBILITY_RANGE; dx++) {
                int newY = player.y + dy;
                int newX = player.x + dx;
                if (isValidPosition(new Position(newX, newY))) {
                    // Check if the distance is within range (Manhattan distance)
                    if (Math.abs(dx) + Math.abs(dy) <= VISIBILITY_RANGE) {
                        visible[newY][newX] = true;
                    }
                }
            }
        }
    }

    public boolean isVisible(Position p) {
        return visible[p.y][p.x];
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
            updateVisibility();
            if (collideDrake()) {
                gameLost = true;
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
            int direction = random.nextInt(4);
            Position next = new Position(drakePosition.x + dx[direction], drakePosition.y + dy[direction]); //check the position the dragon is about to move

            while (isValidPosition(next) && level[next.y][next.x] == LevelItem.EMPTY) { 

                Position afterNext = new Position(next.x + dx[direction], next.y + dy[direction]); //check stop just one step before a wall in its current direction
                if (!isValidPosition(afterNext) || level[afterNext.y][afterNext.x] == LevelItem.WALL) { 
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
        int[] dx = {-1, 1, 0, 0}; //scanning around
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            Position neighbor = new Position(player.x + dx[i], player.y + dy[i]);
            if (isValidPosition(neighbor) && level[neighbor.y][neighbor.x] == LevelItem.DRAKE) {
                return true;
            }
        }
        return false;
    }

    public boolean hasExit(){
        return level[player.y][player.x] == LevelItem.DESTINATION;
    }

    public void printLevel() { //debug thing
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