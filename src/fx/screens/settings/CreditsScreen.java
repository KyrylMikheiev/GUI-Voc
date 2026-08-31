package src.fx.screens.settings;

import src.fx.ui.FxScreen;

/**
 * Replaces {@link src.ui.screens.settings.Credits}. The screen is static text,
 * so everything is in {@code Credits.fxml}.
 */
public class CreditsScreen extends FxScreen {

    @Override
    protected String fxmlPath() {
        return "Credits.fxml";
    }
}
