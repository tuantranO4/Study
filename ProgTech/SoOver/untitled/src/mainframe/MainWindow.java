package mainframe;

import java.awt.BorderLayout;
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
import gamelogic.Direction;
import gamelogic.Game;
import gamelogic.GameID;
import database.LeaderboardManager;
import database.PlayerScore;

public class MainWindow extends JFrame {
    private final Game game;
    private Board board;
    private final JLabel gameStatLabel;
    private Direction currDir;
    private final LeaderboardManager leaderboardManager;

    public MainWindow() throws IOException {
        game = new Game();
        leaderboardManager = new LeaderboardManager();

        setTitle("Diddler Escape - Night of the Living Drake");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("res/drake.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Options");
        JMenu menuGameLevel = new JMenu("Choose Room");
        JMenu menuGameScale = new JMenu("Resolution");
        
        // Add leaderboard menu item
        JMenuItem leaderboardItem = new JMenuItem(new AbstractAction("Leaderboard") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLeaderboard();
            }
        });

        // Add restart game menu item
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
                    game.step(currDir);
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
        int lives = game.getLives();
        int steps = game.getNumSteps();
        String name = JOptionPane.showInputDialog(this, 
            "Congratulations! You escaped with " + lives + " lives in " + steps + " steps!\nEnter your name for the leaderboard:", 
            "Victory!", 
            JOptionPane.INFORMATION_MESSAGE);
        
        if (name != null && !name.trim().isEmpty()) {
            leaderboardManager.addScore(new PlayerScore(name, lives, steps));
            showLeaderboard();
        }
        
        restartLevel();
    }

    private void handleGameLost() {
        int lives = game.getLives();
        if (lives <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Game Over! You've run out of lives!", 
                "Game Over", 
                JOptionPane.INFORMATION_MESSAGE);
            showLeaderboard();
            restartGame(); // Start fresh game when all lives are lost
        } else {
            JOptionPane.showMessageDialog(this, 
                "Drake got you. Lives remaining: " + lives, 
                "Mane...", 
                JOptionPane.INFORMATION_MESSAGE);
            restartLevel(); // Just restart the current level if lives remain
        }
    }

    private void restartLevel() {
        GameID currentID = game.getGameID();
        game.loadGame(currentID); // This preserves the current lives count
        board.refresh();
        refreshGameStatLabel();
    }

    private void restartGame() {
        game.loadGame(new GameID("EASY", 1));
        game.resetLives(); // Reset lives to 3
        board.refresh();
        refreshGameStatLabel();
    }

    private void showLeaderboard() {
        StringBuilder leaderboard = new StringBuilder("Top 10 Escapes:\n\n");
        List<PlayerScore> topScores = leaderboardManager.getTopScores(10);
        
        for (int i = 0; i < topScores.size(); i++) {
            PlayerScore score = topScores.get(i);
            leaderboard.append(String.format("%d. %s - Lives: %d, Steps: %d\n", 
                i + 1, score.getName(), score.getLives(), score.getSteps()));
        }

        JOptionPane.showMessageDialog(this, 
            leaderboard.toString(), 
            "Leaderboard", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshGameStatLabel() {
        String s = "Steps: " + game.getNumSteps();
        String l = "Lives: " + game.getLives();
        gameStatLabel.setText(s + "  |  " + l);
    }
    
    private void createGameLevelMenuItems(JMenu menu){
        for (String s : game.getDifficulties()){
            JMenu difficultyMenu = new JMenu(s);
            menu.add(difficultyMenu);
            for (Integer i : game.getLevelsOfDifficulty(s)){
                JMenuItem item = new JMenuItem(new AbstractAction("Level-" + i) {
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
    
    private void createScaleMenuItems(JMenu menu, double from, double to, double by){
        while (from <= to){
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
        } catch (IOException ex) {}
    }    
}
