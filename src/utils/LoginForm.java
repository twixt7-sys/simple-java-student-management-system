package src.utils;

import src.models.UserAccount;
import src.services.UserAccountService;
import src.ui.MainFrame;
import src.exceptions.InvalidInputException;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JPanel {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private final UserAccountService userService;
    private final MainFrame mainFrame;

    public LoginForm(MainFrame frame) {
        this.mainFrame = frame;
        this.userService = new UserAccountService();
        initialize();
    }

    private void initialize() {
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> login());
        add(btnLogin);
    }

    private void login() {
        try {
            UserAccount user = userService.login(
                    txtUsername.getText(),
                    new String(txtPassword.getPassword())
            );

            JOptionPane.showMessageDialog(this,
                    "Welcome " + user.getUsername());

            mainFrame.showMainSystem();

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
