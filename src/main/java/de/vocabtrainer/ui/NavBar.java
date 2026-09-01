package de.vocabtrainer.ui;

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

import de.vocabtrainer.App;
import de.vocabtrainer.ui.screens.StartPage;
import de.vocabtrainer.ui.screens.Search;
import de.vocabtrainer.ui.screens.settings.Settings;
import de.vocabtrainer.auth.AuthManager;

/**
 * Controller for {@code NavBar.fxml}.
 *
 * <p>Layout, menu entries and event wiring live in the FXML; this class only
 * holds behaviour.
 */
public class NavBar {

    @FXML private Button backButton;
    @FXML private Label appName;
    @FXML private HBox searchBox;
    @FXML private Label searchIcon;
    @FXML private TextField searchField;
    @FXML private MenuButton burgerMenu;

    private final Parent view;

    public NavBar() {
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
        App.goBack();
    }

    @FXML
    private void onHome() {
        App.switchScreen(new StartPage());
    }

    @FXML
    private void onSearch() {
        String query = searchField.getText();
        if (query != null && !query.isBlank()) {
            App.switchScreen(new Search(query));
        }
    }

    @FXML
    private void onSettings() {
        App.switchScreen(new Settings());
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
