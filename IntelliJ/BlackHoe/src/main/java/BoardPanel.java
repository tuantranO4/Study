import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import enums.Direction;
import gamestate.Board;
import gamestate.Cell;

public class BoardPanel extends JPanel {
    private static final int CELL_SIZE = 60;
    private Board board;
    private Cell selectedCell;  // To keep track of the selected cell

    public BoardPanel() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    public void setBoard(Board board) {
        this.board = board;
        repaint();
    }

    private void handleMouseClick(MouseEvent e) {
        if (board == null) return;
        int x = e.getX() / CELL_SIZE;
        int y = e.getY() / CELL_SIZE;
        if (x >= 0 && x < board.getBoardSize() && y >= 0 && y < board.getBoardSize()) {
            selectedCell = board.getCell(y, x);  // Select cell based on mouse click
            repaint();
        }
    }

    private void handleKeyPress(KeyEvent e) {
        if (board == null || selectedCell == null) return;
        Direction direction = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> direction = Direction.UP;
            case KeyEvent.VK_S -> direction = Direction.DOWN;
            case KeyEvent.VK_A -> direction = Direction.LEFT;
            case KeyEvent.VK_D -> direction = Direction.RIGHT;
        }
        if (direction != null) {
            board.traverse(selectedCell.getX(), selectedCell.getY(), direction);
            selectedCell = null;  // Deselect the cell after moving
            repaint();
            ((GameFrame) SwingUtilities.getWindowAncestor(this)).updateStatus();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board == null) return;
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                int x = j * CELL_SIZE;
                int y = i * CELL_SIZE;
                g.setColor(Color.WHITE);
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                String text = board.getCell(i, j).getStatus().toString().substring(0, 1);
                g.drawString(text, x + (CELL_SIZE / 2), y + (CELL_SIZE / 2));
                if (selectedCell != null && i == selectedCell.getX() && j == selectedCell.getY()) {
                    g.setColor(Color.RED);  
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
}
