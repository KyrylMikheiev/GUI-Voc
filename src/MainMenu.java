package src;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu {

    private GridBagConstraints gbc = new GridBagConstraints();
    private JButton learn, games, settings, widgets, library;

    public MainMenu(JPanel content)
    {
        // # create main menu
        //create elements and add them to the contentPane
        //----------------body-----------------
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.darkGray);        
        bodyPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        learn = new JButton("Lernen");
        games = new JButton("Minispiele");
        settings = new JButton("Einstellungen");
        widgets = new JButton("Widgets");
        library = new JButton("Library");

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Color.RED);
                //System.out.println("Entered");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(Color.YELLOW);
                //System.out.println("Exited");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                e.getComponent().setBackground(Color.GREEN);
                //System.out.println("Clicked");
            }
        };

        JButton[] buttons = {learn, games, settings, widgets, library};
        for(JButton button : buttons) {
            button.setBorder(null);
            button.setFont(new Font(Font.MONOSPACED, 0, 20));
            button.setFocusPainted(false);
            // button.setContentAreaFilled(false);
            button.setBackground(Color.YELLOW);
            button.addMouseListener(mouseListener);
            if (button != settings) {
                button.setPreferredSize(new Dimension(100, 100));
            } else {
                button.setPreferredSize(new Dimension(200, 100));
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        bodyPanel.add(learn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        bodyPanel.add(games, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.gridwidth = 2;
        widgets.setPreferredSize(new Dimension(150, 200));
        bodyPanel.add(widgets, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        bodyPanel.add(library, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        bodyPanel.add(settings, gbc);

        content.add(bodyPanel, BorderLayout.CENTER);

    }
}
