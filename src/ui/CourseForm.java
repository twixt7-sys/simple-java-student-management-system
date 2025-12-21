package src.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import src.exceptions.InvalidInputException;
import src.models.Course;
import src.services.CourseService;
import src.ui.components.RoundedButton;
import src.ui.components.RoundedTextField;
import src.ui.components.Theme;

public class CourseForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedCourseId = -1;

    private RoundedTextField txtCourseCode;
    private RoundedTextField txtCourseName;
    private RoundedTextField txtUnits;
    private JLabel statusLabel;

    private final CourseService courseService;

    public CourseForm() {
        courseService = new CourseService();
        setLayout(new BorderLayout(12, 12));
        setBackground(Theme.DARK_BG);
        setBorder(new EmptyBorder(16, 16, 16, 16));
        initialize();
    }

    private void initialize() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.DARK_SURFACE);
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 12, 10, 12);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Header
        JLabel header = new JLabel("Course Management");
        header.setFont(new Font("Segoe UI", Font.BOLD, (int) 16f));
        header.setForeground(Theme.ACCENT_BLUE);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        card.add(header, g);
        g.gridwidth = 1;
        g.gridy++;

        // Course Code
        JLabel lbl1 = new JLabel("Course Code");
        lbl1.setForeground(Theme.DARK_TEXT);
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
        g.gridx = 0;
        card.add(lbl1, g);
        g.gridx = 1;
        txtCourseCode = new RoundedTextField();
        txtCourseCode.setPreferredSize(new Dimension(200, 38));
        card.add(txtCourseCode, g);

        // Course Name
        g.gridx = 0;
        g.gridy++;
        JLabel lbl2 = new JLabel("Course Name");
        lbl2.setForeground(Theme.DARK_TEXT);
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
        card.add(lbl2, g);
        g.gridx = 1;
        txtCourseName = new RoundedTextField();
        txtCourseName.setPreferredSize(new Dimension(200, 38));
        card.add(txtCourseName, g);

        // Units
        g.gridx = 0;
        g.gridy++;
        JLabel lbl3 = new JLabel("Credits");
        lbl3.setForeground(Theme.DARK_TEXT);
        lbl3.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
        card.add(lbl3, g);
        g.gridx = 1;
        txtUnits = new RoundedTextField();
        txtUnits.setPreferredSize(new Dimension(200, 38));
        card.add(txtUnits, g);

        // Status label
        g.gridx = 0;
        g.gridy++;
        g.gridwidth = 2;
        statusLabel = new JLabel("");
        statusLabel.setForeground(Theme.SUCCESS_GREEN);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, (int) 10f));
        card.add(statusLabel, g);
        g.gridwidth = 1;
        g.gridy++;

        // Buttons
        g.gridx = 0;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.EAST;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        RoundedButton btnClear = new RoundedButton("Clear", Theme.DARK_SURFACE_LIGHT,
                Theme.DARK_SURFACE, new Color(100, 100, 100));
        RoundedButton btnDelete = new RoundedButton("Delete", Theme.ERROR_RED,
                new Color(220, 100, 100), new Color(180, 50, 50));
        RoundedButton btnUpdate = new RoundedButton("Update", Theme.ACCENT_BLUE_HOVER,
                Theme.ACCENT_BLUE, Theme.ACCENT_BLUE_DARK);
        RoundedButton btnSave = new RoundedButton("Add New", Theme.SUCCESS_GREEN,
                new Color(80, 220, 120), new Color(30, 150, 60));

        btnClear.setPreferredSize(new Dimension(100, 36));
        btnDelete.setPreferredSize(new Dimension(100, 36));
        btnUpdate.setPreferredSize(new Dimension(100, 36));
        btnSave.setPreferredSize(new Dimension(100, 36));

        btnSave.addActionListener(e -> saveCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnClear.addActionListener(e -> clearFields());

        actions.add(btnClear);
        actions.add(btnDelete);
        actions.add(btnUpdate);
        actions.add(btnSave);

        card.add(actions, g);

        add(card, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Code", "Name", "Credits"}, 0
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
        table.setGridColor(new Color(50, 50, 50));
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header2 = table.getTableHeader();
        header2.setBackground(Theme.DARK_SURFACE_LIGHT);
        header2.setForeground(Theme.DARK_TEXT);
        header2.setFont(new Font("Segoe UI", Font.BOLD, (int) 12f));
        header2.setPreferredSize(new Dimension(0, 36));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Theme.DARK_BG);
        scrollPane.getViewport().setBackground(Theme.DARK_SURFACE);
        scrollPane.setBorder(new LineBorder(Theme.DARK_SURFACE_LIGHT, 1));

        add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                selectedCourseId = (int) tableModel.getValueAt(row, 0);
                txtCourseCode.setText(tableModel.getValueAt(row, 1).toString());
                txtCourseName.setText(tableModel.getValueAt(row, 2).toString());
                txtUnits.setText(tableModel.getValueAt(row, 3).toString());
                setStatus("Selected course #" + selectedCourseId, Theme.ACCENT_BLUE);
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
        if (!validateInputs()) {
            return;
        }

        try {
            Course course = new Course();
            course.setCourseCode(txtCourseCode.getText());
            course.setCourseName(txtCourseName.getText());
            course.setUnits(Integer.parseInt(txtUnits.getText()));

            courseService.createCourse(course);
            loadCourses();
            clearFields();
            setStatus("✓ Course added successfully!", Theme.SUCCESS_GREEN);

        } catch (InvalidInputException ex) {
            setStatus(ex.getMessage(), Theme.ERROR_RED);
        } catch (NumberFormatException ex) {
            setStatus("Credits must be a number", Theme.ERROR_RED);
        }
    }

    private void updateCourse() {
        if (selectedCourseId == -1) {
            setStatus("Select a course first", Theme.ERROR_RED);
            return;
        }

        if (!validateInputs()) {
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
            setStatus("✓ Course updated!", Theme.SUCCESS_GREEN);
        } catch (Exception ex) {
            setStatus(ex.getMessage(), Theme.ERROR_RED);
        }
    }

    private void deleteCourse() {
        if (selectedCourseId == -1) {
            setStatus("Select a course first", Theme.ERROR_RED);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete this course?\nThis cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                courseService.deleteCourse(selectedCourseId);
                loadCourses();
                clearFields();
                setStatus("✓ Course deleted", Theme.SUCCESS_GREEN);
            } catch (Exception ex) {
                setStatus("Failed to delete: " + ex.getMessage(), Theme.ERROR_RED);
            }
        }
    }

    private void clearFields() {
        txtCourseCode.setText("");
        txtCourseName.setText("");
        txtUnits.setText("");
        selectedCourseId = -1;
        statusLabel.setText("");
        table.clearSelection();
    }

    private boolean validateInputs() {
        if (txtCourseCode.getText().trim().isEmpty()) {
            setStatus("Course code is required", Theme.ERROR_RED);
            return false;
        }
        if (txtCourseName.getText().trim().isEmpty()) {
            setStatus("Course name is required", Theme.ERROR_RED);
            return false;
        }
        if (txtUnits.getText().trim().isEmpty()) {
            setStatus("Credits are required", Theme.ERROR_RED);
            return false;
        }
        return true;
    }

    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
}
