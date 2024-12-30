package org.mainframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import org.gamelogic.Game;
import org.gamelogic.LevelItem;
import org.gamelogic.Position;
import org.res.ResourceLoader;

public class Board extends JPanel {
    private Game game;
    private final Image drake, destination, player, wall, empty;
    private double scale;
    private int scaled_size;
    private final int tile_size = 32;
    private Image black;
    
    public Board(Game g) throws IOException{
        black = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)black.getGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 1, 1);
        g2d.dispose();
        game = g;
        scale = 1.5;
        scaled_size = (int)(scale * tile_size);
        drake = ResourceLoader.loadImage("drake.png");
        destination = ResourceLoader.loadImage("destination.png");
        player = ResourceLoader.loadImage("player.png");
        wall = ResourceLoader.loadImage("wall.png");
        empty = ResourceLoader.loadImage("empty.png");
    }
    
    public boolean setScale(double scale){
        this.scale = scale;
        scaled_size = (int)(scale * tile_size);
        return refresh();
    }
    
    public boolean refresh(){
        if (!game.isLevelLoaded()) return false;
        Dimension dim = new Dimension(game.getLevelCols() * scaled_size, game.getLevelRows() * scaled_size);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (!game.isLevelLoaded()) return;
        Graphics2D gr = (Graphics2D)g;
        int w = game.getLevelCols();
        int h = game.getLevelRows();
        Position p = game.getPlayerPos();
        for (int y = 0; y < h; y++){
            for (int x = 0; x < w; x++){
                Image img = null;
                LevelItem li = game.getItem(y, x);
                switch (li){
                    case DRAKE: img = drake; break;
                    case DESTINATION: img = destination; break;
                    case WALL: img = wall; break;
                    case EMPTY: img = empty; break;
                }
                 if (!game.getVisibility(y, x)) {
                     img = black;
                 }
                if (p.x == x && p.y == y) img = player;
                if (img == null) continue;
                gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
            }
        }
    }
    
}
