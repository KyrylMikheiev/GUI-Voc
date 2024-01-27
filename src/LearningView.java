package src;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class LearningView {
    

    public LearningView(JPanel content) {
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);

        //communicate with vocab api (sql?)

        content.add(bodyPanel);
    }
}
