package src;

import javax.swing.JPanel;

public class NewLessonView {
    public NewLessonView(JPanel content) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Main.BodyColor);



        content.add(bodyPanel);
    }
}
