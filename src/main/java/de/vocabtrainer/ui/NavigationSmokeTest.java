package de.vocabtrainer.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import de.vocabtrainer.App;

/**
 * Clicks every button on the start page and reports which routes fail.
 *
 * <p>{@link FxmlSmokeTest} proves a screen can be loaded in isolation;
 * this drives the navigation that gets there, so a handler that throws is
 * caught even though its FXML is fine.
 *
 * <pre>./mvnw javafx:run -DmainClass=de.vocabtrainer.ui.NavigationSmokeTest</pre>
 */
public class NavigationSmokeTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        List<String> failures = new ArrayList<>();

        App.run(stage);

        int clicked = 0;

        // Every button on the start page.
        clicked += fireAll("start page",
                new de.vocabtrainer.ui.screens.StartPage().getView(), failures);

        // The lesson pickers build one button per lesson; firing them all
        // exercises LearningView and MemoryMain with real vocabulary.
        clicked += fireAll("Lernen → lesson",
                new de.vocabtrainer.ui.screens.learning.LearningSelection().getView(), failures);
        clicked += fireAll("Minispiele → lesson",
                new de.vocabtrainer.ui.screens.games.memory.MemorySelection().getView(), failures);

        // The library lists every word in the selected lesson.
        clicked += fireAll("Bibliothek → word",
                new de.vocabtrainer.ui.screens.learning.LibraryView().getView(), failures);

        // The memory game: Start with no input falls back to one pair, then
        // click each card, which covers reveal, match and mismatch.
        Parent memory = new de.vocabtrainer.ui.screens.games.memory.MemoryMain(
                Lessons.all().get(0)).getView();
        clicked += fireAll("Memory", memory, failures);
        clicked += fireAll("Memory board", memory, failures);

        System.out.println();
        if (failures.isEmpty()) {
            System.out.println("All " + clicked + " clicks worked.");
        } else {
            System.out.println(failures.size() + " route(s) failed:");
            for (String failure : failures) {
                System.out.println("  - " + failure);
            }
        }

        Platform.exit();
        System.exit(failures.isEmpty() ? 0 : 1);
    }

    /** Fires every button under {@code root}, reporting failures. */
    private static int fireAll(String what, Parent root, List<String> failures) {
        List<Button> buttons = new ArrayList<>();
        collectButtons(root, buttons);

        for (Button button : buttons) {
            String label = what + ": " + describe(button);
            try {
                button.fire();
                System.out.println("  ok   " + label);
            } catch (Exception | Error e) {
                failures.add(label + " -> " + rootCause(e));
                System.out.println("  FAIL " + label);
            }
        }
        return buttons.size();
    }

    private static String describe(Button button) {
        String text = button.getText();
        return text == null || text.isBlank() ? "<icon>" : text;
    }

    private static void collectButtons(Node node, List<Button> out) {
        if (node instanceof Button button) {
            out.add(button);
        }
        // A ScrollPane's content is not among its children, so the lesson
        // pickers would otherwise look empty.
        if (node instanceof javafx.scene.control.ScrollPane scrollPane) {
            collectButtons(scrollPane.getContent(), out);
        }
        if (node instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                collectButtons(child, out);
            }
        }
    }

    private static String rootCause(Throwable t) {
        Throwable cause = t;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.toString();
    }
}
