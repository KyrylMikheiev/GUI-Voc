package src;

import javax.swing.*;

import restAPI.APIClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Settings {

    public Settings(JPanel content, Main main) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(6, 2, 10, 10)); // GridLayout with 6 rows and 2 columns
        bodyPanel.setBackground(Main.BodyColor);
        bodyPanel.setBorder(new ResponsiveBorder(100, 100, 20, 100));

        JButton darkModeButton = new JButton("Dunkelmodus");
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

        ImageIcon schiebereglerON,schiebereglerOFF;
        schiebereglerON = new ImageIcon("resources/images/Schieberegler_ON.png");
        schiebereglerOFF = new ImageIcon("resources/images/Schieberegler_OFF.png");

        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == darkModeButton) {
                    main.toggleDarkmode();
                    if (main.getDarkmodeState()) {
                        darkModeButton.setIcon(schiebereglerON);
                    } else {
                        darkModeButton.setIcon(schiebereglerOFF);
                    }
                    bodyPanel.setBackground(Main.BodyColor);
                    JButton[] buttons = {darkModeButton, deleteDataButton, creditsButton, privacyButton};
                    for (JButton button : buttons) {
                        button.setBackground(Main.DefaultButton);
                        button.setForeground(Main.TextColor);
                    }
                    widgetSelect.setBackground(Main.DefaultButton);
                    widgetSelect.setForeground(Main.TextColor);                        
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

MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Main.HoverButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(Main.DefaultButton);
            }
        };

        JButton[] buttons = {darkModeButton, deleteDataButton, creditsButton, privacyButton};
        for (JButton button : buttons) {
            button.addActionListener(buttonActionListener);
            button.addMouseListener(mouseListener);
            button.setBackground(Main.DefaultButton);
            button.setForeground(Main.TextColor);
            button.setFont(new Font(Font.SANS_SERIF, 0, 20));        
        }   
        widgetSelect.setBackground(Main.DefaultButton);
        widgetSelect.setForeground(Main.TextColor);       
        widgetSelect.setFont(new Font(Font.SANS_SERIF, 0, 20));
        if (main.getDarkmodeState()) {
            darkModeButton.setIcon(schiebereglerON);
        } else {
            darkModeButton.setIcon(schiebereglerOFF);
        }
        content.add(bodyPanel);
    }
}
