package src.ui;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import src.models.Student;
import src.services.StudentService;
import src.ui.components.RoundedButton;
import src.ui.components.RoundedTextField;
import src.ui.components.Theme;

public class StudentForm extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private int selectedId = -1;

    private RoundedTextField txtStudentNo;
    private RoundedTextField txtFirst;
    private RoundedTextField txtLast;
    private RoundedTextField txtEmail;
    private RoundedTextField txtProgram;

    private JLabel statusLabel;

    private final StudentService service = new StudentService();
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public StudentForm() {
        try {
            setLayout(new BorderLayout(12, 12));
            setBackground(Theme.DARK_BG);
            setBorder(new EmptyBorder(16, 16, 16, 16));

            add(buildForm(), BorderLayout.NORTH);
            add(buildTable(), BorderLayout.CENTER);

            load();
        } catch (Exception ex) {
            showError("Failed to initialize Student form", ex);
        }
    }

    private JPanel buildForm() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.DARK_SURFACE);
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        try {
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(10, 12, 10, 12);
            g.fill = GridBagConstraints.HORIZONTAL;

            // Header
            JLabel header = new JLabel("Student Information");
            header.setFont(new Font("Segoe UI", Font.BOLD, (int) 16f));
            header.setForeground(Theme.ACCENT_BLUE);
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 2;
            card.add(header, g);
            g.gridwidth = 1;
            g.gridy++;

            // Student Number
            JLabel lbl1 = new JLabel("Student Number");
            lbl1.setForeground(Theme.DARK_TEXT);
            lbl1.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
            g.gridx = 0;
            card.add(lbl1, g);
            g.gridx = 1;
            txtStudentNo = new RoundedTextField();
            txtStudentNo.setPreferredSize(new Dimension(200, 38));
            card.add(txtStudentNo, g);

            // First Name
            g.gridx = 0;
            g.gridy++;
            JLabel lbl2 = new JLabel("First Name");
            lbl2.setForeground(Theme.DARK_TEXT);
            lbl2.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
            card.add(lbl2, g);
            g.gridx = 1;
            txtFirst = new RoundedTextField();
            txtFirst.setPreferredSize(new Dimension(200, 38));
            card.add(txtFirst, g);

            // Last Name
            g.gridx = 0;
            g.gridy++;
            JLabel lbl3 = new JLabel("Last Name");
            lbl3.setForeground(Theme.DARK_TEXT);
            lbl3.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
            card.add(lbl3, g);
            g.gridx = 1;
            txtLast = new RoundedTextField();
            txtLast.setPreferredSize(new Dimension(200, 38));
            card.add(txtLast, g);

            // Email
            g.gridx = 0;
            g.gridy++;
            JLabel lbl4 = new JLabel("Email");
            lbl4.setForeground(Theme.DARK_TEXT);
            lbl4.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
            card.add(lbl4, g);
            g.gridx = 1;
            txtEmail = new RoundedTextField();
            txtEmail.setPreferredSize(new Dimension(200, 38));
            card.add(txtEmail, g);

            // Program
            g.gridx = 0;
            g.gridy++;
            JLabel lbl5 = new JLabel("Program");
            lbl5.setForeground(Theme.DARK_TEXT);
            lbl5.setFont(new Font("Segoe UI", Font.BOLD, (int) 11f));
            card.add(lbl5, g);
            g.gridx = 1;
            txtProgram = new RoundedTextField();
            txtProgram.setPreferredSize(new Dimension(200, 38));
            card.add(txtProgram, g);

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

            btnClear.addActionListener(e -> clear());
            btnDelete.addActionListener(e -> delete());
            btnUpdate.addActionListener(e -> update());
            btnSave.addActionListener(e -> save());

            actions.add(btnClear);
            actions.add(btnDelete);
            actions.add(btnUpdate);
            actions.add(btnSave);

            card.add(actions, g);

        } catch (Exception ex) {
            showError("Failed to build form layout", ex);
        }

        return card;
    }

    private JScrollPane buildTable() {
        try {
            model = new DefaultTableModel(
                    new Object[]{"ID", "Student No", "First", "Last", "Email", "Program"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(model);
            table.setRowHeight(32);
            table.setShowGrid(false);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setBackground(Theme.DARK_SURFACE);
            table.setForeground(Theme.DARK_TEXT);
            table.setSelectionBackground(Theme.ACCENT_BLUE);
            table.setSelectionForeground(Color.WHITE);
            table.setGridColor(new Color(50, 50, 50));
            table.setIntercellSpacing(new Dimension(0, 0));

            JTableHeader header = table.getTableHeader();
            header.setBackground(Theme.DARK_SURFACE_LIGHT);
            header.setForeground(Theme.DARK_TEXT);
            header.setFont(new Font("Segoe UI", Font.BOLD, (int) 12f));
            header.setPreferredSize(new Dimension(0, 36));

            table.getSelectionModel().addListSelectionListener(e -> {
                try {
                    int r = table.getSelectedRow();
                    if (r >= 0) {
                        selectedId = (int) model.getValueAt(r, 0);
                        txtStudentNo.setText(model.getValueAt(r, 1).toString());
                        txtFirst.setText(model.getValueAt(r, 2).toString());
                        txtLast.setText(model.getValueAt(r, 3).toString());
                        txtEmail.setText(model.getValueAt(r, 4).toString());
                        txtProgram.setText(model.getValueAt(r, 5).toString());
                        setStatus("Selected student #" + selectedId, Theme.ACCENT_BLUE);
                    }
                } catch (Exception ex) {
                    showError("Failed to load selected student", ex);
                }
            });

            JScrollPane sp = new JScrollPane(table);
            sp.setBackground(Theme.DARK_BG);
            sp.getViewport().setBackground(Theme.DARK_SURFACE);
            sp.setBorder(new LineBorder(Theme.DARK_SURFACE_LIGHT, 1));
            return sp;

        } catch (Exception ex) {
            showError("Failed to initialize table", ex);
            return new JScrollPane();
        }
    }

    private void load() {
        try {
            model.setRowCount(0);
            List<Student> students = service.getAllStudents();

            for (Student s : students) {
                model.addRow(new Object[]{
                    s.getId(),
                    s.getStudentNumber(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getEmail(),
                    s.getProgram()
                });
            }
        } catch (Exception ex) {
            showError("Failed to load students", ex);
        }
    }

    private void save() {
        if (!validateInputs()) {
            return;
        }

        try {
            Student s = new Student();
            s.setStudentNumber(txtStudentNo.getText());
            s.setFirstName(txtFirst.getText());
            s.setLastName(txtLast.getText());
            s.setEmail(txtEmail.getText());
            s.setProgram(txtProgram.getText());

            service.createStudent(s);
            load();
            clear();
            setStatus("✓ Student added successfully!", Theme.SUCCESS_GREEN);

        } catch (Exception ex) {
            showError("Failed to save student", ex);
        }
    }

    private boolean validateInputs() {
        if (txtStudentNo.getText().trim().isEmpty()) {
            setStatus("Student number is required", Theme.ERROR_RED);
            return false;
        }
        if (txtFirst.getText().trim().isEmpty() || txtLast.getText().trim().isEmpty()) {
            setStatus("First and last names are required", Theme.ERROR_RED);
            return false;
        }
        if (!txtEmail.getText().trim().isEmpty() && !EMAIL_PATTERN.matcher(txtEmail.getText()).matches()) {
            setStatus("Invalid email format", Theme.ERROR_RED);
            return false;
        }
        return true;
    }

    private void update() {
        if (selectedId == -1) {
            setStatus("Select a student first", Theme.ERROR_RED);
            return;
        }

        if (!validateInputs()) {
            return;
        }

        try {
            Student s = new Student();
            s.setId(selectedId);
            s.setStudentNumber(txtStudentNo.getText());
            s.setFirstName(txtFirst.getText());
            s.setLastName(txtLast.getText());
            s.setEmail(txtEmail.getText());
            s.setProgram(txtProgram.getText());

            service.updateStudent(s);
            load();
            clear();
            setStatus("✓ Student updated successfully!", Theme.SUCCESS_GREEN);

        } catch (Exception ex) {
            showError("Failed to update student", ex);
        }
    }

    private void delete() {
        if (selectedId == -1) {
            setStatus("Select a student first", Theme.ERROR_RED);
            return;
        }

        try {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?\nThis cannot be undone.",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteStudent(selectedId);
                load();
                clear();
                setStatus("✓ Student deleted", Theme.SUCCESS_GREEN);
            }
        } catch (Exception ex) {
            showError("Failed to delete student", ex);
        }
    }

    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private void clear() {
        try {
            txtStudentNo.setText("");
            txtFirst.setText("");
            txtLast.setText("");
            txtEmail.setText("");
            txtProgram.setText("");
            selectedId = -1;
            statusLabel.setText("");
            table.clearSelection();
        } catch (Exception ex) {
            showError("Failed to clear form", ex);
        }
    }

    private void showError(String message, Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                message + "\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
        ex.printStackTrace();
    }
}
