package src.ui.screens.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.ui.ColorManager;
import src.ui.screens._BaseScreen;
import src.ui.helper.*;

public class Credits extends _BaseScreen {
    public Credits() {
        super();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(ColorManager.bodyPrimary());

        JLabel title = new JLabel("Ein Projekt des Informatik-Profils der Jgst. Abi 2025 im Auftrag von Herrn Grabbe und Herrn Faber");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        title.setForeground(ColorManager.text());

        JLabel team = new JLabel("Mitwirkende:");
        team.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        team.setForeground(ColorManager.text());
        
        JLabel people = new JLabel("Adrian Steyer, Jonah Emme, Kyryl Mikheiev, Noah Schwichtenberg, Sten Buhlnheim, Henri Haberlach, Bela Pautzke, Emily Probst, Nick Bohdan, Lukas Borck, Jacob Loszynski, Daniel Seeman");
        people.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        people.setForeground(ColorManager.text());
        
        
        JLabel icons = new JLabel("Icons von Flaticon:");
        icons.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        icons.setForeground(ColorManager.text());
        
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
        flaticons.setForeground(ColorManager.text());

        JLabel copyright = new JLabel("Lizenz");
        copyright.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        copyright.setForeground(ColorManager.text());

        JLabel copyrightText = new JLabel("<html>" + 
        "Diese Software ist lizensiert unter der Creative Commons Attribution 4.0 International Lizenz. (CC BY 4.0) ???????????????????????????????????????" + "</html>");
        copyrightText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        copyrightText.setForeground(ColorManager.text());
        
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

        return bodyPanel;
    }
}