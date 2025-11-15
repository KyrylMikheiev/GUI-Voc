package src.ui.screens.games;

import javax.swing.*;
import java.awt.*;

import src.App;
import src.ui.ColorManager;
import src.ui.screens._BaseScreen;
import src.ui.screens.games.memory.MemorySelection;

public class GameSelection extends _BaseScreen {

    public GameSelection() {
        super();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(ColorManager.bodyPrimary());

        JButton memory = new JButton("Memory");
        memory.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        memory.addActionListener(e -> {
            App.switchScreen(new MemorySelection());
        });
        bodyPanel.add(memory);

        return bodyPanel;
    }
}