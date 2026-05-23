import java.sql.*;
import java.util.ArrayList;

public class QuestionService {

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> list = new ArrayList<>();

        try (Connection conn = DBConnection.connect()) {

            String query = "SELECT * FROM questions";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(new Question(
                        rs.getInt("question_id"),
                        rs.getString("question_text")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return list;
    }
}