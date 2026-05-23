import java.sql.*;
import java.util.*;

public class HistoryService {

    public static List<History> getHistory(int userId) {

        List<History> historyList = new ArrayList<>();

        String query = "SELECT date_taken, total_score, category FROM assessments WHERE user_id = ? ORDER BY date_taken DESC";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                System.out.println("Database connection failed.");
                return historyList;
            }

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                historyList.add(new History(
                        rs.getString("date_taken"),
                        rs.getInt("total_score"),
                        rs.getString("category")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error fetching history.");
        }

        return historyList;
    }
}