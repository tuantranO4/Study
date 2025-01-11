/**
 * The GameEngine class represents the core of the Yogi Bear game.
 * It extends JPanel and implements Runnable and KeyListener interfaces.
 * This class handles game mechanics, player movement, levels, and high scores.
 */
package Yogi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * The GameEngine class manages the game logic, rendering, and player input.
 */
public class GameEngine extends JPanel implements Runnable, KeyListener {
    // SCREEN SETTINGS
    // Constants for screen and tile dimensions
    private final int TILESIZE = 16;
    private final int scale = 3;
    public final int tileSize = TILESIZE * scale;
    public final int screenCol = 16;
    public final int screenRow = 12;
    private final int screenWidth = tileSize * screenCol;
    private final int screenHeight = tileSize * screenRow;
    private final int FPS = 60;
    private boolean pause = false;
    Thread gameThread;

    // player
    Player player = new Player(this);
    int playerDir = 0;
    // level
    int curLevelNum = 1;
    Level level = new Level(this, curLevelNum);
    boolean gameisWon = false;

    /**
     * Constructor for the GameEngine class.
     * Initializes screen settings, background, and sets up key listener.
     * Also initializes the HighScores database.
     */
    public GameEngine() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        addKeyListener(this);
    }

    /**
     * Initiates the game thread for continuous updates and rendering.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000 / FPS;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1 && !pause) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Handles game updates, including player movement, collision checks, and level
     * progression.
     */
    public void update() {
        // Check for player's lives and reset the game if necessary
        if (player.lives <= 0) {
            // Prompt user for name, update high scores, and reset the game
            String n = JOptionPane.showInputDialog(this, "Input Your Name", "You Lost");
            player = new Player(this);
            playerDir = 0;
            curLevelNum = 1;
            level = new Level(this, curLevelNum);
            gameisWon = false;
        }

        // Check if all fruits are collected and update the game state
        if (level.getNumberOfFruitsLeft() <= 0) {
            JOptionPane.showMessageDialog(this, "You collected All the berries");
            gameisWon = true;
            player = new Player(this);
            playerDir = 0;
            curLevelNum++;

            // Check if all levels are completed
            if (curLevelNum == 11) {
                // Prompt user for name, update high scores, and reset the game
                String n = JOptionPane.showInputDialog(this, "Input Your Name", "You Won");
                player = new Player(this);
                playerDir = 0;
                curLevelNum = 1;
                level = new Level(this, curLevelNum);
                gameisWon = false;
            } else {
                level = new Level(this, curLevelNum);
                gameisWon = false;
            }
        }

        // Check for collisions, update player position, and reset if collided with an
        // enemy
        level.collsionCheck(player);
        Rectangle borders[] = level.getBorders();
        player.update(playerDir, borders);
        if (player.collisionDetect(level.getEnemyLookingBorders())) {
            System.out.println("Collided With Enemy");
            player.playerReset();
            level = new Level(this, curLevelNum);
            gameisWon = false;
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        level.draw(g2);
        g2.drawString("Number Of Fruits Left: " + player.lives, (screenCol - 6) * tileSize, 40);
        player.draw(g2);

        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println("SOMETHING WAS TYPED");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerDir = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerDir = 2;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerDir = 3;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            playerDir = 4;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pause = !pause;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        if (playerDir == 1 && e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerDir = 0;
        } else if (playerDir == 2 && e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerDir = 0;
        } else if (playerDir == 3 && e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerDir = 0;
        } else if (playerDir == 4 && e.getKeyCode() == KeyEvent.VK_UP) {
            playerDir = 0;
        }
    }
}
