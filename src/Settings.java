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
        JButton deleteDataButton = new JButton("Konto löschen");
        JButton creditsButton = new JButton("Mitwirkende und Copyright");
        JButton privacyButton = new JButton("Datenschutzerklärung");
        
        JComboBox<String> widgetSelect = new JComboBox<>();
        widgetSelect.addItem("Top 10 Fehler");
        widgetSelect.addItem("Leaderboard");
        widgetSelect.addItem("Letzte Lektionen");
        
        
        bodyPanel.add(darkModeButton);
        bodyPanel.add(widgetSelect);
        bodyPanel.add(deleteDataButton);
        bodyPanel.add(creditsButton);
        bodyPanel.add(privacyButton);

        

        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == darkModeButton) {
                    main.toggleDarkmode();
                    bodyPanel.setBackground(Main.BodyColor);
                    main.getNavBar().updateDesign();
                    APIClient.updatePreferences(main.getDarkmodeState(), 0, 0);

                } else if (e.getSource() == deleteDataButton) {
                    String password = JOptionPane.showInputDialog(null, "Enter Password:");
                    if (password != null) {
                        boolean success = APIClient.deleteAccount(password);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Account deleted successfully.");
                            main.newSetup();
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect password. Data deletion aborted.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Data deletion aborted.");
                    }
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
