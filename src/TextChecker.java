package src;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class TextChecker {
    public TextChecker(JPanel content) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(1, 2));
        bodyPanel.setBackground(Main.BodyColor);



        content.add(bodyPanel);
    }
}
