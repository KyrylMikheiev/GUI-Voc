package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings {

    public Settings(JPanel content, Main main) {
        
        JPanel bodyPanel = new JPanel();
        
        bodyPanel.setLayout(new GridLayout(6, 2, 10, 10)); // GridLayout with 6 rows and 2 columns

        bodyPanel.setBackground(Main.BodyColor);

        JLabel changeName = new JLabel("Name ändern");
        changeName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        changeName.setForeground(Main.TextColor);
        bodyPanel.add(changeName);

        PlaceholderTextField textArea = new PlaceholderTextField("Max Mustermann", Color.GRAY);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        bodyPanel.add(textArea);

        JLabel darkModeLabel = new JLabel("Dunkelmodus");
        JButton darkModeButton = new JButton("Toggle");
        darkModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add dark mode toggle functionality
                main.toggleDarkmode();
                bodyPanel.setBackground(Main.BodyColor);
            }
        });
        bodyPanel.add(darkModeLabel);
        bodyPanel.add(darkModeButton);

        JLabel deleteDataLabel = new JLabel("Daten Löschen");
        JButton deleteDataButton = new JButton("Delete");
        bodyPanel.add(deleteDataLabel);
        bodyPanel.add(deleteDataButton);

        JButton creditsButton = new JButton("Mitwirkende und Copyright");
        bodyPanel.add(creditsButton);

        JButton privacyButton = new JButton("Datenschutzerklärung");
        bodyPanel.add(privacyButton);

        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == darkModeButton) {
                    // Add dark mode toggle functionality
                } else if (e.getSource() == deleteDataButton) {
                    // Add delete data functionality
                } else if (e.getSource() == creditsButton) {
                    main.newCredits(content);
                } else if (e.getSource() == privacyButton) {
                    main.newPrivacyStatement();
                }
            }
        };

        darkModeButton.addActionListener(buttonActionListener);
        deleteDataButton.addActionListener(buttonActionListener);
        creditsButton.addActionListener(buttonActionListener);
        privacyButton.addActionListener(buttonActionListener);

        content.add(bodyPanel);
    }
}
