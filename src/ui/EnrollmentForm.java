package src.ui;

import src.models.Enrollment;
import src.models.Student;
import src.services.EnrollmentService;
import src.models.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Swing form for managing student enrollments.
public class EnrollmentForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtStudentId;
    private JTextField txtCourseId;
    private JTextField txtSemester;
    private JTextField txtSchoolYear;

    private final EnrollmentService enrollmentService;

    public EnrollmentForm() {
        enrollmentService = new EnrollmentService();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        formPanel.add(new JLabel("Student ID:"));
        txtStudentId = new JTextField();
        formPanel.add(txtStudentId);

        formPanel.add(new JLabel("Course ID:"));
        txtCourseId = new JTextField();
        formPanel.add(txtCourseId);

        formPanel.add(new JLabel("Semester:"));
        txtSemester = new JTextField();
        formPanel.add(txtSemester);

        formPanel.add(new JLabel("School Year:"));
        txtSchoolYear = new JTextField();
        formPanel.add(txtSchoolYear);

        JButton btnEnroll = new JButton("Enroll");
        btnEnroll.addActionListener(e -> enrollStudent());

        add(formPanel, BorderLayout.NORTH);
        add(btnEnroll, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
                new Object[]{"Student ID", "Course ID", "Semester", "Year"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void enrollStudent() {
        try {
            Student student = new Student();
            student.setId(Integer.parseInt(txtStudentId.getText()));

            Course course = new Course();
            course.setId(Integer.parseInt(txtCourseId.getText()));

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setSemester(txtSemester.getText());
            enrollment.setSchoolYear(txtSchoolYear.getText());

            enrollmentService.enrollStudent(enrollment);

            JOptionPane.showMessageDialog(this, "Enrollment successful");
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtCourseId.setText("");
        txtSemester.setText("");
        txtSchoolYear.setText("");
    }
}
