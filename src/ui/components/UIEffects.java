package src.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class UIEffects {

    public static void applyHover(JButton button) {
        Color normal = Theme.ACCENT_BLUE;
        Color hover = Theme.ACCENT_BLUE_HOVER;
        Color pressed = Theme.ACCENT_BLUE_DARK;

        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(normal);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hover);
            }
        });
    }

    public static void styleButton(JButton button) {
        button.setBackground(Theme.ACCENT_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        applyHover(button);
    }

    public static void styleTextField(JTextField field) {
        field.setBackground(Theme.DARK_SURFACE);
        field.setForeground(Theme.DARK_TEXT);
        field.setCaretColor(Theme.DARK_TEXT);
        field.setBorder(new LineBorder(Theme.DARK_SURFACE_LIGHT, 1));

        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                field.setBorder(new LineBorder(Theme.ACCENT_BLUE, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                field.setBorder(new LineBorder(Theme.DARK_SURFACE_LIGHT, 1));
            }
        });

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(new LineBorder(Theme.ACCENT_BLUE, 2));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(new LineBorder(Theme.DARK_SURFACE_LIGHT, 1));
            }
        });
    }
}
