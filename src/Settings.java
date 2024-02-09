package src;

import javax.swing.*;

import restAPI.APIClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings {

    public Settings(JPanel content, Main main) {
        
        JPanel bodyPanel = new JPanel();
        
        bodyPanel.setLayout(new GridLayout(6, 2, 10, 10)); // GridLayout with 6 rows and 2 columns

        bodyPanel.setBackground(Main.BodyColor);

        JButton darkModeButton = new JButton("Dunkelmodus an/aus");
        bodyPanel.add(darkModeButton);

        JButton deleteDataButton = new JButton("Konto löschen");
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
                    main.toggleDarkmode();
                    bodyPanel.setBackground(Main.BodyColor);
                    APIClient.updatePreferences(main.getDarkmodeState(), 0, 0);

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
