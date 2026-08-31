package de.vocabtrainer.ui.screens;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import de.vocabtrainer.ui.Screen;

/**
 * Quick-search results.
 *
 * <p>Not implemented; it renders an empty pane, as it did before the JavaFX
 * port. The query is kept so the navbar's search box can hand it over once
 * this is built.
 */
public class Search extends Screen {

    private final String query;

    public Search(String query) {
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
