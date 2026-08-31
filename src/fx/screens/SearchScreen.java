package src.fx.screens;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import src.fx.ui.FxScreen;

/**
 * Quick-search results.
 *
 * <p>Not implemented; it renders an empty pane, as it did before the JavaFX
 * port. The query is kept so the navbar's search box can hand it over once
 * this is built.
 */
public class SearchScreen extends FxScreen {

    private final String query;

    public SearchScreen(String query) {
        this.query = query;
        setKeepInHistory(false);
    }

    public String getQuery() {
        return query;
    }

    @Override
    protected Parent createView() {
        VBox empty = new VBox();
        empty.getStyleClass().add("body-primary");
        return empty;
    }
}
