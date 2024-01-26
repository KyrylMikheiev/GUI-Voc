package src;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;


public class SettingsMenu {


    
    public SettingsMenu(JPanel content)
    {
        JPanel bodyPanel = new JPanel();

        Color defaultButton = Color.decode("#4d6190");
        Color hoverButton = Color.decode("#4255ff");
        Color clickButton = Color.decode("#2f3990");
        
        JLabel changeName = new JLabel("Name ändern");
        changeName.setFont(new Font("Arial Rounded MT", 0, 20));




        content.add(bodyPanel);
    }
}
