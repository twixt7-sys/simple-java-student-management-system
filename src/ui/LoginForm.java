package src.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.exceptions.InvalidInputException;
import src.models.UserAccount;
import src.services.UserAccountService;
import src.ui.components.RoundedButton;
import src.ui.components.RoundedTextField;
import src.ui.components.Theme;

public class LoginForm extends JPanel {

    private RoundedTextField txtUsername;
    private JPasswordField txtPassword;
    private RoundedButton btnLogin;

    private final UserAccountService userService;
    private final MainFrame mainFrame;

    public LoginForm(MainFrame frame) {
        this.mainFrame = frame;
        this.userService = new UserAccountService();
        initialize();
    }

    private void initialize() {
        setLayout(new GridBagLayout());
        setBackground(Theme.DARK_BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.DARK_SURFACE);
        card.setBorder(new EmptyBorder(40, 40, 40, 40));
        card.setMaximumSize(new Dimension(420, 480));
        card.setPreferredSize(new Dimension(420, 480));

        // Title
        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("Segoe UI", Font.BOLD, (int) 32f));
        title.setForeground(Theme.DARK_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Student Management System");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, (int) 13f));
        subtitle.setForeground(Theme.DARK_TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username section
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Theme.DARK_TEXT);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));

        txtUsername = new RoundedTextField();
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        txtUsername.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));

        // Password section
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Theme.DARK_TEXT);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));

        txtPassword = new JPasswordField();
        txtPassword.setBackground(Theme.DARK_SURFACE);
        txtPassword.setForeground(Theme.DARK_TEXT);
        txtPassword.setCaretColor(Theme.DARK_TEXT);
        txtPassword.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, (int) 13f));
        txtPassword.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        // Login button
        btnLogin = new RoundedButton("Sign In", Theme.ACCENT_BLUE,
                Theme.ACCENT_BLUE_HOVER, Theme.ACCENT_BLUE_DARK);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnLogin.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, (int) 14f));
        btnLogin.addActionListener(e -> login());

        // Help text
        JLabel helpText = new JLabel("Demo credentials: admin / admin123");
        helpText.setForeground(Theme.DARK_TEXT_SECONDARY);
        helpText.setFont(new Font("Segoe UI", Font.PLAIN, (int) 10f));
        helpText.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assembly
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(40));
        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(txtUsername);
        card.add(Box.createVerticalStrut(18));
        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(32));
        card.add(btnLogin);
        card.add(Box.createVerticalStrut(20));
        card.add(helpText);

        add(card);
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }

        try {
            btnLogin.setEnabled(false);
            btnLogin.setText("Signing in...");

            UserAccount user = userService.login(username, password);

            JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername() + "! 🎓",
                    "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.showMainSystem();

        } catch (InvalidInputException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Login failed. Please check your credentials.");
        } finally {
            btnLogin.setEnabled(true);
            btnLogin.setText("Sign In");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
