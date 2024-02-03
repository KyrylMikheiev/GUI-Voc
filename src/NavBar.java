package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NavBar {

    private Color GRAY = Color.decode("#374151");

    public NavBar(JPanel globalPanel, Main main)
    {
        //------------------navigationBar----------------
        JPanel navigationBar = new JPanel();
        navigationBar.setBackground(GRAY);
        navigationBar.setPreferredSize(new Dimension(200, 80));
        navigationBar.setLayout(new GridLayout(1, 3));

        //---------------appName---------------
        JPanel navigation_contentLeft = new JPanel();
        navigation_contentLeft.setLayout(new BorderLayout(10, 0));
        navigation_contentLeft.setBorder(BorderFactory.createEmptyBorder(9, 0, 9, 30)); // adjustment for appname
        navigation_contentLeft.setOpaque(false);


        JLabel appName = new JLabel("Vokabeltrainer");
        appName.setFont(new Font("Times New Roman", 0, 28));
        appName.setOpaque(false);
        Image logoImage = new ImageIcon("resources/images/logo.png").getImage().getScaledInstance(100, 100, 0);      
        appName.setIcon(new ImageIcon(logoImage));
        appName.setForeground(Color.WHITE);
        
        JButton plusButton = new JButton();
        plusButton.setBorder(null);
        plusButton.setFocusPainted(false);
        plusButton.setBackground(GRAY);
        plusButton.setPreferredSize(new Dimension(60, 10));
        plusButton.setOpaque(false);
        ImageIcon plusIcon = new ImageIcon("resources/images/plus.png");
        Image plusImage = plusIcon.getImage();
        plusImage = plusImage.getScaledInstance(260, 200, Image.SCALE_SMOOTH);
        plusIcon = new ImageIcon(plusImage); 
        plusButton.setIcon(plusIcon);
        
        navigation_contentLeft.add(plusButton, BorderLayout.EAST);
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.newMainMenu();
            }
        };
        appName.addMouseListener(mouseListener);
        appName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navigation_contentLeft.add(appName, BorderLayout.CENTER);
        
        //---------------textArea---------------
        JPanel navigation_contentMiddle = new JPanel();
        navigation_contentMiddle.setLayout(new BorderLayout());
        navigation_contentMiddle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        navigation_contentMiddle.setOpaque(false);
        navigation_contentMiddle.setBackground(Color.RED);

        JPanel searchBar = new JPanel();
        searchBar.setLayout(new BorderLayout(10, 0));
        searchBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        searchBar.setOpaque(false); 

        PlaceholderTextField textArea = new PlaceholderTextField("Schnellsuche...", Color.GRAY);
        textArea.setFont(new Font(Font.SANS_SERIF, 0, 18));
        textArea.setBorder(null);       
        textArea.setCaretColor(Color.WHITE);
        textArea.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(GRAY);  

        JLabel searchLabel = new JLabel();
        ImageIcon searchIcon = new ImageIcon("resources/images/search.png");
        Image searchImage = searchIcon.getImage();
        searchImage = searchImage.getScaledInstance(25, 20, Image.SCALE_SMOOTH);
        searchIcon = new ImageIcon(searchImage);
        searchLabel.setIcon(searchIcon);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        searchBar.add(searchLabel, BorderLayout.WEST);
        searchBar.add(textArea, BorderLayout.CENTER);
        navigation_contentMiddle.add(searchBar);

        //------------------menu----------------
        JPanel navigation_contentRight = new JPanel();
        navigation_contentRight.setLayout(new BorderLayout());
        navigation_contentRight.setOpaque(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(false);
        menuBar.setBorder(null);

        ImageIcon burgerIcon = new ImageIcon("resources/images/burgerMenuIcon.png");
        Image burgerImage = burgerIcon.getImage();
        burgerImage = burgerImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        burgerIcon = new ImageIcon(burgerImage);

        JMenu burgerMenu = new JMenu();
        burgerMenu.setIcon(burgerIcon);
        burgerMenu.setHorizontalAlignment(JMenu.RIGHT);
        burgerMenu.setPreferredSize(new Dimension(90, 100));
        burgerMenu.setFont(new Font(Font.SANS_SERIF, 0, 24));
        burgerMenu.setOpaque(false);
        burgerMenu.setBorder(null);
        burgerMenu.setIcon(burgerIcon);
        burgerMenu.setHorizontalAlignment(JMenu.CENTER);
        burgerMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        burgerMenu.setForeground(Main.BodyColor);

        JMenuItem mainmenu = new JMenuItem("Hauptmenü");
        JMenuItem learn = new JMenuItem("Lernen");
        JMenuItem test = new JMenuItem("Testen");
        JMenuItem library = new JMenuItem("Bibliothek");
        JMenuItem text = new JMenuItem("Textchecker");
        JMenuItem games = new JMenuItem("Minispiele");
        JMenuItem settings = new JMenuItem("Einstellungen");
        JMenuItem exit = new JMenuItem("Beenden");

        // MouseListener for hovering and unhovering menuItems
        MouseListener menuMouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // e.getComponent().setBackground(Main.hoverButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // e.getComponent().setBackground(Main.defaultButton);
            }
        };
        //actionLister bc mouseListener can't detect clicks of menuItems for some reason
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource(); // Identify the source of the event
                if (source == mainmenu) {
                    main.newMainMenu();
                } else if (source == learn) {
                    main.newLearningSelection();
                } else if (source == library) {
                    main.newLibraryMenu();
                } else if (source == games) {
                    main.newGamesMenu();
                } else if (source == settings) {
                    main.newSettingsMenu();
                } else if (source == exit) {
                    System.exit(0);
                } else if (source == test) {
                    main.newTestSelection();
                } else if (source == text) {
                    main.newTextChecker();
                }
            }
        };

        JMenuItem[] menuItems = {mainmenu, learn, test, library, text, games, settings, exit};
        for (JMenuItem menuItem : menuItems) {
            burgerMenu.add(menuItem);
            menuItem.setFont(new Font(Font.SANS_SERIF, 0, 20));
            setRightAlignment(menuItem);
            //menuItem.setBackground(Main.defaultButton);
            //menuItem.setForeground(Color.WHITE);
            menuItem.addMouseListener(menuMouseListener);
            menuItem.addActionListener(actionListener);
        }

        menuBar.add(burgerMenu);
        navigation_contentRight.add(menuBar, BorderLayout.EAST);

        navigationBar.add(navigation_contentLeft);
        navigationBar.add(navigation_contentMiddle);
        navigationBar.add(navigation_contentRight);

        globalPanel.add(navigationBar, BorderLayout.NORTH);
    }
    // Custom method to set right alignment for JMenuItem
    private static void setRightAlignment(JMenuItem menuItem) {
        menuItem.setHorizontalAlignment(SwingConstants.RIGHT);
        menuItem.setHorizontalTextPosition(SwingConstants.RIGHT);
        menuItem.setBorder(new EmptyBorder(0, 0, 0, 0)); // Adjust the right padding as needed
    }
    
}
