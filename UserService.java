import java.sql.*;

public class UserService {

    public static boolean registerUser(String name, String email, String password) {

        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                return false;
            }

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            // Handle duplicate email
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE")) {
                return false;
            }

            return false;
        }
    }

    public static int loginUser(String email, String password) {

        String query = "SELECT user_id FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                return -1;
            }

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            System.out.println("Login error.");
        }

        return -1;
    }
    public static String getUserName(int userId) {
        String query = "SELECT name FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.connect()) {
            if (conn == null) return "User";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            // keep UI clean
        }
        return "User";
    }
}