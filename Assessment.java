import java.sql.*;
import java.util.*;

public class Assessment {

    // Console version (UI layer)
    public static AssessmentResult startAssessment(int userId) {
        Scanner sc = new Scanner(System.in);

        var questions = QuestionService.getQuestions();

        Map<Integer, Integer> responses = new HashMap<>();

        if (questions.isEmpty()) {
            System.out.println("No questions available. Please contact admin.");
            return new AssessmentResult(0, "N/A");
        }

        for (Question q : questions) {
            System.out.println("\n---------------------------");
            System.out.println("Q: " + q.text);
            System.out.println("0=Not at all | 1=Several days | 2=More than half | 3=Nearly every day");

            int answer;

            while (true) {
                try {
                    answer = Integer.parseInt(sc.nextLine());

                    if (answer >= 0 && answer <= 3) {
                        break;
                    } else {
                        System.out.println("Enter a value between 0 and 3:");
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input. Enter a number (0–3):");
                }
            }

            responses.put(q.id, answer);
        }

        // Delegate logic
        AssessmentResult result = processAssessment(userId, responses);

        System.out.println("\nTotal Score: " + result.getScore());
        System.out.println("Category: " + result.getCategory());

        return result;
    }

    // Core logic (GUI + Console use this)
    public static AssessmentResult processAssessment(int userId, Map<Integer, Integer> responses) {

        int totalScore = 0;

        for (int score : responses.values()) {
            totalScore += score;
        }

        String category = calculateCategory(totalScore);

        saveAssessment(userId, responses, totalScore, category);

        return new AssessmentResult(totalScore, category);
    }

    public static String calculateCategory(int score) {
        if (score <= 4) return "Minimal";
        else if (score <= 9) return "Mild";
        else if (score <= 14) return "Moderate";
        else return "Severe";
    }

    public static void saveAssessment(int userId, Map<Integer, Integer> responses,
                                      int totalScore, String category) {

        String insertAssessment = "INSERT INTO assessments (user_id, date_taken, total_score, category) VALUES (?, datetime('now'), ?, ?)";
        String insertResponse = "INSERT INTO responses (assessment_id, question_id, selected_option, score) VALUES (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = DBConnection.connect();

            if (conn == null) {
                System.out.println("Database connection failed.");
                return;
            }

            conn.setAutoCommit(false);

            int assessmentId = -1;

            try (PreparedStatement ps = conn.prepareStatement(insertAssessment, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, userId);
                ps.setInt(2, totalScore);
                ps.setString(3, category);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        assessmentId = rs.getInt(1);
                    }
                }
            }

            if (assessmentId == -1) {
                System.out.println("Failed to save assessment.");
                conn.rollback();
                return;
            }

            try (PreparedStatement respStmt = conn.prepareStatement(insertResponse)) {

                for (Map.Entry<Integer, Integer> entry : responses.entrySet()) {
                    respStmt.setInt(1, assessmentId);
                    respStmt.setInt(2, entry.getKey());
                    respStmt.setInt(3, entry.getValue());
                    respStmt.setInt(4, entry.getValue());
                    respStmt.addBatch();
                }

                respStmt.executeBatch();
            }

            conn.commit();

            System.out.println("\nAssessment completed and saved successfully!");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                System.out.println("Rollback failed.");
            }
            System.out.println("Failed to save assessment. Please try again.");
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Failed to close connection.");
            }
        }
    }
}