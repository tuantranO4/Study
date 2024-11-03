/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author bli
 */
public class CalcGUI {
    //GUI declaration
    private JFrame frame;
    private JPanel numPanel;
    private JPanel buttonPanel;
    private JTextField operand1;
    private JTextField operand2;
    private JTextField result;

    public CalcGUI(int fieldWidth) {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //press X to close
        
        numPanel = new JPanel();
        numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.Y_AXIS)); //vertical layout
        operand1 = new JTextField(fieldWidth); //var1
        numPanel.add(operand1);
        operand2 = new JTextField(fieldWidth); //var2
        numPanel.add(operand2);
        result = new JTextField(fieldWidth); //res
        numPanel.add(result);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1)); //5 rows, 1 column (5 operation)
        JButton addButton = new JButton("+");
        addButton.addActionListener(new CalcActionListener(Operation.ADD));
        buttonPanel.add(addButton);
        JButton subButton = new JButton("-");
        subButton.addActionListener(new CalcActionListener(Operation.SUB));
        buttonPanel.add(subButton);
        JButton mulButton = new JButton("*");
        mulButton.addActionListener(new CalcActionListener(Operation.MUL));
        buttonPanel.add(mulButton);
        JButton divButton = new JButton("/");
        divButton.addActionListener(new CalcActionListener(Operation.DIV));
        buttonPanel.add(divButton);
        JButton powButton = new JButton("^");
        powButton.addActionListener(new CalcActionListener(Operation.POW));
        buttonPanel.add(powButton);
        
        frame.getContentPane().add(BorderLayout.WEST, numPanel); //add number to left side of panel (west)
        frame.getContentPane().add(BorderLayout.EAST, buttonPanel); //add operations buttons to right side of panel (east)
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu calcMenu = new JMenu("Calc"); //label Calc on Menu dropdown
        menuBar.add(calcMenu);
        JMenuItem exitMenuItem = new JMenuItem("Exit");//Exit on dropdown to quit System.exit(0);
        calcMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
                
        frame.setResizable(false); //disable resize
        frame.pack(); //packing frame (manually setting window size)
        frame.setVisible(true);
    }


    class CalcActionListener implements ActionListener {

        private final Operation operation; //ENUM java file

        public CalcActionListener(Operation operation) {
            this.operation = operation;
        } //construct

        @Override
        public void actionPerformed(ActionEvent ae) { //When a button is clicked, actionPerformed is triggered. It overrides actionPerformed in ActionListener package interface
            try {
                double op1 = Double.parseDouble(operand1.getText()); //read input on numPanel
                double op2 = Double.parseDouble(operand2.getText());
                
                double res = switch (operation) {
                    case ADD -> op1 + op2;
                    case SUB -> op1 - op2;
                    case MUL -> op1 * op2;
                    case DIV -> op1 / op2;
                    case POW -> Math.pow(op1, op2);
                };

                result.setText(String.valueOf(res)); //settext: write output
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "The numbers are in the wrong format!", "Error", JOptionPane.ERROR_MESSAGE); //error catching
            }
        }
    }
}