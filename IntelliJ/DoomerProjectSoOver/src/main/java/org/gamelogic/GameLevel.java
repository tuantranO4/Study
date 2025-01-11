package org.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLevel {
    public final GameID gameID;
    public final int rows = 10;
    public final int cols = 10;
    public final LevelItem[][] level;
    public final boolean[][] visible;
    private int lives = 1;
    private int solved = 0;
    public Position player;
    private int currentDrakeDx = 0;
    private int currentDrakeDy = 0;
    private int numSteps;
    private boolean gameLost;
    private static final int VISIBILITY_RANGE = 3;

    public GameLevel(ArrayList<String> gameLevelRows, GameID gameID) {
        this.gameID = gameID;
        level = new LevelItem[rows][cols];
        visible = new boolean[rows][cols];
        numSteps = 0;
        gameLost = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                level[i][j] = LevelItem.EMPTY;
                visible[i][j] = false;
            }
        }
        // Populate level
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
        var random = new Random();
        List<Position> freePositions = new ArrayList<>();
        // Collect all free position
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (level[y][x] == LevelItem.EMPTY && !(x == player.x && y == player.y)
                        && !isDrakeSpawnpointPlayerAdjacent(new Position(x, y))) {
                    freePositions.add(new Position(x, y));
                }
            }
        }
        // Select a random free position
        if (!freePositions.isEmpty()) {
            Position randomFreePosition = freePositions.get(random.nextInt(freePositions.size()));
            level[randomFreePosition.y][randomFreePosition.x] = LevelItem.DRAKE;
        }
    }

    private static float manhattanDistance(Position p1, Position p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private boolean isDrakeSpawnpointPlayerAdjacent(Position p) {
        int distance = 5;
        return manhattanDistance(player, p) <= distance;
    }


    public GameLevel(GameLevel gl) { // copy constructor
        this.gameID = gl.gameID;
        this.numSteps = gl.numSteps;
        this.gameLost = gl.gameLost;
        this.lives = gl.lives;
        this.solved = gl.solved;
        this.level = new LevelItem[rows][cols];
        this.visible = new boolean[rows][cols];
        this.player = new Position(gl.player.x, gl.player.y);


        for (int i = 0; i < rows; i++) {
            System.arraycopy(gl.level[i], 0, level[i], 0, cols);
        }
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
        if (!isValidPosition(p))
            return false;
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
            return true;
        }
        return false;
    }


    public void moveDrake() {
        Position drakePosition = null;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (level[i][j] == LevelItem.DRAKE) {
                    drakePosition = new Position(j, i);
                    break;
                }
            }
            if (drakePosition != null)
                break;
        }
        if (drakePosition == null)
            return;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        if (currentDrakeDx == 0 && currentDrakeDy == 0) {
            List<Integer> directions = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                directions.add(i);
            }

            Random random = new Random();
            while (!directions.isEmpty()) {
                int idx = random.nextInt(directions.size());
                int dirIndex = directions.get(idx);
                Position possible = new Position(
                        drakePosition.x + dx[dirIndex],
                        drakePosition.y + dy[dirIndex]
                );

                if (isValidPosition(possible) && level[possible.y][possible.x] == LevelItem.EMPTY) {
                    currentDrakeDx = dx[dirIndex];
                    currentDrakeDy = dy[dirIndex];
                    break;
                }
                directions.remove(idx);
            }
        }

        Position nextPosition = new Position(
                drakePosition.x + currentDrakeDx,
                drakePosition.y + currentDrakeDy
        );

        if (!isValidPosition(nextPosition) || level[nextPosition.y][nextPosition.x] != LevelItem.EMPTY) {
            List<Integer> directions = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                directions.add(i);
            }

            Random random = new Random();
            while (!directions.isEmpty()) {
                int idx = random.nextInt(directions.size());
                int dirIndex = directions.get(idx);

                Position possible = new Position(
                        drakePosition.x + dx[dirIndex],
                        drakePosition.y + dy[dirIndex]
                );

                if (isValidPosition(possible) && level[possible.y][possible.x] == LevelItem.EMPTY) {
                    currentDrakeDx = dx[dirIndex];
                    currentDrakeDy = dy[dirIndex];
                    nextPosition = possible;
                    break;
                }
                directions.remove(idx);
            }
        }

        if (isValidPosition(nextPosition) && level[nextPosition.y][nextPosition.x] == LevelItem.EMPTY) {
            level[drakePosition.y][drakePosition.x] = LevelItem.EMPTY;
            level[nextPosition.y][nextPosition.x] = LevelItem.DRAKE;
        }
    }

    public boolean collideDrake() {
        int[] dx = {-1, 1, 0, 0, 0, -1, 1, -1, 1};
        int[] dy = {0, 0, -1, 1, 0, -1, 1, 1, -1};
        for (int i = 0; i < 9; i++) {
            Position neighbor = new Position(player.x + dx[i], player.y + dy[i]);
            if (isValidPosition(neighbor) && level[neighbor.y][neighbor.x] == LevelItem.DRAKE) {
                return true;
            }
        }
        return false;
    }

    public boolean hasExit() {
        return level[player.y][player.x] == LevelItem.DESTINATION;
    }

    public void printLevel() {
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

    public void setLost() {
        gameLost = true;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void resetLives() {
        lives = 1;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLive() {
        lives--;
        if (lives <= 0) {
            setLost();
        }
    }

    public void incrementSolved() {
        solved++;
    }

    public int getSolved() {
        return solved;
    }

    public void setSteps(int steps) {
        this.numSteps = steps;
    }

    public boolean isGameWon() {
        return lives > 0 && !gameLost && hasExit();
    }
}
