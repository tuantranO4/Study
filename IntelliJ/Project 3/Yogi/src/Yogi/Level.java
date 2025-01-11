/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yogi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Font;

/**
 *
 * @author bli
 */
public class Level {
    GameEngine ge;
    Tile[] tileTypes;
    Tile[][] mapTilesDiff;
    Tile[] terrainTypes;
    Tile[] objectivesTypes;
    private Tile[][] terrainTilesDiff;
    Tile[][] objectivesTilesDiff;
    Tile enemyTypes;
    Tile[][] enemiesTilesDiff;
    ArrayList<Enemy> enemies = new ArrayList<>();
    int numberOfCollecatable = 0;

    public Level(GameEngine ge, int levelNum) {
        this.ge = ge;
        tileTypes = new Tile[80];
        mapTilesDiff = new Tile[ge.screenRow][ge.screenCol];
        terrainTypes = new Tile[46];
        terrainTilesDiff = new Tile[ge.screenRow][ge.screenCol];
        objectivesTilesDiff = new Tile[ge.screenRow][ge.screenCol];
        objectivesTypes = new Tile[46];
        enemiesTilesDiff = new Tile[ge.screenRow][ge.screenCol];
        getTileImage();
        loadLevel(levelNum);
    }

    public void getTileImage() {
        for (int i = 0; i < 80; i++) {
            tileTypes[i] = new Tile();
            tileTypes[i].image = new ImageIcon("data/Assets/Tilesets/Grass/tile_" + i + ".png").getImage();

        }
        for (int i = 0; i < 46; i++) {
            terrainTypes[i] = new Tile();
            terrainTypes[i].image = new ImageIcon("data\\Assets\\Objects\\Collectables\\tile_" + i + ".png").getImage();
        }
        for (int i = 0; i < 46; i++) {
            objectivesTypes[i] = new Tile();
            objectivesTypes[i].image = new ImageIcon("data\\Assets\\Objects\\Collectables\\tile_" + i + ".png")
                    .getImage();
        }
        enemyTypes = new Tile();
        enemyTypes.image = new ImageIcon("data\\Assets\\Characters\\Enemy\\egg.png").getImage();
    }

