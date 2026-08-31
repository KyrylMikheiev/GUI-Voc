package de.vocabtrainer.ui;

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
 * <pre>./mvnw javafx:run -DmainClass=de.vocabtrainer.ui.FxmlSmokeTest</pre>
 */
public class FxmlSmokeTest extends Application {

    /** Screens to load. Add each new screen here. */
    private static final List<Object[]> SCREENS = List.of(
            new Object[]{"StartPage", (Supplier<Screen>) de.vocabtrainer.ui.screens.StartPage::new},
            new Object[]{"Login", (Supplier<Screen>) de.vocabtrainer.ui.screens.auth.Login::new},
            new Object[]{"SignUp", (Supplier<Screen>) de.vocabtrainer.ui.screens.auth.SignUp::new},
            new Object[]{"ForgotPassword",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.auth.ForgotPassword::new},
            new Object[]{"Verification",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.auth.Verification("test@example.com")},
            new Object[]{"LearningSelection",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.learning.LearningSelection::new},
            new Object[]{"LibraryView",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.learning.LibraryView::new},
            new Object[]{"LearningView",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.learning.LearningView(
                            de.vocabtrainer.ui.Lessons.all().get(0))},
            new Object[]{"VocabView(Verb)",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.learning.VocabView(
                            firstOfType(VocabAPI.WordTypes.Verb.class))},
            new Object[]{"VocabView(Adjective)",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.learning.VocabView(
                            firstOfType(VocabAPI.WordTypes.Adjective.class))},
            new Object[]{"VocabView(Noun)",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.learning.VocabView(
                            firstOfType(VocabAPI.WordTypes.Noun.class))},
            new Object[]{"TestSelection",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.test.TestSelection::new},
            new Object[]{"TestView",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.test.TestView(
                            java.util.List.of("Lektion " + de.vocabtrainer.ui.Lessons.all().get(0)),
                            new de.vocabtrainer.ui.screens.test.TestOptions(false, false, false, false, false))},
            new Object[]{"TextChecker",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.test.TextChecker::new},
            new Object[]{"Search",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.Search("test")},
            new Object[]{"Settings",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.settings.Settings::new},
            new Object[]{"Credits",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.settings.Credits::new},
            new Object[]{"PrivacyStatement",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.settings.PrivacyStatement::new},
            new Object[]{"GameSelection",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.games.GameSelection::new},
            new Object[]{"MemorySelection",
                    (Supplier<Screen>) de.vocabtrainer.ui.screens.games.memory.MemorySelection::new},
            new Object[]{"MemoryMain",
                    (Supplier<Screen>) () -> new de.vocabtrainer.ui.screens.games.memory.MemoryMain(
                            de.vocabtrainer.ui.Lessons.all().get(0))});

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
            new NavBar().getView();
            System.out.println("  ok   NavBar");
        } catch (Exception | Error e) {
            failures.add("NavBar: " + rootCause(e));
            System.out.println("  FAIL NavBar");
        }

        for (Object[] entry : SCREENS) {
            String name = (String) entry[0];
            Supplier<Screen> factory = (Supplier<Screen>) entry[1];
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
