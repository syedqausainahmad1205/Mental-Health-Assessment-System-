import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AssessmentUI extends JFrame {

    private final Map<Integer, ButtonGroup> responseMap = new HashMap<>();
    private final int userId;
    private final JButton submitBtn;

    public AssessmentUI(int userId) {
        this.userId = userId;

        setTitle("Mental Health Assessment");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        java.util.List<Question> questions = QuestionService.getQuestions();

        for (Question q : questions) {

            JPanel qPanel = new JPanel();
            qPanel.setLayout(new BoxLayout(qPanel, BoxLayout.Y_AXIS));
            qPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel questionLabel = new JLabel("Q: " + q.text);
            qPanel.add(questionLabel);

            ButtonGroup group = new ButtonGroup();

            for (int i = 0; i <= 3; i++) {
                JRadioButton option = new JRadioButton(String.valueOf(i));
                option.setActionCommand(String.valueOf(i));

                // cleaner lambda (no warning)
                option.addActionListener(evt -> checkAllAnswered());

                group.add(option);
                qPanel.add(option);
            }

            responseMap.put(q.id, group);
            mainPanel.add(qPanel);
        }

        // Submit button
        submitBtn = new JButton("Submit");
        submitBtn.setEnabled(false);
        submitBtn.addActionListener(evt -> handleSubmit());

        JPanel btnPanel = new JPanel();
        btnPanel.add(submitBtn);

        mainPanel.add(btnPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);

        setVisible(true);
    }

    // Enable submit only when all answered
    private void checkAllAnswered() {
        for (ButtonGroup group : responseMap.values()) {
            if (group.getSelection() == null) {
                submitBtn.setEnabled(false);
                return;
            }
        }
        submitBtn.setEnabled(true);
    }

    private void handleSubmit() {

        Map<Integer, Integer> responses = new HashMap<>();

        for (Map.Entry<Integer, ButtonGroup> entry : responseMap.entrySet()) {

            ButtonGroup group = entry.getValue();

            int value = Integer.parseInt(group.getSelection().getActionCommand());
            responses.put(entry.getKey(), value);
        }

        AssessmentResult result = Assessment.processAssessment(userId, responses);

        JOptionPane.showMessageDialog(this,
                "Score: " + result.getScore() +
                        "\nCategory: " + result.getCategory());

        // back to dashboard
        new DashboardUI(userId);
        dispose();
    }
}