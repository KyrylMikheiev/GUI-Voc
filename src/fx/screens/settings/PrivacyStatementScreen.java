package src.fx.screens.settings;

import src.fx.ui.FxScreen;

/**
 * Replaces {@link src.ui.screens.settings.PrivacyStatement}. Static text, so
 * everything is in {@code PrivacyStatement.fxml}.
 */
public class PrivacyStatementScreen extends FxScreen {

    @Override
    protected String fxmlPath() {
        return "PrivacyStatement.fxml";
    }
}
