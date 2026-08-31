package src.fx.screens.auth;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import src.auth.AuthManager;
import src.fx.FxApp;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code Verification.fxml}, replacing
 * {@link src.ui.screens.auth.Verification}.
 *
 * <p>On success AuthManager navigates on through the AuthNavigator, so only the
 * failure path is handled here.
 */
public class VerificationScreen extends FxScreen {

    private final String email;

    @FXML private Label emailLabel;
    @FXML private TextField codeField;
    @FXML private Label errorLabel;

    public VerificationScreen(String email) {
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
        // The Swing screen never showed which address the code went to.
        emailLabel.setText("Code gesendet an: " + email);
        codeField.setOnAction(e -> onVerify());
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
        FxApp.switchScreen(new LoginScreen());
    }

    @FXML
    private void onToSignUp() {
        FxApp.switchScreen(new SignUpScreen());
    }
}
