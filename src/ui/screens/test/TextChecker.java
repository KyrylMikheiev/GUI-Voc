package src.ui.screens.test;

import javax.swing.*;
import java.awt.*;

import src.ui.ColorManager;
import src.ui.screens._BaseScreen;

public class TextChecker extends _BaseScreen {

    public TextChecker() {
        super();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(1, 2));
        bodyPanel.setBackground(ColorManager.bodyPrimary());
        return bodyPanel;
    }
}