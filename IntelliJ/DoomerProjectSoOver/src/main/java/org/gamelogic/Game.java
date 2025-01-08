package org.gamelogic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import org.res.ResourceLoader;

public class Game {
    private final HashMap<String, HashMap<Integer, GameLevel>> gameLevels;
    private GameLevel gameLevel = null;
    private int stepsTotal = 0;

    public Game() {
        gameLevels = new HashMap<>();
        readLevels();
    }

    public boolean getVisibility(int row, int col) {
        return gameLevel.visible[row][col];
    }

    public void loadGame(GameID gameID) {
        if (gameLevel == null) {
            gameLevel = new GameLevel(gameLevels.get(gameID.difficulty).get(gameID.level));
        } else {
            int currentSolved = gameLevel.getSolved();
            int currentSteps = gameLevel.getNumSteps() + stepsTotal;
            int currentLives = gameLevel.getLives();

            gameLevel = new GameLevel(gameLevels.get(gameID.difficulty).get(gameID.level));
            gameLevel.setSteps(currentSteps);
            for (int i = gameLevel.getLives(); i > currentLives; i--) {
                gameLevel.decreaseLive(); //should be only one live, but i leave it here for debug purpose
            }
            for (int i = 0; i < currentSolved; i++) {
                gameLevel.incrementSolved();
            }
        }
    }

    public int getTotalSteps() {
        return stepsTotal + (gameLevel != null ? gameLevel.getNumSteps() : 0);
    }

    public void printGameLevel(){ gameLevel.printLevel(); }

    public void movePlayer(Direction d) {
        if (gameLevel != null) {
            gameLevel.movePlayer(d);
            if (gameLevel.hasExit()) {
                gameLevel.incrementSolved();
                return;
            }
            if (gameLevel.collideDrake()) {
                gameLevel.decreaseLive();
                gameLevel.setLost();
            }
        }
    }
    public void tick() {
        if (gameLevel != null) {
            gameLevel.moveDrake();
            if (gameLevel.hasExit()) {
                gameLevel.incrementSolved();
                return;
            }
            if (gameLevel.collideDrake()) {
                gameLevel.decreaseLive();
                gameLevel.setLost();
            }
        }
    }

//    public void tick() {
//        if (gameLevel != null) {
//            gameLevel.tick();
//        }
//        if (gameLevel.hasExit()) {
//            gameLevel.incrementSolved();
//            return;
//        }
//        if (gameLevel.collideDrake()){
//            gameLevel.decreaseLive();
//            gameLevel.setLost();
//        }
//    }

    public boolean isGameLost(){ return gameLevel.isGameLost(); }
    public boolean isGameWon(){ return gameLevel.isGameWon(); }

    // ----------------------------------
    // Getter methods
    // ----------------------------------

    public Collection<String> getDifficulties(){ return gameLevels.keySet(); }

    public Collection<Integer> getLevelsOfDifficulty(String difficulty){
        if (!gameLevels.containsKey(difficulty)) return null;
        return gameLevels.get(difficulty).keySet();
    }

    public boolean isLevelLoaded(){ return gameLevel != null; }
    public int getLevelRows(){ return gameLevel.rows; }
    public int getLevelCols(){ return gameLevel.cols; }
    public LevelItem getItem(int row, int col){ return gameLevel.level[row][col]; }
    public GameID getGameID(){ return (gameLevel != null) ? gameLevel.gameID : null; }
    public int getLives(){ return (gameLevel != null) ?  gameLevel.getLives():1; }


    public Position getPlayerPos(){ // MAKE IT ~IMMUTABLE
        return new Position(gameLevel.player.x, gameLevel.player.y);
    }

    // ------------------------------------------------------------------------
    // Utility methods to load game levels from levels.txt resource file.
    // ------------------------------------------------------------------------

    private void readLevels() {
        InputStream is = ResourceLoader.loadResource("levels.txt");

        try (Scanner sc = new Scanner(is)) {
            String line = readNextLine(sc);
            ArrayList<String> gameLevelRows = new ArrayList<>();

            while (!line.isEmpty()) {
                GameID id = readGameID(line);
                if (id == null) return;
                gameLevelRows.clear();
                line = readNextLine(sc);
                while (!line.isEmpty() && line.trim().charAt(0) != ';') {
                    gameLevelRows.add(line);
                    line = readNextLine(sc);
                }
                addNewGameLevel(new GameLevel(gameLevelRows, id));
            }
        } catch (Exception e) {
            System.out.println("Error reading the levels file: " + e);
            System.exit(1);
        }
    }


    public void resetLives() {
        gameLevel.resetLives();
    }

    public void resetGame() {
        stepsTotal = 0;
        gameLevel = null;
    }

    public void incrementSolved() {
        gameLevel.incrementSolved();
    }

    public int getSolved() {
        if (gameLevel == null) {
            return 0;
        }
        return gameLevel.getSolved();
    }


    private void addNewGameLevel(GameLevel gameLevel) {
        HashMap<Integer, GameLevel> levelsOfDifficulty;
        if (gameLevels.containsKey(gameLevel.gameID.difficulty)) {
            levelsOfDifficulty = gameLevels.get(gameLevel.gameID.difficulty);
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
        } else {
            levelsOfDifficulty = new HashMap<>();
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
            gameLevels.put(gameLevel.gameID.difficulty, levelsOfDifficulty);
        }
    }

    private String readNextLine(Scanner sc) {
        String line = "";
        while (sc.hasNextLine() && line.trim().isEmpty()) {
            line = sc.nextLine();
        }
        return line;
    }

    private GameID readGameID(String line) {
        line = line.trim();
        if (line.isEmpty() || line.charAt(0) != ';') return null;
        Scanner s = new Scanner(line);
        s.next();
        if (!s.hasNext()) return null;
        String difficulty = s.next().toUpperCase();
        if (!s.hasNextInt()) return null;
        int id = s.nextInt();
        return new GameID(difficulty, id);
    }
}


