package src.fx.screens.auth;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import src.fx.FxApp;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code ForgotPassword.fxml}.
 *
 * <p>Password reset is not implemented; the Submit button is deliberately
 * unwired, as it was before the JavaFX port.
 */
public class ForgotPasswordScreen extends FxScreen {

    @FXML private TextField emailField;

    public ForgotPasswordScreen() {
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
        FxApp.switchScreen(new LoginScreen());
    }

    @FXML
    private void onToSignUp() {
        FxApp.switchScreen(new SignUpScreen());
    }
}
