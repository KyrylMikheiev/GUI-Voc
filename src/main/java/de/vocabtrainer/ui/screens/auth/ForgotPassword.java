package de.vocabtrainer.ui.screens.auth;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import de.vocabtrainer.App;
import de.vocabtrainer.ui.Screen;

/**
 * Controller for {@code ForgotPassword.fxml}.
 *
 * <p>Password reset is not implemented; the Submit button is deliberately
 * unwired, as it was before the JavaFX port.
 */
public class ForgotPassword extends Screen {

    @FXML private TextField emailField;

    public ForgotPassword() {
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    protected String fxmlPath() {
        return "ForgotPassword.fxml";
    }

    // TODO: Implement functionality. The FXML leaves Submit unwired until
    // APIClient exposes a reset endpoint.

    @FXML
    private void onToLogin() {
        App.switchScreen(new Login());
    }

    @FXML
    private void onToSignUp() {
        App.switchScreen(new SignUp());
    }
}
