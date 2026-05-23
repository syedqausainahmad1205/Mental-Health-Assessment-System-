import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryUI extends JFrame {

    private int userId;

    public HistoryUI(int userId) {
        this.userId = userId;

        setTitle("History - Mental Health App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Your Assessment History", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Table
        String[] columns = {"Date", "Score", "Category"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        // Fetch data
        List<History> historyList = HistoryService.getHistory(userId);

        if (historyList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No history found.");
        } else {
            for (History h : historyList) {
                model.addRow(new Object[]{
                        h.getDate(),
                        h.getScore(),
                        h.getCategory()
                });
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.addActionListener(e -> {
            new DashboardUI(userId);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
