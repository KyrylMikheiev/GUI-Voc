package src.ui.screens;

import javax.swing.JPanel;

import src.ui.screens._BaseScreen;

public class Search extends _BaseScreen {
    private String query;

    public Search(String query) {
        super(false);
        this.query = query;
        this.rebuildUI();
    }

    @Override
    public JPanel createUI() {
        // TODO: Implement
        return new JPanel();
    }
}