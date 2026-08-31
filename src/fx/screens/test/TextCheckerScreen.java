package src.fx.screens.test;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import src.fx.ui.FxScreen;

/**
 * Replaces {@link src.ui.screens.test.TextChecker}.
 *
 * <p>Not implemented. The Swing screen returned an empty two-column panel with
 * nothing in it; there is no FXML because there is nothing to lay out yet.
 */
public class TextCheckerScreen extends FxScreen {

    @Override
    protected Parent createView() {
        HBox empty = new HBox();
        empty.getStyleClass().add("body-primary");
        return empty;
    }
}
