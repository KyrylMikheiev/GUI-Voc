package src.ui.screens;

import src.ui.ColorManager;

import javax.swing.*;
import java.awt.BorderLayout;

public abstract class _BaseScreen {
    protected JPanel contentPanel;
    protected boolean keepInHistory = true;
    protected boolean hasNavbar = true;

    public _BaseScreen() {
        this.contentPanel = new JPanel(new BorderLayout());
        this.rebuildUI();
    }

    public _BaseScreen(boolean directSetup) {
        this.contentPanel = new JPanel(new BorderLayout());
        if (directSetup) {
            this.rebuildUI();
        }
    }

    public void setKeepInHistory(boolean val) {
        keepInHistory = val;
    }

    public boolean storeInHistory()  {
        return keepInHistory;
    }

    public void setHasNavbar(boolean val) {
        hasNavbar = val;
    }

    public boolean hasNavbar() {
        return hasNavbar;
    }

    public JPanel getContentPanel() {
        return this.contentPanel;
    }

    public void rebuildUI() {
        this.clearBasePanel();
        this.contentPanel.add(this.createUI());
        this.repaint();
    }

    protected void clearBasePanel() {
        this.contentPanel.removeAll();
        this.contentPanel.setBackground(ColorManager.bodyPrimary());
    }

    protected void repaint() {
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }

    protected JPanel createUI() {
        return new JPanel();
    }
}