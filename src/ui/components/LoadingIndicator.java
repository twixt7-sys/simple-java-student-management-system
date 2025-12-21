package src.ui.components;

import javax.swing.*;
import java.awt.*;

public class LoadingIndicator extends JPanel {

    private int rotation = 0;
    private boolean loading = false;
    private Timer timer;

    public LoadingIndicator() {
        setOpaque(false);
        setPreferredSize(new Dimension(40, 40));
    }

    public void start() {
        if (loading) return;
        loading = true;
        timer = new Timer(30, e -> {
            rotation = (rotation + 6) % 360;
            repaint();
        });
        timer.start();
    }

    public void stop() {
        loading = false;
        if (timer != null) {
            timer.stop();
        }
        rotation = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!loading) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int size = Math.min(w, h) - 4;
        int x = (w - size) / 2;
        int y = (h - size) / 2;

        g2.translate(x + size / 2, y + size / 2);
        g2.rotate(Math.toRadians(rotation));

        g2.setColor(Theme.ACCENT_BLUE);
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int arcSize = size;
        g2.drawArc(-arcSize / 2, -arcSize / 2, arcSize, arcSize, 0, 270);
    }
}
