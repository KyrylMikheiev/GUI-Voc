package de.vocabtrainer.ui.screens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import de.vocabtrainer.ui.Screen;
import de.vocabtrainer.ui.Images;
import de.vocabtrainer.App;
import de.vocabtrainer.ui.screens.learning.LearningSelection;
import de.vocabtrainer.ui.screens.learning.LibraryView;
import de.vocabtrainer.ui.screens.test.TestSelection;
import de.vocabtrainer.ui.screens.test.TextChecker;
import de.vocabtrainer.ui.screens.settings.Settings;
import de.vocabtrainer.ui.screens.games.GameSelection;

/**
 * Controller for {@code StartPage.fxml}.
 *
 * <p>Layout and routing are declared in the FXML ({@code onAction="#onLearn"}),
 * and hover and pressed colours come from the {@code .app-button} CSS rules, so
 * only the navigation calls remain here.
 */
public class StartPage extends Screen {

    @FXML private Button learnButton;
    @FXML private Button testButton;
    @FXML private Button libraryButton;
    @FXML private Button textButton;
    @FXML private Button gamesButton;
    @FXML private Button settingsButton;

    @Override
    protected String fxmlPath() {
        return "StartPage.fxml";
    }

    @FXML
    private void initialize() {
        learnButton.setGraphic(Images.view("card-index.png", 30, 30));
        testButton.setGraphic(Images.view("exam-results.png", 30, 30));
        libraryButton.setGraphic(Images.view("digital-library.png", 30, 30));
        textButton.setGraphic(Images.view("text-frame.png", 30, 30));
        gamesButton.setGraphic(Images.view("gamepad.png", 30, 30));
        settingsButton.setGraphic(Images.view("settings-gear-icon.png", 30, 30));
    }

    @FXML
    private void onLearn() {
        App.switchScreen(new LearningSelection());
    }

    @FXML
    private void onTest() {
        App.switchScreen(new TestSelection());
    }

    @FXML
    private void onLibrary() {
        App.switchScreen(new LibraryView());
    }

    @FXML
    private void onTextChecker() {
        App.switchScreen(new TextChecker());
    }

    @FXML
    private void onGames() {
        App.switchScreen(new GameSelection());
    }

    @FXML
    private void onSettings() {
        App.switchScreen(new Settings());
    }
}
