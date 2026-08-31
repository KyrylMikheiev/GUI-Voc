package src.fx.ui;

import javafx.scene.Scene;

/**
 * JavaFX counterpart of {@link src.ui.ColorManager}.
 *
 * <p>Swing had no styling layer, so ColorManager handed out Color objects that
 * every screen applied by hand and a theme change meant rebuilding the whole UI.
 * Here the palette lives in CSS: switching the theme swaps one stylesheet on the
 * live Scene and every node restyles itself, so no screen has to be rebuilt.
 */
public final class ThemeManager {

    public enum Mode {
        LIGHT(1),
        DARK(2);

        private final int value;

        Mode(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }

        public static Mode fromValue(int value) {
            for (Mode mode : values()) {
                if (mode.value == value) {
                    return mode;
                }
            }
            throw new IllegalArgumentException("Unknown value: " + value);
        }
    }

    private static final String COMMON_CSS = resource("common.css");
    private static final String LIGHT_CSS = resource("theme-light.css");
    private static final String DARK_CSS = resource("theme-dark.css");

    private static Mode mode = Mode.LIGHT;
    private static Scene scene;

    private ThemeManager() {
    }

    private static String resource(String name) {
        return ThemeManager.class.getResource(name).toExternalForm();
    }

    /** Attaches the stylesheets to the application's single Scene. */
    public static void install(Scene target) {
        scene = target;
        apply();
    }

    public static void setMode(Mode newMode) {
        mode = newMode;
        apply();
    }

    /** Accepts the same int encoding the Swing settings screen persisted. */
    public static void setMode(int newMode) {
        setMode(Mode.fromValue(newMode));
    }

    public static int getMode() {
        return mode.value();
    }

    public static void toggle() {
        setMode(mode == Mode.LIGHT ? Mode.DARK : Mode.LIGHT);
    }

    private static void apply() {
        if (scene == null) {
            return;
        }
        scene.getStylesheets().setAll(
                COMMON_CSS,
                mode == Mode.LIGHT ? LIGHT_CSS : DARK_CSS);
    }
}
