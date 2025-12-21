package src.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShadowPanel extends JPanel {

    private static final int SHADOW_WIDTH = 6;
    private static final int CORNER_RADIUS = 15;

    public ShadowPanel() {
        setOpaque(false);
        setBorder(new EmptyBorder(SHADOW_WIDTH, SHADOW_WIDTH, SHADOW_WIDTH, SHADOW_WIDTH));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int x = SHADOW_WIDTH;
        int y = SHADOW_WIDTH;
        int width = w - 2 * SHADOW_WIDTH;
        int height = h - 2 * SHADOW_WIDTH;

        // Draw shadow
        for (int i = SHADOW_WIDTH; i > 0; i--) {
            g2.setColor(new Color(0, 0, 0, (int) (20 * (1 - (float) i / SHADOW_WIDTH))));
            g2.fillRoundRect(x + i / 2, y + i / 2, width - i / 2, height - i / 2, CORNER_RADIUS, CORNER_RADIUS);
        }

        // Draw background
        g2.setColor(Theme.DARK_SURFACE);
        g2.fillRoundRect(x, y, width, height, CORNER_RADIUS, CORNER_RADIUS);

        super.paintComponent(g);
    }
}
