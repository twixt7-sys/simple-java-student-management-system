
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import src.ui.MainFrame;
import src.utils.DemoDataInitializer;

public class Main {

    public static void main(String[] args) {
        applyGlobalStyle();

        // Initialize demo data on startup
        DemoDataInitializer.initializeDemoData();

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    private static void applyGlobalStyle() {
        Font base = new Font("Segoe UI", Font.PLAIN, 13);
        Font bold = base.deriveFont(Font.BOLD);

        UIManager.put("Label.font", base);
        UIManager.put("Button.font", base);
        UIManager.put("TextField.font", base);
        UIManager.put("PasswordField.font", base);
        UIManager.put("Table.font", base);
        UIManager.put("TableHeader.font", bold);

        UIManager.put("Panel.background", new Color(245, 247, 250));
        UIManager.put("ScrollPane.background", new Color(245, 247, 250));
        UIManager.put("Viewport.background", new Color(245, 247, 250));

        UIManager.put("Button.background", new Color(235, 238, 242));
        UIManager.put("Button.focusPainted", false);
        UIManager.put("Button.border", javax.swing.BorderFactory.createEmptyBorder(8, 14, 8, 14));

        UIManager.put("TextField.border",
                javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(new Color(220, 223, 228)),
                        javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8)
                )
        );

        UIManager.put("PasswordField.border", UIManager.get("TextField.border"));

        UIManager.put("Table.gridColor", new Color(230, 233, 238));
        UIManager.put("Table.rowHeight", 26);
        UIManager.put("Table.showVerticalLines", false);
    }
}
