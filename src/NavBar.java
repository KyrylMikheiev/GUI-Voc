package src;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NavBar {

    private Color BLUE = Color.decode("#B7B7B7");

    public NavBar(JPanel content)
    {
        //------------------navigationBar----------------
        JPanel navigationBar = new JPanel();
        navigationBar.setBackground(BLUE);
        navigationBar.setPreferredSize(new Dimension(200, 80));
        navigationBar.setLayout(new GridLayout(1, 3));

        //---------------appName---------------
        JPanel navigation_contentLeft = new JPanel();
        navigation_contentLeft.setLayout(new BorderLayout(10, 0));
        navigation_contentLeft.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 30)); // adjustment for appname
        navigation_contentLeft.setOpaque(false);

        JLabel appName = new JLabel("AppName");
        appName.setFont(new Font("Times New Roman", 0, 28));
        
        JButton plusButton = new JButton();
        plusButton.setBorder(null);
        plusButton.setFocusPainted(false);
        plusButton.setBackground(BLUE);
        plusButton.setPreferredSize(new Dimension(70, 10));
        plusButton.setOpaque(false);
        ImageIcon plusIcon = new ImageIcon("resources/images/plus.png");
        Image plusImage = plusIcon.getImage();
        plusImage = plusImage.getScaledInstance(260, 200, Image.SCALE_SMOOTH);
        plusIcon = new ImageIcon(plusImage); 
        plusButton.setIcon(plusIcon);
        
        navigation_contentLeft.add(appName, BorderLayout.CENTER);
        navigation_contentLeft.add(plusButton, BorderLayout.EAST);
        
        //---------------textArea---------------
        JPanel navigation_contentMiddle = new JPanel();
        navigation_contentMiddle.setLayout(new BorderLayout());
        navigation_contentMiddle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        navigation_contentMiddle.setOpaque(false);
        navigation_contentMiddle.setBackground(Color.RED);

        JPanel searchBar = new JPanel();
        searchBar.setLayout(new BorderLayout(10, 0));
        // searchBar.setBorder(new PlaceholderBorder(40, Color.WHITE));
        searchBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        searchBar.setOpaque(false); 

        PlaceholderTextField textArea = new PlaceholderTextField("Schnellsuche...", Color.WHITE);
        textArea.setFont(new Font(Font.SANS_SERIF, 0, 18)); 
        textArea.setBorder(null);       
        textArea.setCaretColor(Color.WHITE);
        textArea.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(BLUE);  

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
        burgerMenu.setFont(new Font("Times New Roman", 0, 24));
        burgerMenu.setOpaque(false);
        burgerMenu.setBorder(null);
        burgerMenu.setIcon(burgerIcon);
        burgerMenu.setHorizontalAlignment(JMenu.CENTER);
        burgerMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JMenuItem learn = new JMenuItem("Lernen");
        JMenuItem library = new JMenuItem("Bibliothek");
        JMenuItem games = new JMenuItem("Minispiele");
        JMenuItem settings = new JMenuItem("Einstellungen");

        JMenuItem[] menuItems = {learn, library, games, settings};
        for (JMenuItem menuItem : menuItems) {
            burgerMenu.add(menuItem);
            menuItem.setFont(new Font("Unispace", 0, 20));
            setRightAlignment(menuItem);
        }

        menuBar.add(burgerMenu);
        navigation_contentRight.add(menuBar, BorderLayout.EAST);

        navigationBar.add(navigation_contentLeft);
        navigationBar.add(navigation_contentMiddle);
        navigationBar.add(navigation_contentRight);

        content.add(navigationBar, BorderLayout.NORTH);
    }
    // Custom method to set right alignment for JMenuItem
    private static void setRightAlignment(JMenuItem menuItem) {
        menuItem.setHorizontalAlignment(SwingConstants.RIGHT);
        menuItem.setHorizontalTextPosition(SwingConstants.RIGHT);
        menuItem.setBorder(new EmptyBorder(0, 0, 0, 0)); // Adjust the right padding as needed
    }
    
}
