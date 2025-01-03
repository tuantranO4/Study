package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine extends JPanel implements Runnable, KeyListener {
    private final int DIM = 16;
    private final int scale = 3;
    public final int tileSize = DIM * scale;
    public final int screenCol = 10;
    public final int screenRow = 10;
    private final int DimWidth = tileSize * screenCol;
    private final int DimHeight = tileSize * screenRow;
    private final int FPS = 165;
    private boolean pause = false;
    Thread gameThread;

    PlayerSprite player = new PlayerSprite(this);
    int Dir = 0; //dir=0 - stop. TODO : implement later
    int MazeCnt = 0;
    Level level = new Level(this, MazeCnt);
    boolean isWon = false;

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public GameEngine() {
        this.setPreferredSize(new Dimension(DimWidth, DimHeight));
        this.setBackground(Color.black);
        addKeyListener(this);
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
    public void update() {
        // Check for player's lives and reset the game if necessary
        if (player.live <= 0) {
            String n = JOptionPane.showInputDialog(this, "Input Your Name", "You Lost");
            player = new PlayerSprite(this);
            Dir = 0;
            MazeCnt = 1;
            level = new Level(this, MazeCnt);
            isWon = false;
        }

        if (level.getWin()) {
            JOptionPane.showMessageDialog(this, "You escaped");
            isWon = true;
            player = new PlayerSprite(this);
            Dir = 0;
            MazeCnt++;

            // Check if all levels are completed
            if (MazeCnt == 10) {
                String n = JOptionPane.showInputDialog(this, "Input Your Name", "You Finished The Game");
                player = new PlayerSprite(this);
                Dir = 0;
                MazeCnt = 1;
                level = new Level(this, MazeCnt);
                isWon = false;
            } else {
                level = new Level(this, MazeCnt);
                isWon = false;
            }
        }
        // Check for collisions, update player position, and reset if collided with an
        // enemy
        level.collisionCheck(player);
        Rectangle borders[] = level.getBorders();
        player.update(Dir, borders);
        if (player.collision(level.getDrakeDZ())) {
            System.out.println("Collided drake");
            player.playerReset();
            level = new Level(this, MazeCnt);
            isWon = false;
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        level.draw(g2);
        g2.drawString("Maze solved: " + MazeCnt, (screenCol - 6) * tileSize, 40);
        player.draw(g2);

        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Dir = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Dir = 2;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Dir = 3;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            Dir = 4;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pause = !pause;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Dir == 1 && e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Dir = 0;
        } else if (Dir == 2 && e.getKeyCode() == KeyEvent.VK_LEFT) {
            Dir = 0;
        } else if (Dir == 3 && e.getKeyCode() == KeyEvent.VK_DOWN) {
            Dir = 0;
        } else if (Dir == 4 && e.getKeyCode() == KeyEvent.VK_UP) {
            Dir = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        /*
         * The Industrial Revolution and its consequences have been a disaster for the human race.
         * They have greatly increased the life expectancy of those of us who live in "advanced" countries,
         * but they have destabilized society, have made life unfulfilling, have subjected human beings
         * to indignities, have led to widespread psychological suffering (in the Third World to physical
         * suffering as well), and have inflicted severe damage on the natural world.
         *
         * The continued development of technology will worsen the situation. It will certainly subject
         * human beings to greater indignities and inflict greater damage on the natural world, it will
         * probably lead to greater social disruption and psychological suffering, and it may lead to
         * increased physical suffering even in "advanced" countries.
         */
    }
}
