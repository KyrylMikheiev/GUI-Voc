package src.fx.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Loads every screen once and reports which ones fail.
 *
 * <p>FXML is resolved at runtime, so a bad {@code fx:id}, a handler name that
 * does not exist on the controller or a missing import compiles fine and only
 * fails when the screen is opened. This walks all screens in one pass so those
 * errors surface without clicking through the app.
 *
 * <pre>./mvnw javafx:run -DmainClass=src.fx.ui.FxmlSmokeTest</pre>
 */
public class FxmlSmokeTest extends Application {

    /** Screens to load. Add each new screen here. */
    private static final List<Object[]> SCREENS = List.of(
            new Object[]{"StartPage", (Supplier<FxScreen>) src.fx.screens.StartPageScreen::new},
            new Object[]{"Login", (Supplier<FxScreen>) src.fx.screens.auth.LoginScreen::new},
            new Object[]{"SignUp", (Supplier<FxScreen>) src.fx.screens.auth.SignUpScreen::new},
            new Object[]{"ForgotPassword",
                    (Supplier<FxScreen>) src.fx.screens.auth.ForgotPasswordScreen::new},
            new Object[]{"Verification",
                    (Supplier<FxScreen>) () -> new src.fx.screens.auth.VerificationScreen("test@example.com")},
            new Object[]{"LearningSelection",
                    (Supplier<FxScreen>) src.fx.screens.learning.LearningSelectionScreen::new},
            new Object[]{"LibraryView",
                    (Supplier<FxScreen>) src.fx.screens.learning.LibraryViewScreen::new},
            new Object[]{"LearningView",
                    (Supplier<FxScreen>) () -> new src.fx.screens.learning.LearningViewScreen(
                            src.fx.ui.Lessons.all().get(0))},
            new Object[]{"VocabView(Verb)",
                    (Supplier<FxScreen>) () -> new src.fx.screens.learning.VocabViewScreen(
                            firstOfType(VocabAPI.WordTypes.Verb.class))},
            new Object[]{"VocabView(Adjective)",
                    (Supplier<FxScreen>) () -> new src.fx.screens.learning.VocabViewScreen(
                            firstOfType(VocabAPI.WordTypes.Adjective.class))},
            new Object[]{"VocabView(Noun)",
                    (Supplier<FxScreen>) () -> new src.fx.screens.learning.VocabViewScreen(
                            firstOfType(VocabAPI.WordTypes.Noun.class))},
            new Object[]{"TestSelection",
                    (Supplier<FxScreen>) src.fx.screens.test.TestSelectionScreen::new},
            new Object[]{"TestView",
                    (Supplier<FxScreen>) () -> new src.fx.screens.test.TestViewScreen(
                            java.util.List.of("Lektion " + src.fx.ui.Lessons.all().get(0)),
                            new src.fx.screens.test.TestOptions(false, false, false, false, false))},
            new Object[]{"TextChecker",
                    (Supplier<FxScreen>) src.fx.screens.test.TextCheckerScreen::new},
            new Object[]{"Search",
                    (Supplier<FxScreen>) () -> new src.fx.screens.SearchScreen("test")},
            new Object[]{"Settings",
                    (Supplier<FxScreen>) src.fx.screens.settings.SettingsScreen::new},
            new Object[]{"Credits",
                    (Supplier<FxScreen>) src.fx.screens.settings.CreditsScreen::new},
            new Object[]{"PrivacyStatement",
                    (Supplier<FxScreen>) src.fx.screens.settings.PrivacyStatementScreen::new},
            new Object[]{"GameSelection",
                    (Supplier<FxScreen>) src.fx.screens.games.GameSelectionScreen::new},
            new Object[]{"MemorySelection",
                    (Supplier<FxScreen>) src.fx.screens.games.memory.MemorySelectionScreen::new},
            new Object[]{"MemoryMain",
                    (Supplier<FxScreen>) () -> new src.fx.screens.games.memory.MemoryMainScreen(
                            src.fx.ui.Lessons.all().get(0))});

    /** First vocabulary entry of the given word type, for the table screens. */
    private static VocabAPI.WordTypes.Vocab firstOfType(Class<?> type) {
        for (VocabAPI.WordTypes.Vocab vocab : VocabAPI.VocabParser.getAllVocabs()) {
            if (type.isInstance(vocab)) {
                return vocab;
            }
        }
        throw new IllegalStateException("No vocabulary of type " + type.getSimpleName());
    }

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
