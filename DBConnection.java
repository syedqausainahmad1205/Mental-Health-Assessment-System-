import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection connect() {
        String url = "jdbc:sqlite:mental_health.db";

        try {
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
