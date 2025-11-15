package src.ui.screens.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import src.App;
import src.api.APIClient;
import src.auth.AuthManager;
import src.ui.helper.*;
import src.ui.ColorManager;
import src.ui.screens._BaseScreen;
import src.ui.screens.auth.Login;

public class Settings extends _BaseScreen {
    public Settings() {
        super();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridBagLayout());
        bodyPanel.setBackground(ColorManager.bodyPrimary());
        bodyPanel.setBorder(new ResponsiveBorder(20, 100, 100, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 5;

        JLabel title = new JLabel("Einstellungen");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        title.setForeground(ColorManager.text());
        bodyPanel.add(title);

        JButton darkModeButton = new JButton("Dunkelmodus");
        JButton deleteDataButton = new JButton("Konto löschen");
        JButton creditsButton = new JButton("Mitwirkende und Copyright");
        JButton privacyButton = new JButton("Datenschutzerklärung");

        JComboBox<String> widgetSelect = new JComboBox<>();
        widgetSelect.addItem("Top 10 Fehler");
        widgetSelect.addItem("Leaderboard");
        widgetSelect.addItem("Letzte Lektionen");

        JLabel widgetLabel = new JLabel((String) widgetSelect.getSelectedItem());
        widgetLabel.setForeground(ColorManager.text());

        JComboBox<String> classSelect = new JComboBox<>();
        classSelect.addItem("Jahrgang 5");
        classSelect.addItem("Jahrgang 6");
        classSelect.addItem("Jahrgang 7");
        classSelect.addItem("Jahrgang 8");
        classSelect.addItem("Jahrgang 9");
        classSelect.addItem("Jahrgang 10");
        classSelect.addItem("Jahrgang 11");
        classSelect.addItem("Jahrgang 12");

        JLabel classLabel = new JLabel((String) classSelect.getSelectedItem());
        classLabel.setForeground(ColorManager.text());

        widgetSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                widgetLabel.setText((String) widgetSelect.getSelectedItem());
                APIClient.updatePreferences(Map.of("widget", widgetSelect.getSelectedIndex()+1));
            }
        });

        classSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classLabel.setText((String) classSelect.getSelectedItem());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JLabel widgetSelectLabel = new JLabel("Widget:");
        widgetSelectLabel.setForeground(ColorManager.text());
        widgetSelectLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20)); // Adjust the font here
        bodyPanel.add(widgetSelectLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        bodyPanel.add(widgetSelect, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JLabel classSelectLabel = new JLabel("Klasse:");
        classSelectLabel.setForeground(ColorManager.text());
        classSelectLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20)); // Adjust the font here
        bodyPanel.add(classSelectLabel, gbc);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 6;
        bodyPanel.add(classSelect, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        bodyPanel.add(darkModeButton, gbc);

        gbc.gridy = 2;
        bodyPanel.add(deleteDataButton, gbc);

        gbc.gridy = 3;
        bodyPanel.add(creditsButton, gbc);

        gbc.gridy = 4;
        bodyPanel.add(privacyButton, gbc);

        

        ImageIcon schiebereglerON, schiebereglerOFF;
        schiebereglerON = new ImageIcon("resources/images/Schieberegler_ON.png");
        schiebereglerOFF = new ImageIcon("resources/images/Schieberegler_OFF.png");

        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == darkModeButton) {
                    int newMode;
                    if (ColorManager.getMode() == 1) {
                        newMode = 2;
                        darkModeButton.setIcon(schiebereglerON);
                    } else {
                        newMode = 1;
                        darkModeButton.setIcon(schiebereglerOFF);
                    }
                    ColorManager.setMode(newMode);
                    APIClient.updatePreferences(Map.of("mode", newMode));
                    App.updateColors();

                } else if (e.getSource() == deleteDataButton) {
                    String password = JOptionPane.showInputDialog(null, "Enter Password:");
                    if (password != null) {
                        if (AuthManager.deleteAccount(password)) {
                            JOptionPane.showMessageDialog(null, "Account deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect password. Data deletion aborted.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Data deletion aborted.");
                    }
                } else if (e.getSource() == creditsButton) {
                    App.switchScreen(new Credits());

                } else if (e.getSource() == privacyButton) {
                    App.switchScreen(new PrivacyStatement());

                }
            }
        };

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(ColorManager.buttonHover());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(ColorManager.buttonDefault());
            }
        };

        JButton[] buttons = {darkModeButton, deleteDataButton, creditsButton, privacyButton};
        for (JButton button : buttons) {
            button.addActionListener(buttonActionListener);
            button.addMouseListener(mouseListener);
            button.setBackground(ColorManager.buttonDefault());
            button.setForeground(ColorManager.text());
            button.setFont(new Font(Font.SANS_SERIF, 0, 20));
        }
        widgetSelect.setBackground(ColorManager.buttonDefault());
        widgetSelect.setForeground(ColorManager.text());
        widgetSelect.setFont(new Font(Font.SANS_SERIF, 0, 20));

        classSelect.setBackground(ColorManager.buttonDefault());
        classSelect.setForeground(ColorManager.text());
        classSelect.setFont(new Font(Font.SANS_SERIF, 0, 20));

        if (ColorManager.getMode() == 1) {
            darkModeButton.setIcon(schiebereglerOFF);
        } else {
            darkModeButton.setIcon(schiebereglerON);
        }

        return bodyPanel;
    }
}