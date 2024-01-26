package src;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu {
    
    //button colors
    Color defaultButton = Color.decode("#4d6190");
    Color hoverButton = Color.decode("#4255ff");
    Color clickButton = Color.decode("#2f3990");
    Color darkModeBodyColor = Color.decode("#111827");
    private JButton learn, games, settings, widgets, library;

    public MainMenu(JPanel content)
    {
        // # create main menu
        //create elements and add them to the contentPane
        //----------------body-----------------

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(1, 2, 0, 0));
        bodyPanel.setBackground(darkModeBodyColor);

        JPanel body_contentLeft = new JPanel();
        body_contentLeft.setOpaque(false);
        body_contentLeft.setBorder(BorderFactory.createEmptyBorder(100, 80, 100, 80));       
        body_contentLeft.setLayout(new GridLayout(5, 1, 0, 20));

        learn = new JButton("Lernen");     
        library = new JButton("Bibliothek");
        games = new JButton("Minispiele");
        settings = new JButton("Einstellungen");


        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(hoverButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(defaultButton);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    e.getComponent().setBackground(clickButton);
                    //do something
                    if (e.getComponent() == learn) {
                        System.out.println("learn clicked");
                    }
                    else if (e.getComponent() == library) {
                        System.out.println("library clicked");
                    }
                    else if (e.getComponent() == games) {
                        System.out.println("games clicked");
                    }
                    else if (e.getComponent() == settings) {
                        //remove mainMenu and start settings
                        Main.newUI(content);
                        new SettingsMenu(content);
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
            button.setBackground(defaultButton);
            button.setForeground(Color.WHITE);
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
        textAreaPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        JTextArea textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setEditable(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setFont(new Font(Font.SANS_SERIF, 0, 25));
        textArea.setForeground(Color.WHITE);
        textArea.setText("Hallo!");

        textAreaPanel.add(textArea, BorderLayout.CENTER);
        bodyPanel_contentRight.add(textAreaPanel, BorderLayout.CENTER);
        
        bodyPanel.add(body_contentLeft);
        bodyPanel.add(bodyPanel_contentRight);

        content.add(bodyPanel, BorderLayout.CENTER);

    }
}
