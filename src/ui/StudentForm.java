package src.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import src.models.Student;
import src.services.StudentService;
import src.ui.components.Theme;
import src.ui.components.UIEffects;

public class StudentForm extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private int selectedId = -1;

    private JTextField txtStudentNo;
    private JTextField txtFirst;
    private JTextField txtLast;
    private JTextField txtEmail;
    private JTextField txtProgram;

    private final StudentService service = new StudentService();

    public StudentForm() {
        try {
            setLayout(new BorderLayout(20, 20));
            setBackground(Theme.DARK_BG);
            setBorder(new EmptyBorder(20, 20, 20, 20));

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
        card.setBorder(new CompoundBorder(
                new LineBorder(Theme.DARK_SURFACE_LIGHT, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        try {
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(8, 8, 8, 8);
            g.fill = GridBagConstraints.HORIZONTAL;

            g.gridx = 0;
            g.gridy = 0;
            JLabel lbl1 = new JLabel("Student Number");
            lbl1.setForeground(Theme.DARK_TEXT);
            lbl1.setFont(lbl1.getFont().deriveFont(Font.BOLD, 11f));
            card.add(lbl1, g);
            g.gridx = 1;
            txtStudentNo = new JTextField();
            txtStudentNo.setPreferredSize(new Dimension(150, 32));
            UIEffects.styleTextField(txtStudentNo);
            card.add(txtStudentNo, g);

            g.gridx = 0;
            g.gridy++;
            JLabel lbl2 = new JLabel("First Name");
            lbl2.setForeground(Theme.DARK_TEXT);
            lbl2.setFont(lbl2.getFont().deriveFont(Font.BOLD, 11f));
            card.add(lbl2, g);
            g.gridx = 1;
            txtFirst = new JTextField();
            txtFirst.setPreferredSize(new Dimension(150, 32));
            UIEffects.styleTextField(txtFirst);
            card.add(txtFirst, g);

            g.gridx = 0;
            g.gridy++;
            JLabel lbl3 = new JLabel("Last Name");
            lbl3.setForeground(Theme.DARK_TEXT);
            lbl3.setFont(lbl3.getFont().deriveFont(Font.BOLD, 11f));
            card.add(lbl3, g);
            g.gridx = 1;
            txtLast = new JTextField();
            txtLast.setPreferredSize(new Dimension(150, 32));
            UIEffects.styleTextField(txtLast);
            card.add(txtLast, g);

            g.gridx = 0;
            g.gridy++;
            JLabel lbl4 = new JLabel("Email");
            lbl4.setForeground(Theme.DARK_TEXT);
            lbl4.setFont(lbl4.getFont().deriveFont(Font.BOLD, 11f));
            card.add(lbl4, g);
            g.gridx = 1;
            txtEmail = new JTextField();
            txtEmail.setPreferredSize(new Dimension(150, 32));
            UIEffects.styleTextField(txtEmail);
            card.add(txtEmail, g);

            g.gridx = 0;
            g.gridy++;
            JLabel lbl5 = new JLabel("Program");
            lbl5.setForeground(Theme.DARK_TEXT);
            lbl5.setFont(lbl5.getFont().deriveFont(Font.BOLD, 11f));
            card.add(lbl5, g);
            g.gridx = 1;
            txtProgram = new JTextField();
            txtProgram.setPreferredSize(new Dimension(150, 32));
            UIEffects.styleTextField(txtProgram);
            card.add(txtProgram, g);

            g.gridx = 0;
            g.gridy++;
            g.gridwidth = 2;
            g.anchor = GridBagConstraints.EAST;

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            actions.setOpaque(false);
            JButton save = new JButton("Save");
            JButton update = new JButton("Update");
            JButton delete = new JButton("Delete");

            UIEffects.styleButton(save);
            UIEffects.styleButton(update);
            UIEffects.styleButton(delete);

            save.addActionListener(e -> save());
            update.addActionListener(e -> update());
            delete.addActionListener(e -> delete());

            actions.add(delete);
            actions.add(update);
            actions.add(save);

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
            );

            table = new JTable(model);
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

            JOptionPane.showMessageDialog(this, "Student saved successfully");
        } catch (Exception ex) {
            showError("Failed to save student", ex);
        }
    }

    private void update() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first");
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

            JOptionPane.showMessageDialog(this, "Student updated");
        } catch (Exception ex) {
            showError("Failed to update student", ex);
        }
    }

    private void delete() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student first");
            return;
        }

        try {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this student?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteStudent(selectedId);
                load();
                clear();
                JOptionPane.showMessageDialog(this, "Student deleted");
            }
        } catch (Exception ex) {
            showError("Failed to delete student", ex);
        }
    }

    private void clear() {
        try {
            txtStudentNo.setText("");
            txtFirst.setText("");
            txtLast.setText("");
            txtEmail.setText("");
            txtProgram.setText("");
            selectedId = -1;
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
