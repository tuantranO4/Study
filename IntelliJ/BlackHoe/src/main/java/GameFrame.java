import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import enums.PlayerTurn;
import gamestate.Board;

public class GameFrame extends JFrame {
    private Board board;
    private BoardPanel boardPanel;
    private JLabel statusLabel;

    public GameFrame() {
        setTitle("Board Game");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Game menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> initializeGame(5));  
        gameMenu.add(newGame);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(exitItem);

        // Size menu
        JMenu sizeMenu = new JMenu("Size");
        menuBar.add(sizeMenu);

        int[] sizes = {5, 7, 9};
        for (int size : sizes) {
            JMenuItem sizeItem = new JMenuItem(size + "x" + size);
            sizeItem.addActionListener(e -> initializeGame(size));
            sizeMenu.add(sizeItem);
        }

        // Status label
        statusLabel = new JLabel(" Player 1's turn", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Board panel
        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        initializeGame(5); 
        setVisible(true);
    }

    private void initializeGame(int size) {
        board = new Board(size);
        boardPanel.setBoard(board);
        updateStatus();
    }

    public void updateStatus() {
        if (board.isOver()) {
            String winnerText = board.winnerAnnouncer();
            JOptionPane.showMessageDialog(this, winnerText);
            initializeGame(board.getBoardSize()); 
        } else {
            String turnText = board.getTurn() == PlayerTurn.TURN_1 ? "Player 1's turn" : "Player 2's turn";
            statusLabel.setText(turnText);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
