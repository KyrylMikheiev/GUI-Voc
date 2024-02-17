package src;

import javax.swing.JPanel;

public class CustomLessonView {
    public CustomLessonView(JPanel content, Main main) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Main.BodyColor);



        content.add(bodyPanel);
    }
}
