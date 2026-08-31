package src.fx.screens.auth;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import src.api.APIClient;
import src.fx.FxApp;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code SignUp.fxml}, replacing {@link src.ui.screens.auth.SignUp}.
 *
 * <p>The Swing screen tracked a {@code signupStage} field and called
 * rebuildUI() to redraw everything on each step, carrying the entered values
 * across by hand. Both stages exist in the FXML here, so switching is a
 * visibility toggle and the fields keep their own state.
 */
public class SignUpScreen extends FxScreen {

    /** Matches the Swing default (userModePreference = 2). */
    private static final int MODE_PREFERENCE = 2;

    @FXML private VBox stageOne;
    @FXML private VBox stageTwo;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> gradeLevelBox;
    @FXML private Label stageOneError;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField repeatPasswordField;
    @FXML private Label stageTwoError;

    public SignUpScreen() {
        setKeepInHistory(false);
        setHasNavbar(false);
    }

    @Override
    protected String fxmlPath() {
        return "SignUp.fxml";
    }

    @FXML
    private void initialize() {
        gradeLevelBox.setItems(FXCollections.observableArrayList(
                "Freshman", "Sophomore", "Junior", "Senior"));
        gradeLevelBox.getSelectionModel().selectFirst();
        Platform.runLater(firstNameField::requestFocus);
    }

    @FXML
    private void onNext() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
            stageOneError.setVisible(true);
            stageOneError.setManaged(true);
            return;
        }
        stageOneError.setVisible(false);
        stageOneError.setManaged(false);
        showStage(false);
        Platform.runLater(emailField::requestFocus);
    }

    @FXML
    private void onBack() {
        showStage(true);
    }

    @FXML
    private void onToLogin() {
        FxApp.switchScreen(new LoginScreen());
    }

    @FXML
    private void onRegister() {
        if (!passwordField.getText().equals(repeatPasswordField.getText())) {
            showError("Passwörter stimmen nicht überein!");
            return;
        }

        // getSelectedIndex() + 1, as in the Swing version (Freshman = 1).
        int userClass = gradeLevelBox.getSelectionModel().getSelectedIndex() + 1;

        boolean created = APIClient.createUserAccount(
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                MODE_PREFERENCE,
                userClass);

        if (created) {
            FxApp.switchScreen(new VerificationScreen(emailField.getText()));
        } else {
            showError("Bereits angemeldete oder ungültige E-Mail!");
        }
    }

    private void showError(String message) {
        stageTwoError.setText(message);
        stageTwoError.setVisible(true);
        stageTwoError.setManaged(true);
    }

    /** managed follows visible so the hidden stage takes up no space. */
    private void showStage(boolean first) {
        stageOne.setVisible(first);
        stageOne.setManaged(first);
        stageTwo.setVisible(!first);
        stageTwo.setManaged(!first);
    }
}
