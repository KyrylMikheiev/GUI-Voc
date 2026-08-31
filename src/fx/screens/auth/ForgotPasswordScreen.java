package src.fx.screens.auth;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import src.fx.FxApp;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code ForgotPassword.fxml}, replacing
 * {@link src.ui.screens.auth.ForgotPassword}.
 *
 * <p>Password reset is not implemented, as in Swing: that screen was marked
 * "TODO: Implement functionality" and its Submit button had no listener.
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

    // TODO: Implement functionality — the Swing Submit button had no listener
    // either, so the FXML leaves it unwired.

    @FXML
    private void onToLogin() {
        FxApp.switchScreen(new LoginScreen());
    }

    @FXML
    private void onToSignUp() {
        FxApp.switchScreen(new SignUpScreen());
    }
}
