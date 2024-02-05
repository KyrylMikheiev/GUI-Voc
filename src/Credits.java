package src;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Credits {
    public Credits(JPanel content) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(Main.BodyColor);

        JLabel title = new JLabel("Mitwirkende:");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        title.setForeground(Main.TextColor);
        
        JLabel people = new JLabel("Kyryl Mikheiev, Jonah Emme, Adrian Steyer");
        people.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        people.setForeground(Main.TextColor);
        
        
        JLabel icons = new JLabel("Icons von Flaticon:");
        icons.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        icons.setForeground(Main.TextColor);
        
        JLabel flaticons = new JLabel("<html>" + 
        "https://www.flaticon.com/free-icons/card-index" + "<br/>" +
        "https://www.flaticon.com/free-icons/library" + "<br/>" +
        "https://www.flaticon.com/free-icons/exam-results" + "<br/>" +
        "https://www.flaticon.com/free-icons/text" + "<br/>" +
        "https://www.flaticon.com/free-icons/gamepad" + "<br/>" +
        "https://www.flaticon.com/free-icons/setting" + "<br/>" +
        "https://www.flaticon.com/free-icons/sun" +
        "</html>");
        flaticons.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        flaticons.setForeground(Main.TextColor);
        
        //apply margin to everything
        JLabel[] labels = {title, people, icons, flaticons};
        for (JLabel label : labels) {
            label.setBorder(new ResponsiveBorder(10, 10, 10, 10));
        }

        bodyPanel.add(title);
        bodyPanel.add(people);
        bodyPanel.add(icons);
        bodyPanel.add(flaticons);

        content.add(bodyPanel);
    }
}
