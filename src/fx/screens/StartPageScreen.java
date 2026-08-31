package src.fx.screens;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import src.fx.ui.FxScreen;
import src.fx.ui.Images;

/**
 * Controller for {@code StartPage.fxml}, replacing {@link src.ui.screens.StartPage}.
 *
 * <p>The Swing screen built six buttons in a loop, then used a single shared
 * MouseAdapter that compared {@code e.getComponent()} against each button to
 * decide where to navigate, and repainted backgrounds on hover. Layout and
 * routing are now declared in the FXML ({@code onAction="#onLearn"}), and hover
 * and pressed colours come from the {@code .app-button} CSS rules, so only the
 * navigation calls remain here.
 */
public class StartPageScreen extends FxScreen {

    @FXML private Button learnButton;
    @FXML private Button testButton;
    @FXML private Button libraryButton;
    @FXML private Button textButton;
    @FXML private Button gamesButton;
    @FXML private Button settingsButton;
    @FXML private TextArea infoArea;

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

    // The target screens are not migrated yet; each handler is the single line
    // that will become FxApp.switchScreen(new ...Screen()).

    @FXML
    private void onLearn() {
        notYetMigrated("Lernen");
    }

    @FXML
    private void onTest() {
        notYetMigrated("Testen");
    }

    @FXML
    private void onLibrary() {
        notYetMigrated("Bibliothek");
    }

    @FXML
    private void onTextChecker() {
        notYetMigrated("Textchecker");
    }

    @FXML
    private void onGames() {
        notYetMigrated("Minispiele");
    }

    @FXML
    private void onSettings() {
        notYetMigrated("Einstellungen");
    }

    private void notYetMigrated(String screenName) {
        infoArea.setText(screenName + " ist noch nicht auf JavaFX portiert.");
    }
}
