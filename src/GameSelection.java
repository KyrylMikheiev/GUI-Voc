package src;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameSelection {
    public GameSelection(JPanel content) {
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);

        JButton memory = new JButton("Memory");
        memory.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        bodyPanel.add(memory);
        

        content.add(bodyPanel);
    }
}
