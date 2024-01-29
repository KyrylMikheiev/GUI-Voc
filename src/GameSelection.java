package src;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GameSelection {
    public GameSelection(JPanel content) {
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);
        content.add(bodyPanel);
    }
}
