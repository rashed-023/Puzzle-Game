
import java.sql.*;

public class PlayerDB {
    private static final String URL = "jdbc:mysql://localhost:3306/puzzlegame";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Add your XAMPP MySQL password if any

    public static void initializePlayer(String playerName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String checkSql = "SELECT * FROM player_stats WHERE player_name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, playerName);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                String insertSql = "INSERT INTO player_stats (player_name, solve_count) VALUES (?, 0)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, playerName);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void incrementSolveCount(String playerName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String updateSql = "UPDATE player_stats SET solve_count = solve_count + 1 WHERE player_name = ?";
            PreparedStatement stmt = conn.prepareStatement(updateSql);
            stmt.setString(1, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getSolveCount(String playerName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String querySql = "SELECT solve_count FROM player_stats WHERE player_name = ?";
            PreparedStatement stmt = conn.prepareStatement(querySql);
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("solve_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
