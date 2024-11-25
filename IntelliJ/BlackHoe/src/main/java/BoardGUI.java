import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.Direction;
import gamestate.Board;
import gamestate.Cell;

public class BoardGUI {

    private JButton[][] buttons;
    private Board board;
    public JPanel boardPanel;
    private int currentX = -1; // Tracks the selected ship's X-coordinate
    private int currentY = -1; // Tracks the selected ship's Y-coordinate
    private JFrame frame;

    public BoardGUI() {
        setupFrame(); // Create the frame first
        initializeGame(5); // Start with a 5x5 board
    }

    public void initializeGame(int boardSize) {
        board = new Board(boardSize);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize][boardSize];

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(80, 40));
                button.setFocusable(false);
                buttons[i][j] = button;
                boardPanel.add(button);

                int x = i;
                int y = j;
                button.addActionListener(e -> handleShipSelection(x, y));

                refreshButton(i, j);
            }
        }

        frame.getContentPane().removeAll(); // Clear the frame before adding new content
        frame.add(boardPanel, BorderLayout.CENTER); // Add the new board
        frame.revalidate(); // Refresh the frame layout
        frame.repaint(); // Redraw the frame
    }

    private void setupFrame() {
        frame = new JFrame("Board Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        setupKeyAdapter(); // Add the KeyAdapter after the frame is created
    }

    private void refreshButton(int x, int y) {
        JButton button = buttons[x][y];
        Cell cell = board.getCell(x, y);
        Color color;

        switch (cell.getStatus()) {
            case EMPTY:
                color = Color.WHITE;
                break;
            case SHIP_1:
                color = Color.RED;
                break;
            case SHIP_2:
                color = Color.CYAN;
                break;
            case BLACKHOLE:
                color = Color.BLACK;
                break;
            default:
                color = Color.GRAY;
        }

        button.setBackground(color);
        button.setText("");
    }

    private void handleShipSelection(int x, int y) {
        if (currentX != -1 && currentY != -1) {
            refreshButton(currentX, currentY);
        }

        Cell cell = board.getCell(x, y);
        if (cell.getStatus() == enums.BoardStatus.SHIP_1 || cell.getStatus() == enums.BoardStatus.SHIP_2) {
            currentX = x;
            currentY = y;

            if (cell.getStatus() == enums.BoardStatus.SHIP_1) {
                buttons[x][y].setBackground(Color.decode("#f67c7c"));
            } else if (cell.getStatus() == enums.BoardStatus.SHIP_2) {
                buttons[x][y].setBackground(Color.decode("#636db5"));
            }
        }
    }

    private void setupKeyAdapter() {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (currentX == -1 || currentY == -1) {
                    JOptionPane.showMessageDialog(boardPanel, "Select a ship first!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int keyCode = event.getKeyCode();
                Direction direction = null;

                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        direction = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        direction = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                        direction = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        direction = Direction.DOWN;
                        break;
                }

                if (direction != null) {
                    move(direction);
                }
            }
        });
    }

    private void move(Direction direction) {
        board.traverse(currentX, currentY, direction);
        refreshAllButtons();
        checkGameEnd();
    }

    private void refreshAllButtons() {
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                refreshButton(i, j);
            }
        }
    }

    private void checkGameEnd() {
        if (board.isOver()) {
            JOptionPane.showMessageDialog(boardPanel, board.winnerAnnouncer(), "Game Over", JOptionPane.PLAIN_MESSAGE);
            initializeGame(board.getBoardSize());
        }
    }
}
