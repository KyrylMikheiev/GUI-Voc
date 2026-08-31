package de.vocabtrainer;

import javafx.stage.Stage;

import de.vocabtrainer.ui.screens.StartPage;
import de.vocabtrainer.ui.screens.auth.Login;
import de.vocabtrainer.ui.Screen;
import de.vocabtrainer.ui.WindowManager;
import de.vocabtrainer.auth.AuthManager;
import de.vocabtrainer.auth.AuthNavigator;
import de.vocabtrainer.auth.AuthState;

/**
 * Static navigation facade the screens call into.
 */
public class App {

    private static WindowManager windowManager;

    public static void run(Stage stage) {
        windowManager = new WindowManager(stage);

        // Spike aid: start in dark mode with -Dvt.theme=dark to verify that a
        // stylesheet swap restyles the whole scene.
        if ("dark".equalsIgnoreCase(System.getProperty("vt.theme", ""))) {
            de.vocabtrainer.ui.Theme.setMode(de.vocabtrainer.ui.Theme.Mode.DARK);
        }

        // Route post-authentication navigation into the JavaFX UI.
        AuthManager.setNavigator(new AuthNavigator() {
            @Override
            public void toStartPage() {
                setFreshState(new StartPage());
            }

            @Override
            public void toLogin() {
                setFreshState(new Login());
            }
        });

        AuthManager.startupCheck();
        if (AuthManager.getState() == AuthState.NOT_LOGGED_IN) {
            switchScreen(new Login());
        } else {
            switchScreen(new StartPage());
        }

        windowManager.show();
    }

    public static void switchScreen(Screen screen) {
        windowManager.pushNewScreen(screen);
    }

    public static void goBack() {
        windowManager.goBack();
    }

    public static void setFreshState(Screen screen) {
        windowManager.forceOverrideScreen(screen);
    }

    public static int[] getFrameSize() {
        return windowManager.getFrameSize();
    }
}
