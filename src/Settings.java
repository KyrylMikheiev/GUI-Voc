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
        bodyPanel.setLayout(new GridBagLayout());
        bodyPanel.setBackground(Main.BodyColor);
        bodyPanel.setBorder(new ResponsiveBorder(20, 100, 100, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 5;

        JLabel title = new JLabel("Einstellungen");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        title.setForeground(Main.TextColor);
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
        widgetLabel.setForeground(Main.TextColor);

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
        classLabel.setForeground(Main.TextColor);

        widgetSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                widgetLabel.setText((String) widgetSelect.getSelectedItem());
                main.setWidget(widgetSelect.getSelectedIndex());
                main.resetHistory();
                int dm;
                if (main.getDarkmodeState()) {
                    dm = 2;
                } else {
                    dm = 1;
                }
                APIClient.updatePreferences(dm, 0, widgetSelect.getSelectedIndex());
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
        widgetSelectLabel.setForeground(Main.TextColor);
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
        classSelectLabel.setForeground(Main.TextColor);
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
                    classSelect.setBackground(Main.DefaultButton);
                    classSelect.setForeground(Main.TextColor);
                    classSelectLabel.setBackground(Main.DefaultButton);
                    classSelectLabel.setForeground(Main.TextColor);
                    widgetSelectLabel.setBackground(Main.DefaultButton);
                    widgetSelectLabel.setForeground(Main.TextColor);
                    title.setBackground(Main.DefaultButton);
                    title.setForeground(Main.TextColor);
                    main.getNavBar().updateDesign();
                    main.resetHistory();
                    int dm;
                    if (main.getDarkmodeState()) {
                        dm = 2;
                    } else {
                        dm = 1;
                    }
                    APIClient.updatePreferences(dm, 1, widgetSelect.getSelectedIndex());

                } else if (e.getSource() == deleteDataButton) {
                    String password = JOptionPane.showInputDialog(null, "Enter Password:");
                    if (password != null) {
                        boolean success = APIClient.deleteAccount(password);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Account deleted successfully.");
                            main.newSetup();
                            main.resetHistory();
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect password. Data deletion aborted.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Data deletion aborted.");
                    }
                } else if (e.getSource() == creditsButton) {
                    main.newCredits();

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

        classSelect.setBackground(Main.DefaultButton);
        classSelect.setForeground(Main.TextColor);
        classSelect.setFont(new Font(Font.SANS_SERIF, 0, 20));

        if (main.getDarkmodeState()) {
            darkModeButton.setIcon(schiebereglerON);
        } else {
            darkModeButton.setIcon(schiebereglerOFF);
        }
        content.add(bodyPanel);
    }
}
