package src.ui.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FormCard extends JPanel {

    public FormCard() {
        setLayout(new BorderLayout(12, 12));
        setBackground(Theme.DARK_SURFACE);
        setBorder(new CompoundBorder(
                new LineBorder(Theme.DARK_SURFACE_LIGHT, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
    }

    public void setHeader(String title) {
        JLabel lbl = new JLabel(title);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 18f));
        lbl.setForeground(Theme.DARK_TEXT);
        add(lbl, BorderLayout.NORTH);
    }
}
