package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import restAPI.APIClient;

public class NavBar {

    private Color DEFAULT_BUTTON_COLOR = Main.DefaultButton;
    private Color HOVER_BUTTON_COLOR = Main.BodyColor;
    private Color CLICK_BUTTON_COLOR = Main.ClickButton;
    private Color BODY_COLOR = Color.GRAY;
    private Color SECOND_BODY_COLOR = Main.SecondBodyColor;
    private Color TEXT_COLOR = Main.TextColor;

    private boolean active = false;
    private JPanel globalPanel;
    private Main main;

    // Navigation bar components
    private JPanel navigationBar;
    private JPanel searchBar;
    private PlaceholderTextField textArea;
    private JLabel appName;
    private JMenu burgerMenu;

    public NavBar(JPanel globalPanel, Main main) {
        this.globalPanel = globalPanel;
        this.main = main;
        initComponents();
    }

    private void initComponents() {
        //------------------navigationBar----------------
        navigationBar = new JPanel();
        navigationBar.setBackground(BODY_COLOR);
        navigationBar.setPreferredSize(new Dimension(200, 80));
        navigationBar.setLayout(new GridLayout(1, 3));

        JPanel navigation_contentLeft = new JPanel();
        navigation_contentLeft.setLayout(new BorderLayout(10, 0));
        navigation_contentLeft.setBorder(BorderFactory.createEmptyBorder(9, 0, 9, 30)); // adjustment for appname
        navigation_contentLeft.setOpaque(false);
        //---------------backButton---------------
        JButton backButton = new JButton();
        ImageIcon backIcon = new ImageIcon("resources/images/arrow.png");
        backIcon.setImage(backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        backButton.setIcon(backIcon);
        backButton.setOpaque(false);
        backButton.setBackground(BODY_COLOR);
        backButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (active) {
                    main.goBack();
                }
            }
        });
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(new ResponsiveBorder(10, 100, 10, 100));
        navigation_contentLeft.add(backButton, BorderLayout.WEST);

        //---------------appName---------------
        appName = new JLabel("Vokabeltrainer");
        appName.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        appName.setOpaque(false);
        ImageIcon logoIcon = new ImageIcon("resources/images/logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        appName.setIcon(new ImageIcon(logoImage));
        appName.setForeground(TEXT_COLOR);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (active) {
                    main.newMainMenu();
                }
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
        navigation_contentMiddle.setBackground(Color.RED); // This seems unnecessary, as it's being overlaid

        searchBar = new JPanel();
        searchBar.setLayout(new BorderLayout(10, 0));
        searchBar.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 2));
        searchBar.setOpaque(false);

        textArea = new PlaceholderTextField("Schnellsuche...", Color.decode("#E0E0E0"));
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        textArea.setBorder(null);
        textArea.setCaretColor(TEXT_COLOR);
        textArea.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        textArea.setForeground(TEXT_COLOR);
        textArea.setBackground(BODY_COLOR);
        textArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (active) {
                    main.newSearchView(textArea.getText());
                }
            }
        });

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

        burgerMenu = new JMenu();
        burgerMenu.setIcon(burgerIcon);
        burgerMenu.setHorizontalAlignment(JMenu.RIGHT);
        burgerMenu.setPreferredSize(new Dimension(90, 100));
        burgerMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        burgerMenu.setOpaque(false);
        burgerMenu.setBorder(null);
        burgerMenu.setIcon(burgerIcon);
        burgerMenu.setHorizontalAlignment(JMenu.CENTER);
        burgerMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        burgerMenu.setForeground(TEXT_COLOR);

        JMenuItem mainmenu = new JMenuItem("Hauptmenü");
        JMenuItem settings = new JMenuItem("Einstellungen");
        JMenuItem logout = new JMenuItem("Abmelden");
        JMenuItem exit = new JMenuItem("Beenden");

        // MouseListener for hovering and unhovering menuItems
        MouseListener menuMouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // e.getComponent().setBackground(HOVER_BUTTON_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // e.getComponent().setBackground(DEFAULT_BUTTON_COLOR);
            }
        };
        //actionLister bc mouseListener can't detect clicks of menuItems for some reason
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource(); // Identify the source of the event
                if (source == mainmenu) {
                    main.newMainMenu();
                } else if (source == settings) {
                    main.newSettingsMenu();
                } else if (source == exit) {
                    System.exit(0);
                } else if (source == logout) {
                    APIClient.logout();
                    main.newSetup();
                }
            }
        };

        JMenuItem[] menuItems = {mainmenu, settings, logout, exit};
        for (JMenuItem menuItem : menuItems) {
            burgerMenu.add(menuItem);
            menuItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            setRightAlignment(menuItem);
            //menuItem.setBackground(DEFAULT_BUTTON_COLOR);
            //menuItem.setForeground(TEXT_COLOR);
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

    public void activate() {
        active = true;
        // Enable all components
        textArea.setEnabled(true);
        burgerMenu.setEnabled(true);
        appName.setEnabled(true);
    }
    
    public void deactivate() {
        active = false;
        // Disable all components
        textArea.setEnabled(false);
        burgerMenu.setEnabled(false);
        appName.setEnabled(false);
    }
    

    public boolean isActive() {
        return active;
    }

    public void updateDesign() {
        // Update colors
        DEFAULT_BUTTON_COLOR = Main.DefaultButton;
        HOVER_BUTTON_COLOR = Main.HoverButton;
        CLICK_BUTTON_COLOR = Main.ClickButton;
        //BODY_COLOR = Main.BodyColor;
        SECOND_BODY_COLOR = Main.SecondBodyColor;
        TEXT_COLOR = Main.TextColor;

        // Update colors for components
        navigationBar.setBackground(BODY_COLOR);
        searchBar.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 2));
        textArea.setCaretColor(TEXT_COLOR);
        textArea.setForeground(TEXT_COLOR);
        textArea.setBackground(BODY_COLOR);
        appName.setForeground(TEXT_COLOR);
        burgerMenu.setForeground(TEXT_COLOR);

        // Repaint the components
        navigationBar.repaint();
        searchBar.repaint();
        textArea.repaint();
        appName.repaint();
        burgerMenu.repaint();
    }
}
