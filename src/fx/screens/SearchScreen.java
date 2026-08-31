package src.fx.screens;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import src.fx.ui.FxScreen;

/**
 * Replaces {@link src.ui.screens.Search}.
 *
 * <p>Not implemented, as in Swing: that screen was marked "TODO: Implement" and
 * returned an empty panel. The query is kept so the search box in the navbar
 * can hand it over once this is built.
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
