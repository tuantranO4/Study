package org.database;

public class PlayerScore {
    private int id;
    private String name;
    private int lives;
    private int steps;

    public PlayerScore(String name, int lives, int steps) {
        this.name = name;
        this.lives = lives;
        this.steps = steps;
    }

    public PlayerScore(int id, String name, int lives, int steps) {
        this.id = id;
        this.name = name;
        this.lives = lives;
        this.steps = steps;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getLives() { return lives; }
    public int getSteps() { return steps; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLives(int lives) { this.lives = lives; }
    public void setSteps(int steps) { this.steps = steps; }
}