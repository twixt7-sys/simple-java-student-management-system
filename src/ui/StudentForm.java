package src.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.exceptions.InvalidInputException;
import src.models.Student;
import src.services.StudentService;

// Swing form for adding and managing student records.
public class StudentForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedStudentId = -1;

    private JTextField txtStudentNumber;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtProgram;

    private final StudentService studentService;

    public StudentForm() {
        studentService = new StudentService();
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout(10, 10));

        // container for form + buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // form grid
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        JLabel lblStudentNumber = new JLabel("Student Number:");
        lblStudentNumber.setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(lblStudentNumber);
        txtStudentNumber = new JTextField();
        formPanel.add(txtStudentNumber);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(lblFirstName);
        txtFirstName = new JTextField();
        formPanel.add(txtFirstName);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(lblLastName);
        txtLastName = new JTextField();
        formPanel.add(txtLastName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(lblEmail);
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        JLabel lblProgram = new JLabel("Program:");
        lblProgram.setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(lblProgram);
        txtProgram = new JTextField();
        formPanel.add(txtProgram);

        // button grid
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveStudent());
        buttonPanel.add(btnSave);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateStudent());
        buttonPanel.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteStudent());
        buttonPanel.add(btnDelete);

        topPanel.add(formPanel);
        topPanel.add(buttonPanel);

        add(topPanel, BorderLayout.NORTH);

        // table
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Student No", "First Name", "Last Name", "Email", "Program"}, 0
        );
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    selectedStudentId = (int) tableModel.getValueAt(row, 0);
                    txtStudentNumber.setText(tableModel.getValueAt(row, 1).toString());
                    txtFirstName.setText(tableModel.getValueAt(row, 2).toString());
                    txtLastName.setText(tableModel.getValueAt(row, 3).toString());
                    txtEmail.setText(tableModel.getValueAt(row, 4).toString());
                    txtProgram.setText(tableModel.getValueAt(row, 5).toString());
                }
            }
        });

        loadStudents();
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();

        for (Student s : students) {
            tableModel.addRow(new Object[]{
                s.getId(),
                s.getStudentNumber(),
                s.getFirstName(),
                s.getLastName(),
                s.getEmail(),
                s.getProgram()
            });
        }
    }

    private void saveStudent() {
        try {
            Student student = new Student();
            student.setStudentNumber(txtStudentNumber.getText());
            student.setFirstName(txtFirstName.getText());
            student.setLastName(txtLastName.getText());
            student.setEmail(txtEmail.getText());
            student.setProgram(txtProgram.getText());

            studentService.createStudent(student);

            JOptionPane.showMessageDialog(this,
                    "Student saved successfully");

            loadStudents();

            clearFields();

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        if (selectedStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first");
            return;
        }

        try {
            Student student = new Student();
            student.setId(selectedStudentId);
            student.setStudentNumber(txtStudentNumber.getText());
            student.setFirstName(txtFirstName.getText());
            student.setLastName(txtLastName.getText());
            student.setEmail(txtEmail.getText());
            student.setProgram(txtProgram.getText());

            studentService.updateStudent(student);
            loadStudents();
            clearFields();

            JOptionPane.showMessageDialog(this, "Student updated");
            loadStudents();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteStudent() {
        if (selectedStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            studentService.deleteStudent(selectedStudentId);
            loadStudents();
            clearFields();
            JOptionPane.showMessageDialog(this, "Student deleted");
        }

        loadStudents();
    }

    private void clearFields() {
        txtStudentNumber.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtProgram.setText("");
    }
}
