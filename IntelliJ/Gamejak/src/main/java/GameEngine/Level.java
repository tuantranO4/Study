package GameEngine;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Level {
    GameEngine ge;
    private Unit[][] units;
    private Exit exit;
    private ArrayList<Unit> walls = new ArrayList<>();
    private DragonSprite drake;
    private boolean won = false;
    private Random random = new Random();

    public Level(GameEngine ge, int levelNum) {
        this.ge = ge;
        units = new Unit[ge.screenRow][ge.screenCol];
        exit = new Exit();
        loadLevel(levelNum);
        spawnDrake();
    }

    private void spawnDrake() {
        ArrayList<Point> emptySpaces = new ArrayList<>();
        for (int row = 0; row < ge.screenRow; row++) {
            for (int col = 0; col < ge.screenCol; col++) {
                if (units[row][col] == null) {
                    emptySpaces.add(new Point(col * ge.tileSize, row * ge.tileSize));
                }
            }
        }

        if (!emptySpaces.isEmpty()) {
            Point spawnPoint = emptySpaces.get(random.nextInt(emptySpaces.size()));
            String[] directions = {"up", "down", "left", "right"};
            String randomDirection = directions[random.nextInt(directions.length)];
            drake = new DragonSprite(ge, spawnPoint.x, spawnPoint.y, randomDirection);
        }
    }

    public void loadLevel(int levelNum) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data\\levels\\level" + levelNum + ".txt"));
            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine();
                for (int col = 0; col < ge.screenCol; col++) {
                    char tile = line.charAt(col);
                    int x = col * ge.tileSize;
                    int y = row * ge.tileSize;
                    switch(tile) {
                        case '#':
                            Unit wall = new Unit();
                            wall.x = x;
                            wall.y = y;
                            wall.width = ge.tileSize;
                            wall.height = ge.tileSize;
                            wall.collision = true;
                            walls.add(wall);
                            units[row][col] = wall;
                            break;
                        case '.':
                            exit.x = x;
                            exit.y = y;
                            exit.width = ge.tileSize;
                            exit.height = ge.tileSize;
                            exit.collision = true;
                            units[row][col] = exit;
                            break;
                        default:
                            units[row][col] = null;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (drake != null) {
            drake.checkCollisionDrake(getBorders());
        }
    }

    public boolean getWin() {
        return won;
    }

    public Rectangle[] getBorders() {
        ArrayList<Rectangle> borders = new ArrayList<>();
        for (Unit wall : walls) {
            borders.add(wall.getBorder());
        }
        borders.add(exit.getBorder());
        return borders.toArray(new Rectangle[0]);
    }

    public Rectangle[] getDrakeBorders() {
        return drake != null ? new Rectangle[]{drake.rect} : new Rectangle[0];
    }

    public Rectangle[] getDrakeDZ() {
        return drake != null ? new Rectangle[]{drake.getDZ()} : new Rectangle[0];
    }

    public void collisionCheck(PlayerSprite player) {
        if (exit.collides(player)) {
            won = true;
        }
    }

    public void draw(Graphics2D g2) {
        for (Unit wall : walls) {
            g2.fillRect(wall.x, wall.y, wall.width, wall.height);
        }
        g2.fillRect(exit.x, exit.y, exit.width, exit.height);

        if (drake != null) {
            drake.draw(g2);
        }
    }
}