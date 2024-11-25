import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame {

    private JFrame frame;
    private BoardGUI boardGUI;

    private final int INITIAL_BOARD_SIZE = 5;

    public GameFrame() {
        frame = new JFrame("Space Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            Image icon = ImageIO.read(getClass().getResource("/b8f57b1e2badfd1471fd45425ce5d430 (1).jpg"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            System.err.println("Icon image not found!");
        }

        boardGUI = new BoardGUI(frame);
        boardGUI.initializeGame(INITIAL_BOARD_SIZE);

        frame.getContentPane().add(boardGUI.boardPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenu newMenu = new JMenu("New");
        gameMenu.add(newMenu);

        int[] boardSizes = new int[]{5, 7, 9};
        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(boardSize + " x " + boardSize);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(e -> {
                frame.getContentPane().remove(boardGUI.boardPanel);
                boardGUI.initializeGame(boardSize);
                frame.getContentPane().add(boardGUI.boardPanel, BorderLayout.CENTER);
                frame.pack();
            });
        }

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(e -> System.exit(0));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
