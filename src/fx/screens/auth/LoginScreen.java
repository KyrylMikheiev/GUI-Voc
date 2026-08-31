package src.fx.screens.auth;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import src.auth.AuthManager;
import src.auth.AuthState;
import src.fx.FxApp;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code Login.fxml}, replacing {@link src.ui.screens.auth.Login}.
 *
 * <p>On success AuthManager itself navigates on, via the AuthNavigator that
 * FxApp registers, so this class only has to handle the failure paths.
 */
public class LoginScreen extends FxScreen {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    public LoginScreen() {
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    protected String fxmlPath() {
        return "Login.fxml";
    }

    @FXML
    private void initialize() {
        // Enter in either field submits, matching the Swing form's behaviour.
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
            FxApp.switchScreen(new VerificationScreen(emailField.getText()));
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
        FxApp.switchScreen(new SignUpScreen());
    }

    @FXML
    private void onForgotPassword() {
        FxApp.switchScreen(new ForgotPasswordScreen());
    }
}
