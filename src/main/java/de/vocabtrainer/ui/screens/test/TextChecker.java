package de.vocabtrainer.ui.screens.test;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import de.vocabtrainer.ui.Screen;

/**
 * Text checker.
 *
 * <p>Not implemented; there is no FXML because there is nothing to lay out
 * yet, as was the case before the JavaFX port.
 */
public class TextChecker extends Screen {

    @Override
    protected Parent createView() {
        HBox empty = new HBox();
        empty.getStyleClass().add("body-primary");
        return empty;
    }
}
