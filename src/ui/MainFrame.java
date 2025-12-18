package src.ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("Student Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new StudentForm());
    }
}
