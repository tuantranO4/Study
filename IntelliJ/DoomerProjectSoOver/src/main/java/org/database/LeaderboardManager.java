package org.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardManager {
    public Connection conn;

    public LeaderboardManager() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addScore(PlayerScore score) {
        String sql = "INSERT INTO leaderboard (player_name, labyrinths_completed, steps) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, score.getName());
            pstmt.setInt(2, score.getSolved());
            pstmt.setInt(3, score.getSteps());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerScore> getTopScores(int limit) {
        List<PlayerScore> scores = new ArrayList<>();
        String sql = "SELECT * FROM leaderboard ORDER BY labyrinths_completed DESC, steps ASC LIMIT ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                scores.add(new PlayerScore(
                        rs.getInt("id"),
                        rs.getString("player_name"),
                        rs.getInt("labyrinths_completed"),
                        rs.getInt("steps")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
