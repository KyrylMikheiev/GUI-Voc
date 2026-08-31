package src;

import src.ui.screens._BaseScreen;
import src.ui.screens.auth.Login;
import src.ui.screens.StartPage;
import src.ui.WindowManager;
import src.auth.*;

public class App {
    private static WindowManager windowManager;

    public static void run() {
        windowManager = new WindowManager();

        // Route post-authentication navigation back into the Swing UI.
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

        // Auth startup
        AuthManager.startupCheck();
        if (AuthManager.getState() == AuthState.NOT_LOGGED_IN) {
            switchScreen(new Login());
        } else {
            switchScreen(new StartPage());
        }
    }

    public static void switchScreen(_BaseScreen screen) {
        windowManager.pushNewScreen(screen);
    }

    public static void goBack() {
        windowManager.goBack();
    }

    public static void setFreshState(_BaseScreen screen) {
        windowManager.forceOverrideScreen(screen);
    }

    public static int[] getFrameSize() {
        return windowManager.getFrameSize();
    }

    public static void updateColors() {
        windowManager.updateColors();
    }
}