package src.fx.screens.settings;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;

import java.util.Map;
import java.util.Optional;

import src.api.APIClient;
import src.auth.AuthManager;
import src.fx.FxApp;
import src.fx.ui.FxScreen;
import src.fx.ui.Images;
import src.fx.ui.ThemeManager;

/**
 * Controller for {@code Settings.fxml}.
 *
 * <p>The dark-mode button calls ThemeManager, which swaps the stylesheet on the
 * live Scene, so nothing has to be rebuilt.
 */
public class SettingsScreen extends FxScreen {

    @FXML private Button darkModeButton;
    @FXML private ComboBox<String> widgetSelect;
    @FXML private ComboBox<String> classSelect;

    @Override
    protected String fxmlPath() {
        return "Settings.fxml";
    }

    @FXML
    private void initialize() {
        widgetSelect.setItems(FXCollections.observableArrayList(
                "Top 10 Fehler", "Leaderboard", "Letzte Lektionen"));
        widgetSelect.getSelectionModel().selectFirst();
        widgetSelect.setOnAction(e -> APIClient.updatePreferences(
                Map.of("widget", widgetSelect.getSelectionModel().getSelectedIndex() + 1)));

        classSelect.setItems(FXCollections.observableArrayList(
                "Jahrgang 5", "Jahrgang 6", "Jahrgang 7", "Jahrgang 8",
                "Jahrgang 9", "Jahrgang 10", "Jahrgang 11", "Jahrgang 12"));
        classSelect.getSelectionModel().selectFirst();

        updateToggleIcon();
    }

    @FXML
    private void onToggleDarkMode() {
        int newMode = ThemeManager.getMode() == 1 ? 2 : 1;
        ThemeManager.setMode(newMode);
        APIClient.updatePreferences(Map.of("mode", newMode));
        updateToggleIcon();
    }

    private void updateToggleIcon() {
        darkModeButton.setGraphic(Images.view(
                ThemeManager.getMode() == 1 ? "Schieberegler_OFF.png" : "Schieberegler_ON.png",
                60, 30));
    }

    @FXML
    private void onDeleteAccount() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setTitle("Konto löschen");
        dialog.setContentText("Enter Password:");

        Optional<String> password = dialog.showAndWait();
        if (password.isPresent() && AuthManager.deleteAccount(password.get())) {
            show("Account deleted successfully.");
        } else {
            show("Incorrect password. Data deletion aborted.");
        }
    }

    @FXML
    private void onCredits() {
        FxApp.switchScreen(new CreditsScreen());
    }

    @FXML
    private void onPrivacy() {
        FxApp.switchScreen(new PrivacyStatementScreen());
    }

    private void show(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
