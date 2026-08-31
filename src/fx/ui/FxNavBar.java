package src.fx.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.io.UncheckedIOException;

import src.fx.FxApp;
import src.fx.screens.StartPageScreen;
import src.fx.screens.SearchScreen;
import src.fx.screens.settings.SettingsScreen;
import src.auth.AuthManager;

/**
 * Controller for {@code NavBar.fxml}.
 *
 * <p>Layout, menu entries and event wiring live in the FXML; this class only
 * holds behaviour.
 */
public class FxNavBar {

    @FXML private Button backButton;
    @FXML private Label appName;
    @FXML private HBox searchBox;
    @FXML private Label searchIcon;
    @FXML private TextField searchField;
    @FXML private MenuButton burgerMenu;

    private final Parent view;

    public FxNavBar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NavBar.fxml"));
        loader.setController(this);
        try {
            view = loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load NavBar.fxml", e);
        }
    }

    @FXML
    private void initialize() {
        backButton.setGraphic(Images.view("arrow.png", 45, 45));
        appName.setGraphic(Images.view("simplelogo.png", 50, 50));
        searchIcon.setGraphic(Images.view("search.png", 25, 20));
        burgerMenu.setGraphic(Images.view("burgerMenuIcon.png", 60, 60));
    }

    public Parent getView() {
        return view;
    }

    /** Greys out the back button when there is nothing to go back to. */
    public void setBackEnabled(boolean enabled) {
        backButton.setDisable(!enabled);
    }

    @FXML
    private void onBack() {
        FxApp.goBack();
    }

    @FXML
    private void onHome() {
        FxApp.switchScreen(new StartPageScreen());
    }

    @FXML
    private void onSearch() {
        String query = searchField.getText();
        if (query != null && !query.isBlank()) {
            FxApp.switchScreen(new SearchScreen(query));
        }
    }

    @FXML
    private void onSettings() {
        FxApp.switchScreen(new SettingsScreen());
    }

    @FXML
    private void onLogout() {
        AuthManager.logout();
    }

    @FXML
    private void onExit() {
        javafx.application.Platform.exit();
        System.exit(0);
    }
}
