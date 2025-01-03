package org.mainframe;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

import org.gamelogic.*;
import org.database.LeaderboardManager;
import org.database.PlayerScore;


public class MainWindow extends JFrame {
    private GameSprite player;
    private Direction currentDirection;
    private final Game game;
    private Board board;
    private final JLabel gameStatLabel;
    private Direction currDir;
    private final LeaderboardManager leaderboardManager;
//    private javax.swing.Timer gameTimer;

    public MainWindow() throws IOException {
        game = new Game();
        leaderboardManager = new LeaderboardManager();
        setTitle("Diddler Escape - Night of the Living Drake");
        Image playerImage = new ImageIcon("player.png").getImage();
        player = new GameSprite(0, 0, 32, 32, playerImage);
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("drake.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

//        gameTimer = new javax.swing.Timer(50, e -> {
//            if (game.isLevelLoaded()) {
//                game.tick();
//                if (game.isGameLost()) {
//                    handleGameLost();
//                    gameTimer.stop();
//                }
//                board.repaint();
//            }
//        });
//        gameTimer.start();

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Options");
        JMenu menuGameLevel = new JMenu("Choose Room");
        JMenu menuGameScale = new JMenu("Resolution");
        JMenuItem leaderboardItem = new JMenuItem(new AbstractAction("Leaderboard") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLeaderboard();
            }
        });
        JMenuItem restartItem = new JMenuItem(new AbstractAction("Restart Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        createGameLevelMenuItems(menuGameLevel);
        createScaleMenuItems(menuGameScale, 1.0, 2.0, 0.5);

        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit the nightmare") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    leaderboardManager.conn.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });

        menuGame.add(menuGameLevel);
        menuGame.add(menuGameScale);
        menuGame.add(leaderboardItem);
        menuGame.add(restartItem);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout(0, 10));
        gameStatLabel = new JLabel("label");

        add(gameStatLabel, BorderLayout.NORTH);
        try {
            add(board = new Board(game), BorderLayout.CENTER);
        } catch (IOException ex) {
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (!game.isLevelLoaded()) return;

                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        currentDirection = Direction.LEFT;
                        player.setDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        currentDirection = Direction.RIGHT;
                        player.setDirection(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        currentDirection = Direction.UP;
                        player.setDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        currentDirection = Direction.DOWN;
                        player.setDirection(Direction.DOWN);
                        break;
                }

                if (currentDirection != null) {
                    player.move();
                    checkCollisions();
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                Direction releasedDir = null;

                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        releasedDir = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        releasedDir = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        releasedDir = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        releasedDir = Direction.DOWN;
                        break;
                }

                if (currentDirection == releasedDir) {
                    currentDirection = null;
                    player.stopMoving();
                }
            }

        });

        setResizable(false);
        setLocationRelativeTo(null);
        game.loadGame(new GameID("EASY", 1));
        board.refresh();
        pack();
        refreshGameStatLabel();
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        player.draw(g);

    }

    private void checkCollisions() {
        // Add collision detection logic here
        int tileX = player.getX() / 32; // Convert pixel position to tile position
        int tileY = player.getY() / 32;
        Position playerPos = game.getPlayerPos();
        if (tileX != playerPos.x || tileY != playerPos.y) {
            Direction moveDir = null;
            if (tileX > playerPos.x) moveDir = Direction.RIGHT;
            else if (tileX < playerPos.x) moveDir = Direction.LEFT;
            else if (tileY > playerPos.y) moveDir = Direction.DOWN;
            else if (tileY < playerPos.y) moveDir = Direction.UP;

            if (moveDir != null) {
                game.step(moveDir);
            }
        }
    }

    private void handleGameWon() {
        game.incrementSolved();
        JOptionPane.showMessageDialog(this,
                "Crazy! You escaped the diddler! Another W to the total: " + game.getSolved() + ".",
                "W in the SSSHATT",
                JOptionPane.INFORMATION_MESSAGE);
        restartLevel();
    }

    private void handleGameLost() {
        saveScoreToLeaderboard();
        showLeaderboard();
        JOptionPane.showMessageDialog(this,
                "Damn bro... You only solved: " + game.getSolved() + ".",
                "GG Go next",
                JOptionPane.INFORMATION_MESSAGE);
        restartGame();
    }

    private void saveScoreToLeaderboard() {
        String name = JOptionPane.showInputDialog(this,
                "Enter name for the leaderboard:",
                "Game Over!",
                JOptionPane.INFORMATION_MESSAGE);

        if (name != null && !name.trim().isEmpty()) {
            leaderboardManager.addScore(new PlayerScore(name, game.getSolved(), game.getTotalSteps()));
        }
    }

    private void restartLevel() {
        GameID currentID = game.getGameID();
        game.loadGame(currentID);
        board.refresh();
        refreshGameStatLabel();
    }

    private void restartGame() {
        game.resetGame();
        game.loadGame(new GameID("EASY", 1));
        board.refresh();
        refreshGameStatLabel();
    }

    private void showLeaderboard() {
        StringBuilder leaderboard = new StringBuilder("Top 10 Players:\n\n");
        List<PlayerScore> topScores = leaderboardManager.getTopScores(10);

        for (int i = 0; i < topScores.size(); i++) {
            PlayerScore score = topScores.get(i);
            leaderboard.append(String.format("%d. %s - Mazes: %d, Steps: %d\n",
                    i + 1, score.getName(), score.getSolved(), score.getSteps()));
        }

        JOptionPane.showMessageDialog(this,
                leaderboard.toString(),
                "Leaderboard",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshGameStatLabel() {
        if (!game.isLevelLoaded()) {
            gameStatLabel.setText("No level loaded.");
            return;
        }
        String s = "Steps: " + game.getTotalSteps();
        String l = "Mazes Solved: " + game.getSolved();
        gameStatLabel.setText(s + "  |  " + l);
    }


    private void createGameLevelMenuItems(JMenu menu) {
        for (String s : game.getDifficulties()) {
            JMenu difficultyMenu = new JMenu(s);
            menu.add(difficultyMenu);
            for (Integer i : game.getLevelsOfDifficulty(s)) {
                JMenuItem item = new JMenuItem(new AbstractAction("Level: " + i) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.loadGame(new GameID(s, i));
                        board.refresh();
                        pack();
                    }
                });
                difficultyMenu.add(item);
            }
        }
    }

    private void createScaleMenuItems(JMenu menu, double from, double to, double by) {
        while (from <= to) {
            final double scale = from;
            JMenuItem item = new JMenuItem(new AbstractAction(from + "x") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (board.setScale(scale)) pack();
                }
            });
            menu.add(item);

            if (from == to) break;
            from += by;
            if (from > to) from = to;
        }
    }

    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (IOException ex) {
        }
    }
}
