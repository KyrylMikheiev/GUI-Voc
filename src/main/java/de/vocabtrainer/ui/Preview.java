package de.vocabtrainer.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Opens a single screen by name, for visual checks. */
public class Preview extends Application {
    public static void main(String[] args) { launch(args); }

    private static VocabAPI.WordTypes.Vocab firstVerb() {
        for (VocabAPI.WordTypes.Vocab v : VocabAPI.VocabParser.getAllVocabs()) {
            if (v instanceof VocabAPI.WordTypes.Verb) return v;
        }
        return VocabAPI.VocabParser.getAllVocabs().get(0);
    }

    private static VocabAPI.WordTypes.Vocab byForm(String form) {
        for (VocabAPI.WordTypes.Vocab v : VocabAPI.VocabParser.getAllVocabs()) {
            if (form.equals(v.getBasicForm())) return v;
        }
        return firstVerb();
    }

    @Override
    public void start(Stage stage) {
        String which = System.getProperty("screen", "SignUp");
        Screen screen = switch (which) {
            case "Login" -> new de.vocabtrainer.ui.screens.auth.Login();
            case "SignUp" -> new de.vocabtrainer.ui.screens.auth.SignUp();
            case "ForgotPassword" -> new de.vocabtrainer.ui.screens.auth.ForgotPassword();
            case "Verification" -> new de.vocabtrainer.ui.screens.auth.Verification("test@example.com");
            case "LearningSelection" -> new de.vocabtrainer.ui.screens.learning.LearningSelection();
            case "LibraryView" -> new de.vocabtrainer.ui.screens.learning.LibraryView();
            case "LearningView" -> new de.vocabtrainer.ui.screens.learning.LearningView(
                    Lessons.all().get(0));
            case "VocabView" -> new de.vocabtrainer.ui.screens.learning.VocabView(
                    firstVerb());
            case "VocabViewIrregular" -> new de.vocabtrainer.ui.screens.learning.VocabView(
                    byForm("esse"));
            default -> new de.vocabtrainer.ui.screens.StartPage();
        };
        BorderPane root = new BorderPane(screen.getView());
        root.getStyleClass().add("body-primary");
        Scene scene = new Scene(root, 1280, 720);
        Theme.install(scene);
        if ("dark".equalsIgnoreCase(System.getProperty("vt.theme", ""))) {
            Theme.setMode(Theme.Mode.DARK);
        }
        stage.setScene(scene);
        stage.setTitle("Vokabeltrainer");
        stage.show();
    }
}
