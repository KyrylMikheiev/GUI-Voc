package src.fx;

import javafx.stage.Stage;

import src.fx.screens.StartPageScreen;
import src.fx.ui.FxScreen;
import src.fx.ui.FxWindowManager;
import src.auth.AuthManager;
import src.auth.AuthNavigator;
import src.auth.AuthState;

/**
 * JavaFX counterpart of {@link src.App}: the static navigation facade the
 * screens call into.
 */
public class FxApp {

    private static FxWindowManager windowManager;

    public static void run(Stage stage) {
        windowManager = new FxWindowManager(stage);

        // Spike aid: start in dark mode with -Dvt.theme=dark to verify that a
        // stylesheet swap restyles the whole scene.
        if ("dark".equalsIgnoreCase(System.getProperty("vt.theme", ""))) {
            src.fx.ui.ThemeManager.setMode(src.fx.ui.ThemeManager.Mode.DARK);
        }

        // Route post-authentication navigation into the JavaFX UI.
        AuthManager.setNavigator(new AuthNavigator() {
            @Override
            public void toStartPage() {
                setFreshState(new StartPageScreen());
            }

            @Override
            public void toLogin() {
                // TODO: replace with the migrated Login screen.
                setFreshState(new StartPageScreen());
            }
        });

        AuthManager.startupCheck();
        if (AuthManager.getState() == AuthState.NOT_LOGGED_IN) {
            // TODO: show the migrated Login screen once it exists. The spike
            // opens the start page so the shell can be evaluated.
            switchScreen(new StartPageScreen());
        } else {
            switchScreen(new StartPageScreen());
        }

        windowManager.show();
    }

    public static void switchScreen(FxScreen screen) {
        windowManager.pushNewScreen(screen);
    }

    public static void goBack() {
        windowManager.goBack();
    }

    public static void setFreshState(FxScreen screen) {
        windowManager.forceOverrideScreen(screen);
    }

    public static int[] getFrameSize() {
        return windowManager.getFrameSize();
    }
}
