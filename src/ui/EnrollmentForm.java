package src.ui;

import src.models.Enrollment;
import src.models.Student;
import src.exceptions.InvalidInputException;
import src.models.Course;
import src.services.EnrollmentService;
import src.ui.components.Theme;
import src.ui.components.UIEffects;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        setBackground(Theme.DARK_BG);
        initialize();
        loadEnrollments();
    }

    private void initialize() {
        setLayout(new BorderLayout(16, 16));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 12, 12));
        formPanel.setBackground(Theme.DARK_SURFACE);
        formPanel.setBorder(new CompoundBorder(
                new LineBorder(Theme.DARK_SURFACE_LIGHT, 1),
                new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel lbl1 = new JLabel("Student ID:");
        lbl1.setForeground(Theme.DARK_TEXT);
        lbl1.setFont(lbl1.getFont().deriveFont(Font.BOLD, 11f));
        formPanel.add(lbl1);
        txtStudentId = new JTextField();
        txtStudentId.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        UIEffects.styleTextField(txtStudentId);
        formPanel.add(txtStudentId);

        JLabel lbl2 = new JLabel("Course ID:");
        lbl2.setForeground(Theme.DARK_TEXT);
        lbl2.setFont(lbl2.getFont().deriveFont(Font.BOLD, 11f));
        formPanel.add(lbl2);
        txtCourseId = new JTextField();
        txtCourseId.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        UIEffects.styleTextField(txtCourseId);
        formPanel.add(txtCourseId);

        JLabel lbl3 = new JLabel("Semester:");
        lbl3.setForeground(Theme.DARK_TEXT);
        lbl3.setFont(lbl3.getFont().deriveFont(Font.BOLD, 11f));
        formPanel.add(lbl3);
        txtSemester = new JTextField();
        txtSemester.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        UIEffects.styleTextField(txtSemester);
        formPanel.add(txtSemester);

        JLabel lbl4 = new JLabel("School Year:");
        lbl4.setForeground(Theme.DARK_TEXT);
        lbl4.setFont(lbl4.getFont().deriveFont(Font.BOLD, 11f));
        formPanel.add(lbl4);
        txtSchoolYear = new JTextField();
        txtSchoolYear.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        UIEffects.styleTextField(txtSchoolYear);
        formPanel.add(txtSchoolYear);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton btnEnroll = new JButton("Enroll");
        UIEffects.styleButton(btnEnroll);
        btnEnroll.addActionListener(e -> enrollStudent());
        buttonPanel.add(btnEnroll);

        JButton btnDelete = new JButton("Delete");
        UIEffects.styleButton(btnDelete);
        btnDelete.addActionListener(e -> deleteEnrollment());
        buttonPanel.add(btnDelete);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Student ID", "Course ID", "Semester", "Year"}, 0
        );
        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Theme.DARK_SURFACE);
        table.setForeground(Theme.DARK_TEXT);
        table.setSelectionBackground(Theme.ACCENT_BLUE);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(Theme.DARK_SURFACE_LIGHT);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Theme.DARK_SURFACE_LIGHT);
        header.setForeground(Theme.DARK_TEXT);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 11f));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Theme.DARK_BG);
        scrollPane.getViewport().setBackground(Theme.DARK_SURFACE);
        scrollPane.setBorder(new LineBorder(Theme.DARK_SURFACE_LIGHT, 1));

        add(scrollPane, BorderLayout.CENTER);

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
