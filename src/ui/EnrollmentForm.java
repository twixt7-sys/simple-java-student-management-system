package src.ui;

import src.models.Enrollment;
import src.models.Student;
import src.exceptions.InvalidInputException;
import src.models.Course;
import src.services.EnrollmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Swing form for managing student enrollments.
public class EnrollmentForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedEnrollmentId = -1;

    private JTextField txtStudentId;
    private JTextField txtCourseId;
    private JTextField txtSemester;
    private JTextField txtSchoolYear;

    private final EnrollmentService enrollmentService;

    public EnrollmentForm() {
        enrollmentService = new EnrollmentService();
        initialize();
        loadEnrollments();
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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        JButton btnEnroll = new JButton("Enroll");
        btnEnroll.addActionListener(e -> enrollStudent());
        buttonPanel.add(btnEnroll);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteEnrollment());
        buttonPanel.add(btnDelete);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Student ID", "Course ID", "Semester", "Year"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    selectedEnrollmentId = (int) tableModel.getValueAt(row, 0);
                    txtStudentId.setText(tableModel.getValueAt(row, 1).toString());
                    txtCourseId.setText(tableModel.getValueAt(row, 2).toString());
                    txtSemester.setText(tableModel.getValueAt(row, 3).toString());
                    txtSchoolYear.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

    }

    private void loadEnrollments() {
        tableModel.setRowCount(0);
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        for (Enrollment e : enrollments) {
            tableModel.addRow(new Object[]{
                e.getId(),
                e.getStudent().getId(),
                e.getCourse().getId(),
                e.getSemester(),
                e.getSchoolYear()
            });
        }
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

            loadEnrollments();
            clearFields();

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteEnrollment() {
        if (selectedEnrollmentId == -1) {
            JOptionPane.showMessageDialog(this, "Select an enrollment first");
            return;
        }

        enrollmentService.removeEnrollment(selectedEnrollmentId);
        loadEnrollments();
        clearFields();
        JOptionPane.showMessageDialog(this, "Enrollment deleted");
        loadEnrollments();
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtCourseId.setText("");
        txtSemester.setText("");
        txtSchoolYear.setText("");
        selectedEnrollmentId = -1;
    }
}
