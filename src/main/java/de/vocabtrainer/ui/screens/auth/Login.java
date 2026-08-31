package de.vocabtrainer.ui.screens.auth;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import de.vocabtrainer.auth.AuthManager;
import de.vocabtrainer.auth.AuthState;
import de.vocabtrainer.App;
import de.vocabtrainer.ui.Screen;

/**
 * Controller for {@code Login.fxml}.
 *
 * <p>On success AuthManager itself navigates on, via the AuthNavigator that
 * App registers, so this class only has to handle the failure paths.
 */
public class Login extends Screen {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    public Login() {
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    protected String fxmlPath() {
        return "Login.fxml";
    }

    @FXML
    private void initialize() {
        emailField.setOnAction(e -> onLogin());
        passwordField.setOnAction(e -> onLogin());
        Platform.runLater(emailField::requestFocus);
    }

    @FXML
    private void onLogin() {
        showError(false);

        boolean success = AuthManager.login(emailField.getText(), passwordField.getText());
        if (success) {
            // AuthManager routes to the start page through the AuthNavigator.
            return;
        }

        if (AuthManager.getState() == AuthState.NEED_VERIFICATION) {
            App.switchScreen(new Verification(emailField.getText()));
        } else {
            showError(true);
        }
    }

    /** managed follows visible so the hidden label leaves no gap. */
    private void showError(boolean show) {
        errorLabel.setVisible(show);
        errorLabel.setManaged(show);
    }

    @FXML
    private void onSignUp() {
        App.switchScreen(new SignUp());
    }

    @FXML
    private void onForgotPassword() {
        App.switchScreen(new ForgotPassword());
    }
}
