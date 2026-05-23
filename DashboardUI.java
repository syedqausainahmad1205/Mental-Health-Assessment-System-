import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    private int userId;

    public DashboardUI(int userId) {
        this.userId = userId;

        setTitle("Dashboard - Mental Health App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        // Username (FIXED POSITION)
        String userName = UserService.getUserName(userId);
        JLabel welcome = new JLabel("Welcome, " + userName + "!");
        welcome.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        add(welcome, gbc);

        // Take Assessment Button
        JButton assessmentBtn = new JButton("Take Assessment");
        gbc.gridy = 2;
        add(assessmentBtn, gbc);

        // View History Button
        JButton historyBtn = new JButton("View History");
        gbc.gridy = 3;
        add(historyBtn, gbc);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        gbc.gridy = 4;
        add(logoutBtn, gbc);

        // Actions

        assessmentBtn.addActionListener(e -> {
            new AssessmentUI(userId);
            dispose();
        });

        historyBtn.addActionListener(e -> {
            new HistoryUI(userId);
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                new LoginUI();
                dispose();
            }
        });

        setVisible(true);
    }
}