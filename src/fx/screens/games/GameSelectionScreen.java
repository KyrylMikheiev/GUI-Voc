package src.fx.screens.games;

import javafx.fxml.FXML;

import src.fx.FxApp;
import src.fx.screens.games.memory.MemorySelectionScreen;
import src.fx.ui.FxScreen;

/** Controller for {@code GameSelection.fxml}. */
public class GameSelectionScreen extends FxScreen {

    @Override
    protected String fxmlPath() {
        return "GameSelection.fxml";
    }

    @FXML
    private void onMemory() {
        FxApp.switchScreen(new MemorySelectionScreen());
    }
}
