package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main { 
    public Main() {

        JPanel navBar = new JPanel();
        navBar.setBackground(Color.decode("#FFFF00"));
        navBar.setPreferredSize(new Dimension(20, 100));
        navBar.setLayout(new BorderLayout());

        JPanel appNamePanel = new JPanel();
        appNamePanel.setPreferredSize(new Dimension(200, 200));
        appNamePanel.setLayout(new BorderLayout());

        JLabel appName = new JLabel("AppName");
        appName.setFont(new java.awt.Font("Times New Roman", 0, 28));
        appName.setHorizontalAlignment(SwingConstants.CENTER);
        appNamePanel.add(appName, BorderLayout.CENTER);

        PlaceholderTextField textArea = new PlaceholderTextField("Search");
        textArea.setFont(new java.awt.Font(Font.SANS_SERIF, 0, 28));
        textArea.setBorder(null);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.BLUE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(100, 100));
        menuBar.setLayout(new GridLayout());

        JMenu burgerMenu = new JMenu("Burger");

        JMenuItem cheese = new JMenuItem("Cheese");
        JMenuItem bacon = new JMenuItem("Bacon");
        JMenuItem lettuce = new JMenuItem("Lettuce");
        burgerMenu.add(lettuce);
        burgerMenu.add(bacon);
        burgerMenu.add(cheese);
        menuBar.add(burgerMenu);

        navBar.add(appNamePanel, BorderLayout.WEST);
        navBar.add(textArea, BorderLayout.CENTER);
        navBar.add(menuBar, BorderLayout.EAST);	

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.darkGray);        
        bodyPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        JButton learn = new JButton("Learn");
        JButton games = new JButton("Games");
        JButton widgets = new JButton("Widgets");
        JButton settings = new JButton("Settings");
        JButton library = new JButton("Library");

        JButton[] buttons = {learn, games, widgets, settings, library};
        for(JButton button : buttons) {
            button.setBackground(Color.decode("#FFFF00"));
            button.setBorder(null);
            button.setFont(new Font(Font.MONOSPACED, 0, 20));
            button.setFocusPainted(false);
            if (button != widgets) {
                button.setPreferredSize(new Dimension(100, 100));
            } else {
                button.setPreferredSize(new Dimension(200, 100));
            }
        }
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        bodyPanel.add(learn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        bodyPanel.add(games, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        bodyPanel.add(settings, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        bodyPanel.add(library, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        bodyPanel.add(widgets, gbc);
        

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(navBar, BorderLayout.NORTH);
        contentPane.add(bodyPanel, BorderLayout.CENTER);

        JFrame frame = new JFrame();
        frame.setSize(1300, 900);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setVisible(true);

        // create main menu and run it inside the window
        // MainMenu menu = new MainMenu();
        // menu.run(contentPane);

    }

    public static void main(String[] args) {
        new Main();
    }
}