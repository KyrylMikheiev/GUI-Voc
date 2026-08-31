package src.fx.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Loads every migrated screen once and reports which ones fail.
 *
 * <p>FXML is resolved at runtime, so a bad {@code fx:id}, a handler name that
 * does not exist on the controller or a missing import compiles fine and only
 * fails when the screen is opened. This walks all screens in one pass so those
 * errors surface without clicking through the app.
 *
 * <pre>./mvnw javafx:run -Djavafx.mainClass=src.fx.ui.FxmlSmokeTest</pre>
 */
public class FxmlSmokeTest extends Application {

    /** Screens to load. Add each newly migrated screen here. */
    private static final List<Object[]> SCREENS = List.of(
            new Object[]{"StartPage", (Supplier<FxScreen>) src.fx.screens.StartPageScreen::new},
            new Object[]{"Login", (Supplier<FxScreen>) src.fx.screens.auth.LoginScreen::new},
            new Object[]{"SignUp", (Supplier<FxScreen>) src.fx.screens.auth.SignUpScreen::new},
            new Object[]{"ForgotPassword",
                    (Supplier<FxScreen>) src.fx.screens.auth.ForgotPasswordScreen::new},
            new Object[]{"Verification",
                    (Supplier<FxScreen>) () -> new src.fx.screens.auth.VerificationScreen("test@example.com")});

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        List<String> failures = new ArrayList<>();

        // The navbar is loaded by the window manager, not by a screen.
        try {
            new FxNavBar().getView();
            System.out.println("  ok   NavBar");
        } catch (Exception | Error e) {
            failures.add("NavBar: " + rootCause(e));
            System.out.println("  FAIL NavBar");
        }

        for (Object[] entry : SCREENS) {
            String name = (String) entry[0];
            Supplier<FxScreen> factory = (Supplier<FxScreen>) entry[1];
            try {
                factory.get().getView();
                System.out.println("  ok   " + name);
            } catch (Exception | Error e) {
                failures.add(name + ": " + rootCause(e));
                System.out.println("  FAIL " + name);
            }
        }

        System.out.println();
        if (failures.isEmpty()) {
            System.out.println("All " + (SCREENS.size() + 1) + " views loaded successfully.");
        } else {
            System.out.println(failures.size() + " view(s) failed:");
            for (String failure : failures) {
                System.out.println("  - " + failure);
            }
        }

        Platform.exit();
        System.exit(failures.isEmpty() ? 0 : 1);
    }

    private static String rootCause(Throwable t) {
        Throwable cause = t;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.toString();
    }
}
