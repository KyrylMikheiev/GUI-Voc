package src.fx.screens.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import src.fx.FxApp;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code ForgotPassword.fxml}, replacing
 * {@link src.ui.screens.auth.ForgotPassword}.
 *
 * <p>Password reset is not implemented. The Swing screen was marked
 * "TODO: Implement functionality" and its Submit button had no listener at all;
 * here it at least tells the user nothing happened rather than doing nothing
 * silently. Wire this up once the API supports a reset endpoint.
 */
public class ForgotPasswordScreen extends FxScreen {

    @FXML private TextField emailField;
    @FXML private Label statusLabel;

    public ForgotPasswordScreen() {
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    protected String fxmlPath() {
        return "ForgotPassword.fxml";
    }

    @FXML
    private void onSubmit() {
        // TODO: call the reset endpoint once APIClient exposes one.
        statusLabel.setText("Passwort-Reset ist noch nicht implementiert.");
        statusLabel.setVisible(true);
        statusLabel.setManaged(true);
    }

    @FXML
    private void onToLogin() {
        FxApp.switchScreen(new LoginScreen());
    }

    @FXML
    private void onToSignUp() {
        FxApp.switchScreen(new SignUpScreen());
    }
}
