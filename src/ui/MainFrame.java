package src.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("Student Management System");
        setSize(520, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        StudentForm studentForm = new StudentForm();
        CourseForm courseForm = new CourseForm();
        EnrollmentForm enrollmentForm = new EnrollmentForm();

        contentPanel.add(studentForm, "STUDENT");
        contentPanel.add(courseForm, "COURSE");
        contentPanel.add(enrollmentForm, "ENROLLMENT");

        JPanel menuPanel = new JPanel(new FlowLayout());

        JButton btnStudents = new JButton("Students");
        btnStudents.addActionListener(e
                -> cardLayout.show(contentPanel, "STUDENT"));

        JButton btnCourses = new JButton("Courses");
        btnCourses.addActionListener(e
                -> cardLayout.show(contentPanel, "COURSE"));

        JButton btnEnrollments = new JButton("Enrollments");
        btnEnrollments.addActionListener(e
                -> cardLayout.show(contentPanel, "ENROLLMENT"));

        menuPanel.add(btnStudents);
        menuPanel.add(btnCourses);
        menuPanel.add(btnEnrollments);

        add(menuPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
}
