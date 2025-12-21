package src.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.ui.components.Theme;
import src.ui.components.RoundedButton;

public class MainFrame extends JFrame {

    private CardLayout mainLayout;
    private CardLayout formsLayout;

    private JPanel contentPanel;
    private JPanel formsPanel;
    private JLabel titleLabel;

    public MainFrame() {
        Theme.dark();
        initialize();
    }

    private void initialize() {
        setTitle("Student Management System");
        setSize(1400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Theme.DARK_BG);

        mainLayout = new CardLayout();
        contentPanel = new JPanel(mainLayout);
        contentPanel.setBackground(Theme.DARK_BG);

        formsLayout = new CardLayout();
        formsPanel = new JPanel(formsLayout);
        formsPanel.setBackground(Theme.DARK_BG);
        formsPanel.setBorder(new EmptyBorder(20, 24, 24, 24));

        formsPanel.add(new StudentForm(), "STUDENT");
        formsPanel.add(new CourseForm(), "COURSE");
        formsPanel.add(new EnrollmentForm(), "ENROLLMENT");

        // Top bar with title and theme toggle
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.DARK_SURFACE);
        topBar.setBorder(new EmptyBorder(12, 20, 12, 20));

        // Title on left
        titleLabel = new JLabel("Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, (int) 24f));
        titleLabel.setForeground(Theme.ACCENT_BLUE);

        // Right controls
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightPanel.setBackground(Theme.DARK_SURFACE);
        rightPanel.setOpaque(true);

        JButton btnLogout = new RoundedButton("Logout", Theme.ERROR_RED,
                new Color(220, 100, 100), new Color(180, 50, 50));
        btnLogout.setPreferredSize(new Dimension(100, 36));
        btnLogout.addActionListener(e -> logout());

        rightPanel.add(btnLogout);
        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        // Navigation bar
        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        navBar.setBackground(Theme.DARK_BG);
        navBar.setBorder(new EmptyBorder(0, 20, 0, 20));

        RoundedButton btnStudents = createNavButton("👥 Students", true);
        RoundedButton btnCourses = createNavButton("📚 Courses", false);
        RoundedButton btnEnrollments = createNavButton("📋 Enrollments", false);
        btnStudents.addActionListener(e -> {
            formsLayout.show(formsPanel, "STUDENT");
            titleLabel.setText("Students");
            updateNavButtons(btnStudents, btnCourses, btnEnrollments);
        });

        btnCourses.addActionListener(e -> {
            formsLayout.show(formsPanel, "COURSE");
            titleLabel.setText("Courses");
            updateNavButtons(btnCourses, btnStudents, btnEnrollments);
        });

        btnEnrollments.addActionListener(e -> {
            formsLayout.show(formsPanel, "ENROLLMENT");
            titleLabel.setText("Enrollments");
            updateNavButtons(btnEnrollments, btnStudents, btnCourses);
        });

        navBar.add(btnStudents);
        navBar.add(Box.createHorizontalStrut(8));
        navBar.add(btnCourses);
        navBar.add(Box.createHorizontalStrut(8));
        navBar.add(btnEnrollments);

        // Main system panel
        JPanel systemPanel = new JPanel(new BorderLayout());
        systemPanel.setBackground(Theme.DARK_BG);
        systemPanel.add(topBar, BorderLayout.NORTH);
        systemPanel.add(navBar, BorderLayout.SOUTH);

        // Container for nav + forms
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Theme.DARK_BG);
        mainContainer.add(systemPanel, BorderLayout.NORTH);
        mainContainer.add(formsPanel, BorderLayout.CENTER);

        contentPanel.add(new LoginForm(this), "LOGIN");
        contentPanel.add(mainContainer, "SYSTEM");

        add(contentPanel);
        mainLayout.show(contentPanel, "LOGIN");
    }

    private RoundedButton createNavButton(String text, boolean active) {
        Color baseColor = active ? Theme.ACCENT_BLUE : Theme.DARK_SURFACE_LIGHT;
        Color hoverColor = active ? Theme.ACCENT_BLUE_HOVER : Theme.DARK_SURFACE;
        Color pressColor = active ? Theme.ACCENT_BLUE_DARK : new Color(50, 50, 50);

        RoundedButton btn = new RoundedButton(text, baseColor, hoverColor, pressColor);
        btn.setPreferredSize(new Dimension(140, 44));
        btn.setFont(new Font("Segoe UI", Font.BOLD, (int) 12f));
        return btn;
    }

    private void updateNavButtons(RoundedButton active, RoundedButton... inactive) {
        // Update styling for active/inactive buttons
        for (RoundedButton btn : inactive) {
            // Reset to inactive state
        }
    }

    public void showMainSystem() {
        mainLayout.show(contentPanel, "SYSTEM");
        formsLayout.show(formsPanel, "STUDENT");
        titleLabel.setText("Students");
    }

    public void logout() {
        mainLayout.show(contentPanel, "LOGIN");
    }
}
