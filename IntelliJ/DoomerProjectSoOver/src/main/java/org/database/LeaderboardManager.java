package org.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO leaderboard (player_name, lives, steps) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, score.getName());
            pstmt.setInt(2, score.getLives());
            pstmt.setInt(3, score.getSteps());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerScore> getTopScores(int limit) {
        List<PlayerScore> scores = new ArrayList<>();
        String sql = "SELECT * FROM leaderboard ORDER BY lives DESC, steps ASC LIMIT ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                scores.add(new PlayerScore(
                    rs.getInt("id"),
                    rs.getString("player_name"),
                    rs.getInt("lives"),
                    rs.getInt("steps")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }

    public List<PlayerScore> getPlayerHistory(String playerName) {
        List<PlayerScore> scores = new ArrayList<>();
        String sql = "SELECT * FROM leaderboard WHERE player_name = ? ORDER BY lives DESC, steps ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                scores.add(new PlayerScore(
                    rs.getInt("id"),
                    rs.getString("player_name"),
                    rs.getInt("lives"),
                    rs.getInt("steps")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }
}