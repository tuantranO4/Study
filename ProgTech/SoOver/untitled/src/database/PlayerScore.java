package database;

public class PlayerScore {
    private final String name;
    private final int lives;
    private final int steps;

    public PlayerScore(String name, int lives, int steps) {
        this.name = name;
        this.lives = lives;
        this.steps = steps;
    }

    public String getName() { return name; }
    public int getLives() { return lives; }
    public int getSteps() { return steps; }
}
