package src.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {

    private static final int ARC = 12;

    private Color defaultColor;
    private Color hoverColor;
    private Color pressColor;

    private Color currentColor;

    public RoundedButton(String text, Color defaultColor, Color hoverColor, Color pressColor) {
        super(text);

        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressColor = pressColor;
        this.currentColor = defaultColor;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(Font.BOLD, 13f));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentColor = hoverColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentColor = defaultColor;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currentColor = pressColor;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentColor = hoverColor;
                repaint();
            }
        });
    }

    // Allow runtime update of the button color scheme
    public void setColors(Color defaultColor, Color hoverColor, Color pressColor) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressColor = pressColor;
        this.currentColor = this.defaultColor;
        repaint();
    }

    // Reset current displayed color to the default (useful after changing styles)
    public void setDefaultAsCurrent() {
        this.currentColor = this.defaultColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(currentColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(
                0, 0, getWidth(), getHeight(), ARC, ARC
        );
        return shape.contains(x, y);
    }
}
