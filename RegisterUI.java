import javax.swing.*;
import java.awt.*;

public class RegisterUI extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegisterUI() {

        setTitle("Register - Mental Health App");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel title = new JLabel("Create New Account");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(15);
        add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Register Button
        JButton registerBtn = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(registerBtn, gbc);

        // Back Button
        JButton backBtn = new JButton("Back to Login");
        gbc.gridx = 1;
        add(backBtn, gbc);

        // Actions
        registerBtn.addActionListener(e -> handleRegister());
        backBtn.addActionListener(e -> {
            new LoginUI();
            dispose();
        });

        setVisible(true);
    }

    private void handleRegister() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters!");
            return;
        }

        boolean success = UserService.registerUser(name, email, password);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful!");

            new LoginUI();
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Email already exists or registration failed.");
        }
    }
}
