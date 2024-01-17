package src;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main { 
    public Main() {

        // create window
        JFrame frame = new JFrame();
        frame.setSize(1300, 900);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // create content pane and navigation bar
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(31, 38, 59));
        navBar.setSize(1300, 100);//wth why not working

        JPanel contentPane = new JPanel();
        contentPane.add(navBar);
        frame.add(contentPane);

        // create main menu and run it inside the window
        MainMenu menu = new MainMenu();
        menu.run(contentPane);

    }

    public static void main(String[] args) {
        new Main();
    }
}