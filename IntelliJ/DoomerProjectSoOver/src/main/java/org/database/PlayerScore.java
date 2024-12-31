package org.database;

public class PlayerScore {
    private int id;
    private String name;
    private int solved;
    private int steps;

    public PlayerScore(String name, int solved, int steps) {
        this.name = name;
        this.solved = solved;
        this.steps = steps;
    }

    public PlayerScore(int id, String name, int solved, int steps) {
        this.id = id;
        this.name = name;
        this.solved = solved;
        this.steps = steps;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getSolved() { return solved; }
    public int getSteps() { return steps; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSolved(int solved) { this.solved = solved; }
    public void setSteps(int steps) { this.steps = steps; }
}