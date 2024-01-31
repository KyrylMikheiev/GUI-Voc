package src;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class SearchView {
    public SearchView(JPanel content, Main main) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout());
        bodyPanel.setBackground(Main.BodyColor);



        content.add(bodyPanel);
    }
}
