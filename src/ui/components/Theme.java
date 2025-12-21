package src.ui.components;

import java.awt.Color;
import javax.swing.UIManager;

public class Theme {

    // Dark theme colors
    public static final Color DARK_BG = new Color(16, 18, 24);
    public static final Color DARK_SURFACE = new Color(28, 31, 40);
    public static final Color DARK_SURFACE_LIGHT = new Color(41, 45, 56);
    public static final Color DARK_TEXT = new Color(230, 230, 230);
    public static final Color DARK_TEXT_SECONDARY = new Color(180, 180, 180);
    public static final Color ACCENT_BLUE = new Color(59, 130, 246);
    public static final Color ACCENT_BLUE_DARK = new Color(37, 99, 235);
    public static final Color ACCENT_BLUE_HOVER = new Color(96, 165, 250);
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);
    public static final Color ERROR_RED = new Color(239, 68, 68);

    // Light theme colors
    public static final Color LIGHT_BG = new Color(248, 250, 252);
    public static final Color LIGHT_SURFACE = new Color(255, 255, 255);
    public static final Color LIGHT_SURFACE_LIGHT = new Color(243, 244, 246);
    public static final Color LIGHT_TEXT = Color.BLACK;
    public static final Color LIGHT_TEXT_SECONDARY = new Color(100, 116, 139);

    public static void light() {
        UIManager.put("Panel.background", LIGHT_BG);
        UIManager.put("Button.background", ACCENT_BLUE);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.foreground", LIGHT_TEXT);
        UIManager.put("TextField.background", LIGHT_SURFACE);
        UIManager.put("TextField.foreground", LIGHT_TEXT);
        UIManager.put("TextField.caretForeground", LIGHT_TEXT);
        UIManager.put("PasswordField.background", LIGHT_SURFACE);
        UIManager.put("PasswordField.foreground", LIGHT_TEXT);
        UIManager.put("Table.background", LIGHT_SURFACE);
        UIManager.put("Table.foreground", LIGHT_TEXT);
        UIManager.put("TableHeader.background", LIGHT_SURFACE_LIGHT);
        UIManager.put("TableHeader.foreground", LIGHT_TEXT);
    }

    public static void dark() {
        UIManager.put("Panel.background", DARK_BG);
        UIManager.put("Button.background", ACCENT_BLUE);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.foreground", DARK_TEXT);
        UIManager.put("TextField.background", DARK_SURFACE);
        UIManager.put("TextField.foreground", DARK_TEXT);
        UIManager.put("TextField.caretForeground", DARK_TEXT);
        UIManager.put("PasswordField.background", DARK_SURFACE);
        UIManager.put("PasswordField.foreground", DARK_TEXT);
        UIManager.put("Table.background", DARK_SURFACE);
        UIManager.put("Table.foreground", DARK_TEXT);
        UIManager.put("TableHeader.background", DARK_SURFACE_LIGHT);
        UIManager.put("TableHeader.foreground", DARK_TEXT);
    }
}
