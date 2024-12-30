package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardManager {
    private static final String DB_URL = "jdbc:sqlite:diddler_escape.db";
    
    public LeaderboardManager() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS leaderboard (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                lives INTEGER NOT NULL,
                steps INTEGER NOT NULL,
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
            )
            """;
            
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
    
    public void addScore(PlayerScore score) {
        String insertSQL = "INSERT INTO leaderboard (name, lives, steps) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, score.getName());
            pstmt.setInt(2, score.getLives());
            pstmt.setInt(3, score.getSteps());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error adding score: " + e.getMessage());
        }
    }
    
    public List<PlayerScore> getTopScores(int limit) {
        List<PlayerScore> scores = new ArrayList<>();
        String selectSQL = """
            SELECT name, lives, steps 
            FROM leaderboard 
            ORDER BY lives DESC, steps ASC 
            LIMIT ?
            """;
            
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                scores.add(new PlayerScore(
                    rs.getString("name"),
                    rs.getInt("lives"),
                    rs.getInt("steps")
                ));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving scores: " + e.getMessage());
        }
        
        return scores;
    }
    
    // DEBUG clear all scores testing
    public void clearScores() {
        String deleteSQL = "DELETE FROM leaderboard";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(deleteSQL);
        } catch (SQLException e) {
            System.err.println("Error clearing scores: " + e.getMessage());
        }
    }
}