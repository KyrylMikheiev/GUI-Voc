package src.fx;

import javafx.stage.Stage;

import src.fx.screens.StartPageScreen;
import src.fx.screens.auth.LoginScreen;
import src.fx.ui.FxScreen;
import src.fx.ui.FxWindowManager;
import src.auth.AuthManager;
import src.auth.AuthNavigator;
import src.auth.AuthState;

/**
 * Static navigation facade the screens call into.
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
                setFreshState(new LoginScreen());
            }
        });

        AuthManager.startupCheck();
        if (AuthManager.getState() == AuthState.NOT_LOGGED_IN) {
            switchScreen(new LoginScreen());
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
