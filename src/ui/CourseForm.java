package src.ui;

import src.models.Course;
import src.services.CourseService;
import src.exceptions.InvalidInputException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Swing form for adding and managing course records.
public class CourseForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedCourseId = -1;

    private JTextField txtCourseCode;
    private JTextField txtCourseName;
    private JTextField txtUnits;

    private final CourseService courseService;

    public CourseForm() {
        courseService = new CourseService();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));

        formPanel.add(new JLabel("Course Code:"));
        txtCourseCode = new JTextField();
        formPanel.add(txtCourseCode);

        formPanel.add(new JLabel("Course Name:"));
        txtCourseName = new JTextField();
        formPanel.add(txtCourseName);

        formPanel.add(new JLabel("Units:"));
        txtUnits = new JTextField();
        formPanel.add(txtUnits);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveCourse());
        buttonPanel.add(btnSave);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateCourse());
        buttonPanel.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteCourse());
        buttonPanel.add(btnDelete);

        topPanel.add(formPanel);
        topPanel.add(buttonPanel);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Code", "Name", "Units"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    selectedCourseId = (int) tableModel.getValueAt(row, 0);
                    txtCourseCode.setText(tableModel.getValueAt(row, 1).toString());
                    txtCourseName.setText(tableModel.getValueAt(row, 2).toString());
                    txtUnits.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        loadCourses();
    }

    private void loadCourses() {
        tableModel.setRowCount(0);
        List<Course> courses = courseService.getAllCourses();

        for (Course c : courses) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getCourseCode(),
                c.getCourseName(),
                c.getUnits()
            });
        }
    }

    private void saveCourse() {
        try {
            Course course = new Course();
            course.setCourseCode(txtCourseCode.getText());
            course.setCourseName(txtCourseName.getText());
            course.setUnits(Integer.parseInt(txtUnits.getText()));

            courseService.createCourse(course);
            loadCourses();
            clearFields();

            JOptionPane.showMessageDialog(this, "Course saved successfully");

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Units must be a number");
        }
    }

    private void updateCourse() {
        if (selectedCourseId == -1) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }

        try {
            Course course = new Course();
            course.setId(selectedCourseId);
            course.setCourseCode(txtCourseCode.getText());
            course.setCourseName(txtCourseName.getText());
            course.setUnits(Integer.parseInt(txtUnits.getText()));

            courseService.updateCourse(course);
            loadCourses();
            clearFields();

            JOptionPane.showMessageDialog(this, "Course updated");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteCourse() {
        if (selectedCourseId == -1) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this course?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            courseService.deleteCourse(selectedCourseId);
            loadCourses();
            clearFields();
            JOptionPane.showMessageDialog(this, "Course deleted");
        }
    }

    private void clearFields() {
        txtCourseCode.setText("");
        txtCourseName.setText("");
        txtUnits.setText("");
        selectedCourseId = -1;
    }
}
