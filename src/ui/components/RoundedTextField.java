package src.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {

    private static final int ARC = 10;
    private static final int PADDING = 10;
    private Color borderColor = Theme.DARK_SURFACE_LIGHT;
    private Color focusColor = Theme.ACCENT_BLUE;

    public RoundedTextField() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        setForeground(Theme.DARK_TEXT);
        setCaretColor(Theme.DARK_TEXT);
        setFont(getFont().deriveFont(13f));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                borderColor = Theme.ACCENT_BLUE_HOVER;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isFocusOwner()) {
                    borderColor = Theme.DARK_SURFACE_LIGHT;
                    repaint();
                }
            }
        });

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                borderColor = focusColor;
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                borderColor = Theme.DARK_SURFACE_LIGHT;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Theme.DARK_SURFACE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC);

        super.paintComponent(g);
    }
}
