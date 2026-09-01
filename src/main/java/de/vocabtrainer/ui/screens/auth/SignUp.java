package de.vocabtrainer.ui.screens.auth;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import de.vocabtrainer.api.APIClient;
import de.vocabtrainer.App;
import de.vocabtrainer.ui.Screen;

/**
 * Controller for {@code SignUp.fxml}.
 *
 * <p>Both wizard stages exist in the FXML, so switching between them is a
 * visibility toggle and the fields keep their own state.
 */
public class SignUp extends Screen {

    /** Default mode preference sent with a new account. */
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

    public SignUp() {
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
        App.switchScreen(new Login());
    }

    @FXML
    private void onRegister() {
        if (!passwordField.getText().equals(repeatPasswordField.getText())) {
            showError("Passwörter stimmen nicht überein!");
            return;
        }

        // The API numbers classes from 1 (Freshman = 1).
        int userClass = gradeLevelBox.getSelectionModel().getSelectedIndex() + 1;

        boolean created = APIClient.createUserAccount(
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                MODE_PREFERENCE,
                userClass);

        if (created) {
            App.switchScreen(new Verification(emailField.getText()));
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
