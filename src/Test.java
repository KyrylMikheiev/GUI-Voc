package src;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Test {
    public Test(JPanel content, String lesson, int type) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(1, 2));
        bodyPanel.setBackground(Main.BodyColor);



        content.add(bodyPanel);
    }
}
