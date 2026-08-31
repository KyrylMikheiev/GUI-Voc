package de.vocabtrainer.ui.screens.auth;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import de.vocabtrainer.auth.AuthManager;
import de.vocabtrainer.App;
import de.vocabtrainer.ui.Screen;

/**
 * Controller for {@code Verification.fxml}.
 *
 * <p>On success AuthManager navigates on through the AuthNavigator, so only the
 * failure path is handled here.
 */
public class Verification extends Screen {

    private final String email;

    @FXML private TextField codeField;
    @FXML private Label errorLabel;

    public Verification(String email) {
        this.email = email;
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    protected String fxmlPath() {
        return "Verification.fxml";
    }

    @FXML
    private void initialize() {
        Platform.runLater(codeField::requestFocus);
    }

    @FXML
    private void onVerify() {
        if (!AuthManager.verifyAccount(email, codeField.getText())) {
            // managed follows visible so the hidden label leaves no gap.
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    @FXML
    private void onToLogin() {
        App.switchScreen(new Login());
    }

    @FXML
    private void onToSignUp() {
        App.switchScreen(new SignUp());
    }
}
