package src;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu {
    
    

    private JButton learn, games, settings, library, exit;

    public MainMenu(JPanel content, Main main)
    {
        // # create main menu
        //create elements and add them to the contentPane
        //----------------body-----------------

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(1, 2, 0, 0));
        bodyPanel.setBackground(Main.BodyColor);

        JPanel body_contentLeft = new JPanel();
        body_contentLeft.setOpaque(false);
        body_contentLeft.setBorder(BorderFactory.createEmptyBorder(100, 80, 100, 80));       
        body_contentLeft.setLayout(new GridLayout(4, 1, 0, main.getFrame().getHeight() / 30));

        learn = new JButton("Lernen");
        library = new JButton("Bibliothek");
        games = new JButton("Minispiele");
        settings = new JButton("Einstellungen");
        // exit = new JButton("Beenden");


        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Main.hoverButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(Main.defaultButton);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    e.getComponent().setBackground(Main.clickButton);
                    //do something
                    if (e.getComponent() == learn) {
                        main.newLearnMenu();
                    }
                    else if (e.getComponent() == library) {
                        main.newLibraryMenu();
                    }
                    else if (e.getComponent() == games) {
                        main.newGamesMenu();
                    }
                    else if (e.getComponent() == settings) {
                        //remove mainMenu and start settings
                        main.newSettingsMenu();
                    }
                    else if (e.getComponent() == exit) {
                        System.exit(0);
                    }
                });
            }

        };

        JButton[] buttons = {learn, library, games, settings};
        for(JButton button : buttons) {
            button.setBorder(null);
            button.setFont(new Font(Font.SANS_SERIF, 0, 20));
            button.setFocusPainted(false);
            button.setLayout(new BorderLayout());
            button.setBackground(Main.defaultButton);
            button.setForeground(Main.TextColor);
            button.addMouseListener(mouseListener);
            body_contentLeft.add(button);
        }
        
        JPanel bodyPanel_contentRight = new JPanel();
        bodyPanel_contentRight.setOpaque(false);
        bodyPanel_contentRight.setBorder(BorderFactory.createEmptyBorder(100, 30, 100, 80));
        bodyPanel_contentRight.setLayout(new BorderLayout());

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.setOpaque(false);
        textAreaPanel.setBorder(BorderFactory.createLineBorder(Main.TextColor, 3));

        JTextArea textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setEditable(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setFont(new Font(Font.SANS_SERIF, 0, 25));
        textArea.setForeground(Main.TextColor);
        textArea.setText("Hallo!");

        textAreaPanel.add(textArea, BorderLayout.CENTER);
        bodyPanel_contentRight.add(textAreaPanel, BorderLayout.CENTER);
        
        bodyPanel.add(body_contentLeft);
        bodyPanel.add(bodyPanel_contentRight);

        content.add(bodyPanel, BorderLayout.CENTER);

    }
}
