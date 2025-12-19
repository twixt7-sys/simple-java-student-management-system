package src.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout mainLayout;
    private CardLayout formsLayout;

    private JPanel contentPanel;
    private JPanel formsPanel;

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("Student Management System");
        setSize(520, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // main card layout (LOGIN / SYSTEM)
        mainLayout = new CardLayout();
        contentPanel = new JPanel(mainLayout);

        // ---------- FORMS ----------
        StudentForm studentForm = new StudentForm();
        CourseForm courseForm = new CourseForm();
        EnrollmentForm enrollmentForm = new EnrollmentForm();

        formsLayout = new CardLayout();
        formsPanel = new JPanel(formsLayout);
        formsPanel.add(studentForm, "STUDENT");
        formsPanel.add(courseForm, "COURSE");
        formsPanel.add(enrollmentForm, "ENROLLMENT");

        // ---------- MENU ----------
        JPanel menuPanel = new JPanel(new FlowLayout());

        JButton btnStudents = new JButton("Students");
        btnStudents.addActionListener(e
                -> formsLayout.show(formsPanel, "STUDENT"));

        JButton btnCourses = new JButton("Courses");
        btnCourses.addActionListener(e
                -> formsLayout.show(formsPanel, "COURSE"));

        JButton btnEnrollments = new JButton("Enrollments");
        btnEnrollments.addActionListener(e
                -> formsLayout.show(formsPanel, "ENROLLMENT"));

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                logout();
            }
        });

        menuPanel.add(btnStudents);
        menuPanel.add(btnCourses);
        menuPanel.add(btnEnrollments);
        menuPanel.add(btnLogout);

        // ---------- SYSTEM PANEL ----------
        JPanel systemPanel = new JPanel(new BorderLayout());
        systemPanel.add(menuPanel, BorderLayout.NORTH);
        systemPanel.add(formsPanel, BorderLayout.CENTER);

        // ---------- LOGIN ----------
        LoginForm loginForm = new LoginForm(this);

        contentPanel.add(loginForm, "LOGIN");
        contentPanel.add(systemPanel, "SYSTEM");

        add(contentPanel);

        // start at login
        mainLayout.show(contentPanel, "LOGIN");
    }

    // called after successful login
    public void showMainSystem() {
        mainLayout.show(contentPanel, "SYSTEM");
        formsLayout.show(formsPanel, "STUDENT");
    }

    // logout action
    public void logout() {
        mainLayout.show(contentPanel, "LOGIN");
    }
}
