package src;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.BorderLayout;

public class Main {

    public Main() {
        // Set the cross-platform look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // Window
        JFrame frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setVisible(true);

        // Navigation bar
        NavBar nav = new NavBar(contentPane);

        // Create UI's (in a cardPanel later?)
        MainMenu menu = new MainMenu(contentPane);

        // Refresh screen to make stuff show up
        contentPane.revalidate();
        contentPane.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }
}
