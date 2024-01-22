package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class Main {
    
    private JButton learn, games, widgets, settings, library;
    private Color BLUE = Color.decode("#4255FF");
    private GridBagConstraints gbc = new GridBagConstraints();

    public Main() {

        //------------------navigationBar----------------
        JPanel navigationBar = new JPanel();
        navigationBar.setBackground(BLUE);
        navigationBar.setPreferredSize(new Dimension(200, 80));
        navigationBar.setLayout(new GridLayout(1, 3));

        //---------------appName---------------
        JPanel navigation_contentLeft = new JPanel();
        navigation_contentLeft.setLayout(new BorderLayout(10, 0));
        navigationBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        navigation_contentLeft.setOpaque(false);

        JLabel appName = new JLabel("AppName");
        appName.setFont(new Font("Times New Roman", 0, 28));
        navigation_contentLeft.add(appName, BorderLayout.CENTER);

        //---------------textArea---------------
        JPanel navigation_contentMiddle = new JPanel();
        navigation_contentMiddle.setLayout(new BorderLayout());
        Border border = BorderFactory.createEmptyBorder(10, 0, 10, 0);
        navigation_contentMiddle.setBorder(border);
        navigation_contentMiddle.setOpaque(false);
        navigation_contentMiddle.setBackground(Color.RED);

        JPanel searchBar = new JPanel();
        searchBar.setLayout(new BorderLayout(10, 0));
        searchBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        searchBar.setOpaque(false);

        PlaceholderTextField textArea = new PlaceholderTextField("Search", Color.WHITE);
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

        JMenuItem cheese = new JMenuItem("Cheese");
        JMenuItem bacon = new JMenuItem("Bacon");
        JMenuItem lettuce = new JMenuItem("Lettuce");

        burgerMenu.add(lettuce);
        burgerMenu.add(bacon);
        burgerMenu.add(cheese);
        menuBar.add(burgerMenu);
        navigation_contentRight.add(menuBar, BorderLayout.EAST);

        navigationBar.add(navigation_contentLeft);
        navigationBar.add(navigation_contentMiddle);
        navigationBar.add(navigation_contentRight);	


        //----------------body-----------------
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.darkGray);        
        bodyPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        learn = new JButton("Learn");
        games = new JButton("Games");
        widgets = new JButton("Widgets");
        settings = new JButton("Settings");
        library = new JButton("Library");

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(Color.RED);
                System.out.println("Entered");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(Color.YELLOW);
                System.out.println("Exited");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                e.getComponent().setBackground(Color.GREEN);
                System.out.println("Clicked");
            }
            @Override
            public void mousePressed(MouseEvent e) {
                e.getComponent().setBackground(Color.PINK);
                System.out.println("Pressed ---");
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                e.getComponent().setBackground(Color.ORANGE);
                System.out.println("Released");
            }
        };

        JButton[] buttons = {learn, games, widgets, settings, library};
        for(JButton button : buttons) {
            button.setBorder(null);
            button.setFont(new Font(Font.MONOSPACED, 0, 20));
            button.setFocusPainted(false);
            // button.setContentAreaFilled(false);
            button.setBackground(Color.YELLOW);
            button.addMouseListener(mouseListener);
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
  
        //content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(navigationBar, BorderLayout.NORTH);
        contentPane.add(bodyPanel, BorderLayout.CENTER);

        //window
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