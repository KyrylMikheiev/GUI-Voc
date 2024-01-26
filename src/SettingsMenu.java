package src;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class SettingsMenu {


    
    public SettingsMenu(JPanel content)
    {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Main.BodyColor);
        
        JLabel changeName = new JLabel("Name ändern");
        changeName.setFont(new Font(Font.SANS_SERIF, 0, 20));
        changeName.setForeground(Main.TextColor);
        bodyPanel.add(changeName);

        content.add(bodyPanel);
    }
}
