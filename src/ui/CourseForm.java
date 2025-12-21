package src.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import src.exceptions.InvalidInputException;
import src.models.Course;
import src.services.CourseService;
import src.ui.components.FormCard;
import src.ui.components.Theme;
import src.ui.components.UIEffects;

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
        setLayout(new BorderLayout(16, 16));
        setBackground(Theme.DARK_BG);
        initialize();
    }

    private void initialize() {
        FormCard card = new FormCard();
        card.setHeader("Courses");

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setOpaque(false);
        form.setBorder(new javax.swing.border.EmptyBorder(6, 6, 6, 6));

        JLabel lbl1 = new JLabel("Course Code");
        lbl1.setForeground(Theme.DARK_TEXT);
        lbl1.setFont(lbl1.getFont().deriveFont(Font.BOLD, 11f));
        form.add(lbl1);
        txtCourseCode = new JTextField();
        txtCourseCode.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        UIEffects.styleTextField(txtCourseCode);
        form.add(txtCourseCode);

        JLabel lbl2 = new JLabel("Course Name");
        lbl2.setForeground(Theme.DARK_TEXT);
        lbl2.setFont(lbl2.getFont().deriveFont(Font.BOLD, 11f));
        form.add(lbl2);
        txtCourseName = new JTextField();
        txtCourseName.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        UIEffects.styleTextField(txtCourseName);
        form.add(txtCourseName);

        JLabel lbl3 = new JLabel("Units");
        lbl3.setForeground(Theme.DARK_TEXT);
        lbl3.setFont(lbl3.getFont().deriveFont(Font.BOLD, 11f));
        form.add(lbl3);
        txtUnits = new JTextField();
        txtUnits.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        UIEffects.styleTextField(txtUnits);
        form.add(txtUnits);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnSave = new JButton("Save");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        UIEffects.styleButton(btnSave);
        UIEffects.styleButton(btnUpdate);
        UIEffects.styleButton(btnDelete);

        btnSave.addActionListener(e -> saveCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());

        actions.add(btnSave);
        actions.add(btnUpdate);
        actions.add(btnDelete);

        card.add(form, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);

        add(card, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Code", "Name", "Units"}, 0
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
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                selectedCourseId = (int) tableModel.getValueAt(row, 0);
                txtCourseCode.setText(tableModel.getValueAt(row, 1).toString());
                txtCourseName.setText(tableModel.getValueAt(row, 2).toString());
                txtUnits.setText(tableModel.getValueAt(row, 3).toString());
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

            JOptionPane.showMessageDialog(this, "Course saved");
        } catch (InvalidInputException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateCourse() {
        if (selectedCourseId == -1) {
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteCourse() {
        if (selectedCourseId == -1) {
            return;
        }

        courseService.deleteCourse(selectedCourseId);
        loadCourses();
        clearFields();
    }

    private void clearFields() {
        txtCourseCode.setText("");
        txtCourseName.setText("");
        txtUnits.setText("");
        selectedCourseId = -1;
    }
}
