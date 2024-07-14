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

        JLabel title = new JLabel("Ein Projekt des Informatik-Profils der Jgst. Abi 2025 im Auftrag von Herrn Grabbe und Herrn Faber");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        title.setForeground(Main.TextColor);

        JLabel team = new JLabel("Mitwirkende:");
        team.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        team.setForeground(Main.TextColor);
        
        JLabel people = new JLabel("Adrian Steyer, Jonah Emme, Kyryl Mikheiev, Noah Schwichtenberg, Sten Buhlnheim, Henri Haberlach, Bela Pautzke, Emily Probst, Nick Bohdan, Lukas Borck, Jacob Loszynski, Daniel Seeman");
        people.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        people.setForeground(Main.TextColor);
        
        
        JLabel icons = new JLabel("Icons von Flaticon:");
        icons.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
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

        JLabel copyright = new JLabel("Lizenz");
        copyright.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        copyright.setForeground(Main.TextColor);

        JLabel copyrightText = new JLabel("<html>" + 
        "Diese Software ist lizensiert unter der Creative Commons Attribution 4.0 International Lizenz. (CC BY 4.0) ???????????????????????????????????????" + "</html>");
        copyrightText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        copyrightText.setForeground(Main.TextColor);
        
        //apply margin to everything
        JLabel[] labels = {title, people, icons, flaticons, team, copyright, copyrightText};
        for (JLabel label : labels) {
            label.setBorder(new ResponsiveBorder(10, 10, 10, 10));
        }

        bodyPanel.add(title);
        bodyPanel.add(team);
        bodyPanel.add(people);
        bodyPanel.add(icons);
        bodyPanel.add(flaticons);
        bodyPanel.add(copyright);
        bodyPanel.add(copyrightText);

        content.add(bodyPanel);
    }
}
