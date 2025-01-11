/**
 * The YogiGUI class is responsible for creating the main JFrame for the Yogi Bear game.
 * It initializes the game window, sets up the GameEngine, and starts the game thread.
 */
package Yogi;

import javax.swing.JFrame;

/**
 * The YogiGUI class represents the main graphical user interface for the Yogi
 * Bear game.
 */
public class YogiGUI {

    /**
     * Constructor for YogiGUI.
     * Creates a new JFrame for the Yogi Bear game, sets up the game window
     * properties, and starts the game thread.
     */
    public YogiGUI() {
        // Create a new JFrame
        JFrame frame = new JFrame("Yogi Bear");

        // Set default close operation and make the frame non-resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create a new instance of the GameEngine
        GameEngine gameArea = new GameEngine();

        // Add the GameEngine to the JFrame
        frame.add(gameArea);

        // Pack the components, set the frame location to the center of the screen, and
        // make it visible
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the game thread
        gameArea.startGameThread();
    }
}
