/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author bli
 */
public class GameEngine extends JPanel { //inherit Jpanel

    private final int FPS = 240;
    private final int PADDLE_Y = 550;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 20;
    private final int PADDLE_MOVEMENT = 2;
    private final int BALL_RADIUS = 20;

    private boolean paused = false;
    private Image background;
    private int levelNum = 0;
    private Level level;
    private Ball ball;
    private Paddle paddle;
    private Timer newFrameTimer;

    public GameEngine() {

        super(); //call the jpanel constructor
        background = new ImageIcon("data/images/background.jpg").getImage(); //load background

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paddle.setVelx(-PADDLE_MOVEMENT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paddle.setVelx(PADDLE_MOVEMENT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paddle.setVelx(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        }); //keybinds

        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener()); //Calls the actionPerformed method every (1000 / FPS) milliseconds
        newFrameTimer.start();
    }

    public void restart() {
        try {
            level = new Level("data/levels/level0" + levelNum + ".txt"); //eg: level01.txt
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex); //try-catch block to load level
        }
        Image paddleImage = new ImageIcon("data/images/paddle.png").getImage();
        paddle = new Paddle(350, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT, paddleImage); //init paddle
        Image ballImage = new ImageIcon("data/images/ball.png").getImage();
        ball = new Ball(400, 300, BALL_RADIUS, BALL_RADIUS, ballImage); //init ball
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 600, null);
        level.draw(grphcs);
        paddle.draw(grphcs);
        ball.draw(grphcs);
    } //making graphics for the game

    class NewFrameListener implements ActionListener { //interface
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) { //
                ball.moveX();
                if (level.collides(ball)) { //check for collision between ball and bricks (in level.java), yes -> we invert direction
                    ball.invertVelX();
                }
                if (!ball.moveY()) { //check y coord, if ball out (<=600) = restart
                    levelNum = 0;
                    restart();
                }
                if (level.collides(ball)) {
                    ball.invertVelY();
                }
                if (paddle.collides(ball)) {
                    ball.invertVelY();
                }

                paddle.move(); //keep update on each frame
            }
            if (level.isOver()) {
                levelNum = (levelNum+1) % 2;
                restart();
            }
            repaint(); //refresh the visual representation
        }

    }
}
