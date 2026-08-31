package de.vocabtrainer.ui.screens.games;

import javafx.fxml.FXML;

import de.vocabtrainer.App;
import de.vocabtrainer.ui.screens.games.memory.MemorySelection;
import de.vocabtrainer.ui.Screen;

/** Controller for {@code GameSelection.fxml}. */
public class GameSelection extends Screen {

    @Override
    protected String fxmlPath() {
        return "GameSelection.fxml";
    }

    @FXML
    private void onMemory() {
        App.switchScreen(new MemorySelection());
    }
}
