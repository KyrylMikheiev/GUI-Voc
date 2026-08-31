package src.fx.screens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import src.fx.ui.FxScreen;
import src.fx.ui.Images;
import src.fx.FxApp;
import src.fx.screens.learning.LearningSelectionScreen;
import src.fx.screens.learning.LibraryViewScreen;
import src.fx.screens.test.TestSelectionScreen;
import src.fx.screens.test.TextCheckerScreen;
import src.fx.screens.settings.SettingsScreen;
import src.fx.screens.games.GameSelectionScreen;

/**
 * Controller for {@code StartPage.fxml}.
 *
 * <p>Layout and routing are declared in the FXML ({@code onAction="#onLearn"}),
 * and hover and pressed colours come from the {@code .app-button} CSS rules, so
 * only the navigation calls remain here.
 */
public class StartPageScreen extends FxScreen {

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
        FxApp.switchScreen(new LearningSelectionScreen());
    }

    @FXML
    private void onTest() {
        FxApp.switchScreen(new TestSelectionScreen());
    }

    @FXML
    private void onLibrary() {
        FxApp.switchScreen(new LibraryViewScreen());
    }

    @FXML
    private void onTextChecker() {
        FxApp.switchScreen(new TextCheckerScreen());
    }

    @FXML
    private void onGames() {
        FxApp.switchScreen(new GameSelectionScreen());
    }

    @FXML
    private void onSettings() {
        FxApp.switchScreen(new SettingsScreen());
    }
}
