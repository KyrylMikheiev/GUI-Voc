package src.fx.screens.test;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import src.fx.ui.FxScreen;

/**
 * Text checker.
 *
 * <p>Not implemented; there is no FXML because there is nothing to lay out
 * yet, as was the case before the JavaFX port.
 */
public class TextCheckerScreen extends FxScreen {

    @Override
    protected Parent createView() {
        HBox empty = new HBox();
        empty.getStyleClass().add("body-primary");
        return empty;
    }
}
