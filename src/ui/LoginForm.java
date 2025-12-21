package src.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import src.exceptions.InvalidInputException;
import src.models.UserAccount;
import src.services.UserAccountService;
import src.ui.components.Theme;
import src.ui.components.UIEffects;

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
        setLayout(new GridBagLayout());
        setBackground(Theme.DARK_BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.DARK_SURFACE);
        card.setBorder(new CompoundBorder(
                new LineBorder(Theme.DARK_SURFACE_LIGHT, 1),
                new EmptyBorder(32, 36, 36, 36)
        ));
        card.setMaximumSize(new Dimension(380, 400));
        card.setPreferredSize(new Dimension(380, 400));

        JLabel title = new JLabel("Welcome Back");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setForeground(Theme.DARK_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Sign in to your account");
        subtitle.setFont(subtitle.getFont().deriveFont(12f));
        subtitle.setForeground(Theme.DARK_TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Theme.DARK_TEXT);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD, 12f));

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUsername.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        UIEffects.styleTextField(txtUsername);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Theme.DARK_TEXT);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.BOLD, 12f));

        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPassword.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        UIEffects.styleTextField(txtPassword);

        JButton btnLogin = new JButton("Sign In");
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnLogin.setPreferredSize(new Dimension(Integer.MAX_VALUE, 44));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setFont(btnLogin.getFont().deriveFont(Font.BOLD, 13f));
        UIEffects.styleButton(btnLogin);
        btnLogin.addActionListener(e -> login());

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(28));
        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(txtUsername);
        card.add(Box.createVerticalStrut(14));
        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(24));
        card.add(btnLogin);

        add(card);
    }

    private void login() {
        try {
            UserAccount user = userService.login(
                    txtUsername.getText(),
                    new String(txtPassword.getPassword())
            );

            JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername());
            mainFrame.showMainSystem();

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error");
        }
    }
}
