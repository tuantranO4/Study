package org.mainframe;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import org.gamelogic.Direction;
import org.gamelogic.Game;
import org.gamelogic.GameID;
import org.database.LeaderboardManager;
import org.database.PlayerScore;



public class MainWindow extends JFrame {
    private final Game game;
    private Board board;
    private final JLabel gameStatLabel;
    private Direction currDir;
    private javax.swing.Timer gameTimer;
    private final LeaderboardManager leaderboardManager;

    public MainWindow() throws IOException {
        game = new Game();
        leaderboardManager = new LeaderboardManager();
        setTitle("Diddler Escape - Night of the Living Drake");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("drake.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));


        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Options");
        JMenu menuGameLevel = new JMenu("Choose Room");
        JMenu menuGameScale = new JMenu("Resolution");

        gameTimer = new javax.swing.Timer(500, e -> {
            if (game.isLevelLoaded() && !game.isGameWon() && !game.isGameLost()) {
                game.tick();
                board.repaint();
                if (game.isGameLost()) {
                    handleGameLost();
                }
            }
        });
        gameTimer.start();
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
                super.keyPressed(ke);
                if (!game.isLevelLoaded()) return;
                int kk = ke.getKeyCode();
                Direction d = null;
                switch (kk) {
                    case KeyEvent.VK_LEFT:
                        currDir = d = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        currDir = d = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        currDir = d = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        currDir = d = Direction.DOWN;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        restartLevel();
                        break;
                }
                refreshGameStatLabel();
                board.repaint();
                if (currDir != null) {
                    game.movePlayer(currDir); // Changed from step() to movePlayer()
                    if (game.isGameWon()) {
                        handleGameWon();
                    } else if (game.isGameLost()) {
                        handleGameLost();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                super.keyReleased(ke);
                Direction d = null;
                int kk = ke.getKeyCode();

                switch (kk) {
                    case KeyEvent.VK_LEFT:
                        d = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        d = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        d = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        d = Direction.DOWN;
                        break;
                }

                if (currDir == d)
                    currDir = null;
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
