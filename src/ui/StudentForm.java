package src.ui;

import src.models.Student;
import src.services.StudentService;
import src.exceptions.InvalidInputException;

import javax.swing.*;
import java.awt.*;

public class StudentForm extends JPanel {

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
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Student Number:"));
        txtStudentNumber = new JTextField();
        add(txtStudentNumber);

        add(new JLabel("First Name:"));
        txtFirstName = new JTextField();
        add(txtFirstName);

        add(new JLabel("Last Name:"));
        txtLastName = new JTextField();
        add(txtLastName);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Program:"));
        txtProgram = new JTextField();
        add(txtProgram);

        JButton btnSave = new JButton("Save Student");
        btnSave.addActionListener(e -> saveStudent());
        add(btnSave);
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

            clearFields();

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtStudentNumber.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtProgram.setText("");
    }
}
