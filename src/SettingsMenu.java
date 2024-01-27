package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu {

    public SettingsMenu(JPanel content) {
        content.setLayout(new GridLayout(6, 2, 10, 10)); // GridLayout with 6 rows and 2 columns

        content.setBackground(Main.BodyColor);

        JLabel changeName = new JLabel("Name ändern");
        changeName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        changeName.setForeground(Main.TextColor);
        content.add(changeName);

        PlaceholderTextField textArea = new PlaceholderTextField("Max Mustermann", Color.GRAY);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        content.add(textArea);

        JLabel darkModeLabel = new JLabel("Dunkelmodus");
        JButton darkModeButton = new JButton("Toggle");
        content.add(darkModeLabel);
        content.add(darkModeButton);

        JLabel deleteDataLabel = new JLabel("Daten Löschen");
        JButton deleteDataButton = new JButton("Delete");
        content.add(deleteDataLabel);
        content.add(deleteDataButton);

        JButton creditsButton = new JButton("Mitwirkende und Copyright");
        content.add(creditsButton);

        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == darkModeButton) {
                    // Add dark mode toggle functionality
                } else if (e.getSource() == deleteDataButton) {
                    // Add delete data functionality
                } else if (e.getSource() == creditsButton) {
                    // Add credits functionality
                }
            }
        };

        darkModeButton.addActionListener(buttonActionListener);
        deleteDataButton.addActionListener(buttonActionListener);
        creditsButton.addActionListener(buttonActionListener);
    }
}
