package src.ui;

import java.awt.Color;

import java.util.Map;

enum ColorMode {
    LIGHT(1),
    DARK(2);

    private final int value;

    ColorMode(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ColorMode fromValue(int value) {
        for (ColorMode mode : ColorMode.values()) {
            if (mode.value == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

public class ColorManager {
    private static ColorMode mode = ColorMode.LIGHT;

    private static Map<ColorMode, Color> buttonDefault = Map.of(
        ColorMode.LIGHT, Color.decode("#B7B7B7"),
        ColorMode.DARK, Color.decode("#4d6190"));
    
    private static Map<ColorMode, Color> buttonHover = Map.of(
        ColorMode.LIGHT, Color.decode("#E1E1E1"),
        ColorMode.DARK, Color.decode("#4255ff"));
    
    private static Map<ColorMode, Color> buttonClick = Map.of(
        ColorMode.LIGHT, Color.decode("#FAFAFA"),
        ColorMode.DARK, Color.decode("#2f3990"));
    
    private static Map<ColorMode, Color> bodyPrimary = Map.of(
        ColorMode.LIGHT, Color.decode("#FFFFFF"),
        ColorMode.DARK, Color.decode("#111827"));
    
    private static Map<ColorMode, Color> bodySecondary = Map.of(
        ColorMode.LIGHT, Color.decode("#DDDDDD"),
        ColorMode.DARK, Color.decode("#1f2937"));
    
    private static Map<ColorMode, Color> text = Map.of(
        ColorMode.LIGHT, Color.decode("#000000"),
        ColorMode.DARK, Color.decode("#f9fafb"));
    
    private static Map<ColorMode, Color> navbarBg = Map.of(
        ColorMode.LIGHT, Color.GRAY,
        ColorMode.DARK, Color.GRAY);

    private static Map<ColorMode, Color> navbarText = Map.of(
        ColorMode.LIGHT, Color.BLACK,
        ColorMode.DARK, Color.BLACK);

    public static void setMode(ColorMode mode) {
        ColorManager.mode = mode;
    }

    public static void setMode(int mode) {
        ColorManager.mode = ColorMode.fromValue(mode);
    }

    public static int getMode() {
        return ColorManager.mode.value();
    }

    public static Color buttonDefault() {
        return ColorManager.buttonDefault.get(ColorManager.mode);
    }

    public static Color buttonHover() {
        return buttonHover.get(mode);
    }

    public static Color buttonClick() {
        return buttonClick.get(mode);
    }

    public static Color bodyPrimary() {
        return bodyPrimary.get(mode);
    }

    public static Color bodySecondary() {
        return bodySecondary.get(mode);
    }

    public static Color text() {
        return text.get(mode);
    }

    public static Color navbarBg() {
        return navbarBg.get(mode);
    }

    public static Color navbarText() {
        return navbarText.get(mode);
    }
}