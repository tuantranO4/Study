/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flashcards;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author pinter
 */
public class FlashcardsGUI {

    private ArrayList<Card> cards;
    private int cardNumber;
    private boolean showQuestion;
    private int score;

    private JFrame frame;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTextArea display;
    private JLabel scoreLabel;

    public FlashcardsGUI() {
        frame = new JFrame("Flashcards"); //Title: Flashcard
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //press X to close

        northPanel = new JPanel(); //styling
        display = new JTextArea("Please open a cards file.", 10, 40);
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        northPanel.add(display);
        frame.getContentPane().add(northPanel, BorderLayout.NORTH);
        southPanel = new JPanel();
        scoreLabel = new JLabel("0/0");
        southPanel.add(scoreLabel);

        ArrayList<String> buttonLabels = new ArrayList<>(
                Arrays.asList("Reset", "Toggle Q/A", "Wrong answer", "Good answer"));
        ArrayList<ActionListener> listeners = new ArrayList<ActionListener>(
                Arrays.asList(new ResetButtonActionListener(),
                        new ToggleButtonActionListener(),
                        new AnswerButtonActionListener(false),
                        new AnswerButtonActionListener(true)));
        for (int i = 0; i < buttonLabels.size(); ++i) {
            JButton button = new JButton(buttonLabels.get(i));
            southPanel.add(button);
            button.addActionListener(listeners.get(i));
        } //add interaction to buttonLabels by iteration
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);//adding southPanel

        //::DOWN HERE IS THE MENU BAR DROP DOWN CONFIGURATION::
        JMenuBar menuBar = new JMenuBar();//Creating the Menu Bar: JMenu bar object - main container for menus
        frame.setJMenuBar(menuBar); // menuBar to the top of the main application window (frame), where it will be visible to users.
        JMenu fileMenu = new JMenu("File"); //Creating the "File" Menu: it's a dropdown menu in the menuBar
        menuBar.add(fileMenu);//attaches fileMenu to the menu bar, so it appears under the "File" name to toggle dropdown.

        JMenuItem openMenuItem = new JMenuItem("Open..."); //fileMenu component: add "Open..." option in dropdown menu
        openMenuItem.addActionListener(new OpenActionListener());
        fileMenu.add(openMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        frame.pack(); // sizes the window to fit its components (menu, panels, etc.) snugly
        frame.setVisible(true); // makes the entire window visible to the user
    }


    public void reset() {
        score = 0;
        cardNumber = 0;
        showQuestion = true;
        updateScore();
        updateDisplay();
    }

    private void updateScore() {
        scoreLabel.setText(score + "/" + cardNumber); //display score
    }

    private void updateDisplay() {
        if (cards != null && cardNumber < cards.size()) {
            if (showQuestion) {
                display.setText(cards.get(cardNumber).getQuestion()); //showQ = true -> question, else answer
            } else {
                display.setText(cards.get(cardNumber).getAnswer());
            }
        } else if (cards != null) { //If all cards have been reviewed
            display.setText("The End");
        }
    }

    class OpenActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();//choose file with JFileChooser
            int returnVal = fc.showOpenDialog(frame); //0 or 1 to approve file choosing
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile(); //get file with getSelectedFile() function
                try (BufferedReader br = new BufferedReader(new FileReader(file))) { //run bufferedReader
                    String line;
                    cards = new ArrayList<>();
                    while ((line = br.readLine()) != null) { //while loop with != null, it reads until reaching the end of the file
                        String[] qa = line.split("~"); //qa[0] holds the question, qa[1] holds the answer.
                        if (qa.length == 2) {
                            cards.add(new Card(qa[0], qa[1]));
                        }
                    }
                } catch (FileNotFoundException ex) { //catch exceptions
                    JOptionPane.showMessageDialog(frame, "File not found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "IO error!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                reset();
            }
        }
    }

    class ToggleButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            showQuestion = !showQuestion; //flipping showQuestion bool, to toggle between show and unshow display
            updateDisplay();
        }

    }

    class ResetButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    }

    class AnswerButtonActionListener implements ActionListener {//true\false for Good/Wrong answer
        private boolean incScore;
        public AnswerButtonActionListener(boolean incScore) {
            this.incScore = incScore;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cards != null && cardNumber < cards.size()) {
                if (incScore) { //if true(good answer), score ++
                    score++;
                }
                cardNumber++;
                updateScore();
                showQuestion = true;
                updateDisplay(); //set showQ to true -> display question
            }
        }
    }

}
