package src.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.ui.components.Theme;
import src.ui.components.UIEffects;

public class MainFrame extends JFrame {

    private CardLayout mainLayout;
    private CardLayout formsLayout;

    private JPanel contentPanel;
    private JPanel formsPanel;

    public MainFrame() {
        Theme.dark();
        initialize();
    }

    private void initialize() {
        setTitle("Student Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Theme.DARK_BG);

        mainLayout = new CardLayout();
        contentPanel = new JPanel(mainLayout);
        contentPanel.setBackground(Theme.DARK_BG);

        formsLayout = new CardLayout();
        formsPanel = new JPanel(formsLayout);
        formsPanel.setBackground(Theme.DARK_BG);
        formsPanel.setBorder(new EmptyBorder(24, 28, 28, 28));

        formsPanel.add(new StudentForm(), "STUDENT");
        formsPanel.add(new CourseForm(), "COURSE");
        formsPanel.add(new EnrollmentForm(), "ENROLLMENT");

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.DARK_SURFACE);
        topBar.setBorder(new EmptyBorder(14, 20, 14, 20));

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        nav.setBackground(Theme.DARK_SURFACE);
        nav.setOpaque(true);

        JButton btnStudents = new JButton("Students");
        btnStudents.addActionListener(e -> formsLayout.show(formsPanel, "STUDENT"));
        UIEffects.styleButton(btnStudents);

        JButton btnCourses = new JButton("Courses");
        btnCourses.addActionListener(e -> formsLayout.show(formsPanel, "COURSE"));
        UIEffects.styleButton(btnCourses);

        JButton btnEnrollments = new JButton("Enrollments");
        btnEnrollments.addActionListener(e -> formsLayout.show(formsPanel, "ENROLLMENT"));
        UIEffects.styleButton(btnEnrollments);

        nav.add(btnStudents);
        nav.add(btnCourses);
        nav.add(btnEnrollments);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightPanel.setBackground(Theme.DARK_SURFACE);
        rightPanel.setOpaque(true);

        JButton btnTheme = new JButton("🌙 Dark");
        btnTheme.addActionListener(e -> {
            Theme.dark();
            SwingUtilities.updateComponentTreeUI(this);
        });
        UIEffects.styleButton(btnTheme);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> logout());
        UIEffects.styleButton(btnLogout);

        rightPanel.add(btnTheme);
        rightPanel.add(btnLogout);

        topBar.add(nav, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        JPanel systemPanel = new JPanel(new BorderLayout());
        systemPanel.setBackground(Theme.DARK_BG);
        systemPanel.add(topBar, BorderLayout.NORTH);
        systemPanel.add(formsPanel, BorderLayout.CENTER);

        contentPanel.add(new LoginForm(this), "LOGIN");
        contentPanel.add(systemPanel, "SYSTEM");

        add(contentPanel);
        mainLayout.show(contentPanel, "LOGIN");
    }

    public void showMainSystem() {
        mainLayout.show(contentPanel, "SYSTEM");
        formsLayout.show(formsPanel, "STUDENT");
    }

    public void logout() {
        mainLayout.show(contentPanel, "LOGIN");
    }
}