    public void loadLevel(int levelNum) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("data\\levels\\level" + levelNum + "_Tile Layer 1.csv"));

            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTilesDiff[row][col] = (Tile) tileTypes[num].clone();
                    mapTilesDiff[row][col].x = col * ge.tileSize;
                    mapTilesDiff[row][col].y = row * ge.tileSize;
                    mapTilesDiff[row][col].tileNum = num;
                }

            }
            br.close();
            br = new BufferedReader(new FileReader("data\\levels\\level" + levelNum + "_terrain.csv"));
            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine().trim();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col].trim());
                    if (num == -1) {
                        num = 45;
                    }
                    terrainTilesDiff[row][col] = (Tile) terrainTypes[num].clone();
                    if (num < 45) {
                        terrainTilesDiff[row][col].collsion = true;
                        terrainTilesDiff[row][col].height = ge.tileSize;
                        terrainTilesDiff[row][col].width = ge.tileSize;
                        terrainTilesDiff[row][col].x = col * ge.tileSize;
                        terrainTilesDiff[row][col].y = row * ge.tileSize;
                        terrainTilesDiff[row][col].tileNum = num;
                    }
                }

            }
            br.close();
            br = new BufferedReader(new FileReader("data\\levels\\level" + levelNum + "_Collectable.csv"));
            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    if (num == -1) {
                        num = 45;
                    }
                    objectivesTilesDiff[row][col] = (Tile) objectivesTypes[num].clone();
                    if (num < 45) {
                        objectivesTilesDiff[row][col].collsion = true;
                        objectivesTilesDiff[row][col].height = ge.tileSize;
                        objectivesTilesDiff[row][col].width = ge.tileSize;
                        objectivesTilesDiff[row][col].x = col * ge.tileSize;
                        objectivesTilesDiff[row][col].y = row * ge.tileSize;
                        objectivesTilesDiff[row][col].collectable = true;
                        objectivesTilesDiff[row][col].tileNum = num;
                        numberOfCollecatable++;
                    }
                }
            }
            br.close();
            br = new BufferedReader(new FileReader("data\\levels\\level" + levelNum + "_enemy.csv"));
            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    if (num != -1) {
                        Enemy temp = new Enemy(ge, col, num, line);
                        String dir;
                        switch (num / 2) {
                            case 0:
                                dir = "down";
                                temp = new Enemy(ge, col * ge.tileSize, row * ge.tileSize, dir);
                                break;
                            case 1:
                                dir = "left";
                                temp = new Enemy(ge, col * ge.tileSize, row * ge.tileSize, dir);
                                break;
                            case 2:
                                dir = "right";
                                temp = new Enemy(ge, col * ge.tileSize, row * ge.tileSize, dir);
                                break;
                            case 3:
                                dir = "up";
                                temp = new Enemy(ge, col * ge.tileSize, row * ge.tileSize, dir);
                                break;
                        }
                        enemies.add(temp);
                    }
                }
            }
            br.close();
            br = new BufferedReader(new FileReader("data\\levels\\level" + levelNum + "_enemyBase.csv"));
            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine().trim();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col].trim());
                    if (num == -1) {
                        num = 45;
                    }
                    enemiesTilesDiff[row][col] = (Tile) enemyTypes.clone();
                    if (num < 45) {
                        enemiesTilesDiff[row][col].collsion = true;
                        enemiesTilesDiff[row][col].height = ge.tileSize;
                        enemiesTilesDiff[row][col].width = ge.tileSize;
                        enemiesTilesDiff[row][col].x = col * ge.tileSize;
                        enemiesTilesDiff[row][col].y = row * ge.tileSize;
                        enemiesTilesDiff[row][col].tileNum = num;
                    }
                }

            }
            br.close();
        } catch (IOException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    int collisionCount = 0;

    public void collsionCheck(Player player) {
        for (int i = 0; i < ge.screenRow; i++) {
            for (int j = 0; j < ge.screenCol; j++) {
                if (objectivesTilesDiff[i][j].collectable) {
                    if (objectivesTilesDiff[i][j].collides(player)) {
                        objectivesTilesDiff[i][j].image = objectivesTypes[45].image;
                        objectivesTilesDiff[i][j].collectable = false;
                        objectivesTilesDiff[i][j].tileNum = 45;
                        numberOfCollecatable--;
                    }
                }
            }
        }
    }

    public int getNumberOfFruitsLeft() {
        return numberOfCollecatable;
    }

    public Rectangle[] getBorders() {
        Rectangle allBorders[] = new Rectangle[ge.screenRow * ge.screenCol];
        int numOfBorders = 0;
        for (int i = 0; i < ge.screenRow; i++) {
            for (int j = 0; j < ge.screenCol; j++) {
                allBorders[numOfBorders] = terrainTilesDiff[i][j].getBorder();
                numOfBorders++;
            }
        }
        return allBorders;
    }

    public Rectangle[] getEnemyBorders() {
        Rectangle allBorders[] = new Rectangle[ge.screenRow * ge.screenCol];
        int numOfBorders = 0;
        for (int i = 0; i < ge.screenRow; i++) {
            for (int j = 0; j < ge.screenCol; j++) {
                allBorders[numOfBorders] = enemiesTilesDiff[i][j].getBorder();
                numOfBorders++;
            }
        }
        return allBorders;
    }

    public Rectangle[] getEnemyLookingBorders() {
        Rectangle[] temp = new Rectangle[enemies.size()];
        int count = 0;
        for (Enemy enemy : enemies) {
            temp[count] = enemy.getLookRectangle();
            count++;
        }
        return temp;
    }

    public void draw(Graphics2D g2) {

        for (int row = 0; row < ge.screenRow; row++) {
            for (int col = 0; col < ge.screenCol; col++) {
                //the map
                g2.drawImage(mapTilesDiff[row][col].image, mapTilesDiff[row][col].x, mapTilesDiff[row][col].y,
                        ge.tileSize,
                        ge.tileSize, null);
                //the trees and stuff
                g2.drawImage(terrainTilesDiff[row][col].image, terrainTilesDiff[row][col].x,
                        terrainTilesDiff[row][col].y,
                        ge.tileSize, ge.tileSize, null);
                //the fruits
                g2.drawImage(objectivesTilesDiff[row][col].image, objectivesTilesDiff[row][col].x,
                        objectivesTilesDiff[row][col].y, ge.tileSize, ge.tileSize, null);
                g2.drawImage(enemiesTilesDiff[row][col].image, enemiesTilesDiff[row][col].x,
                        enemiesTilesDiff[row][col].y, ge.tileSize, ge.tileSize, null);
            }
        }
        //drawing the enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g2);
            enemy.update(getEnemyBorders());
        }
        g2.setColor(Color.white);
        g2.setFont(new Font("Verdana", Font.BOLD, 20));
        g2.drawString("Number Of Fruits Left: " + numberOfCollecatable, (ge.screenCol - 6) * ge.tileSize, 20);
    }

}
