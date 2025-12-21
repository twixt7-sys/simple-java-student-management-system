package src.ui;

import src.models.Enrollment;
import src.models.Student;
import src.exceptions.InvalidInputException;
import src.models.Course;
import src.services.EnrollmentService;
import src.ui.components.Theme;
import src.ui.components.RoundedButton;
import src.ui.components.RoundedTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class EnrollmentForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedEnrollmentId = -1;

    private RoundedTextField txtStudentId;
    private RoundedTextField txtCourseId;
    private RoundedTextField txtSemester;
    private RoundedTextField txtSchoolYear;
    private JLabel statusLabel;

    private final EnrollmentService enrollmentService;

    public EnrollmentForm() {
        enrollmentService = new EnrollmentService();
        setBackground(Theme.DARK_BG);
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(16, 16, 16, 16));
        initialize();
        loadEnrollments();
    }

    private void initialize() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.DARK_SURFACE);
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 12, 10, 12);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel header = new JLabel("Enrollment Management");
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(Theme.ACCENT_BLUE);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        card.add(header, g);

        g.gridwidth = 1;
        g.gridy++;

        JLabel lbl1 = new JLabel("Student ID");
        lbl1.setForeground(Theme.DARK_TEXT);
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 11));
        g.gridx = 0;
        card.add(lbl1, g);

        g.gridx = 1;
        txtStudentId = new RoundedTextField();
        txtStudentId.setPreferredSize(new Dimension(200, 38));
        card.add(txtStudentId, g);

        g.gridx = 0;
        g.gridy++;
        JLabel lbl2 = new JLabel("Course ID");
        lbl2.setForeground(Theme.DARK_TEXT);
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        card.add(lbl2, g);

        g.gridx = 1;
        txtCourseId = new RoundedTextField();
        txtCourseId.setPreferredSize(new Dimension(200, 38));
        card.add(txtCourseId, g);

        g.gridx = 0;
        g.gridy++;
        JLabel lbl3 = new JLabel("Semester");
        lbl3.setForeground(Theme.DARK_TEXT);
        lbl3.setFont(new Font("Segoe UI", Font.BOLD, 11));
        card.add(lbl3, g);

        g.gridx = 1;
        txtSemester = new RoundedTextField();
        txtSemester.setPreferredSize(new Dimension(200, 38));
        card.add(txtSemester, g);

        g.gridx = 0;
        g.gridy++;
        JLabel lbl4 = new JLabel("School Year");
        lbl4.setForeground(Theme.DARK_TEXT);
        lbl4.setFont(new Font("Segoe UI", Font.BOLD, 11));
        card.add(lbl4, g);

        g.gridx = 1;
        txtSchoolYear = new RoundedTextField();
        txtSchoolYear.setPreferredSize(new Dimension(200, 38));
        card.add(txtSchoolYear, g);

        g.gridx = 0;
        g.gridy++;
        g.gridwidth = 2;
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        card.add(statusLabel, g);

        g.gridy++;
        g.anchor = GridBagConstraints.EAST;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        RoundedButton btnClear = new RoundedButton(
                "Clear",
                Theme.DARK_SURFACE_LIGHT,
                Theme.DARK_SURFACE,
                new Color(100, 100, 100)
        );

        RoundedButton btnDelete = new RoundedButton(
                "Remove",
                Theme.ERROR_RED,
                new Color(220, 100, 100),
                new Color(180, 50, 50)
        );

        RoundedButton btnEnroll = new RoundedButton(
                "Enroll",
                Theme.SUCCESS_GREEN,
                new Color(80, 220, 120),
                new Color(30, 150, 60)
        );

        btnClear.setPreferredSize(new Dimension(100, 36));
        btnDelete.setPreferredSize(new Dimension(100, 36));
        btnEnroll.setPreferredSize(new Dimension(100, 36));

        btnClear.addActionListener(e -> clearFields());
        btnDelete.addActionListener(e -> deleteEnrollment());
        btnEnroll.addActionListener(e -> enrollStudent());

        actions.add(btnClear);
        actions.add(btnDelete);
        actions.add(btnEnroll);

        card.add(actions, g);
        add(card, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Student ID", "Course ID", "Semester", "School Year"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Theme.DARK_SURFACE);
        table.setForeground(Theme.DARK_TEXT);
        table.setSelectionBackground(Theme.ACCENT_BLUE);
        table.setSelectionForeground(Color.WHITE);

        JTableHeader th = table.getTableHeader();
        th.setBackground(Theme.DARK_SURFACE_LIGHT);
        th.setForeground(Theme.DARK_TEXT);
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setPreferredSize(new Dimension(0, 36));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(Theme.DARK_SURFACE_LIGHT));
        add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                selectedEnrollmentId = (int) tableModel.getValueAt(row, 0);
                txtStudentId.setText(tableModel.getValueAt(row, 1).toString());
                txtCourseId.setText(tableModel.getValueAt(row, 2).toString());
                txtSemester.setText(tableModel.getValueAt(row, 3).toString());
                txtSchoolYear.setText(tableModel.getValueAt(row, 4).toString());
                setStatus("Selected enrollment #" + selectedEnrollmentId, Theme.ACCENT_BLUE);
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
                e.getSemester() != null ? e.getSemester() : "-",
                e.getSchoolYear() != null ? e.getSchoolYear() : "-"
            });
        }
    }

    private void enrollStudent() {
        if (!validateInputs()) {
            return;
        }

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

            loadEnrollments();
            clearFields();
            setStatus("Enrollment successful", Theme.SUCCESS_GREEN);

        } catch (InvalidInputException ex) {
            setStatus(ex.getMessage(), Theme.ERROR_RED);
        } catch (Exception ex) {
            setStatus("Enrollment failed: " + ex.getMessage(), Theme.ERROR_RED);
        }
    }

    private void deleteEnrollment() {
        if (selectedEnrollmentId == -1) {
            setStatus("Select an enrollment first", Theme.ERROR_RED);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Remove this enrollment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            enrollmentService.removeEnrollment(selectedEnrollmentId);
            loadEnrollments();
            clearFields();
            setStatus("Enrollment removed", Theme.SUCCESS_GREEN);
        }
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtCourseId.setText("");
        txtSemester.setText("");
        txtSchoolYear.setText("");
        selectedEnrollmentId = -1;
        statusLabel.setText("");
        table.clearSelection();
    }

    private boolean validateInputs() {
        if (txtStudentId.getText().isBlank()) {
            setStatus("Student ID is required", Theme.ERROR_RED);
            return false;
        }
        if (txtCourseId.getText().isBlank()) {
            setStatus("Course ID is required", Theme.ERROR_RED);
            return false;
        }
        if (txtSemester.getText().isBlank()) {
            setStatus("Semester is required", Theme.ERROR_RED);
            return false;
        }
        if (txtSchoolYear.getText().isBlank()) {
            setStatus("School year is required", Theme.ERROR_RED);
            return false;
        }
        return true;
    }

    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
}
