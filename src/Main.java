package src;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

        // Global Content pane (including navbar)
        JPanel globalPane = new JPanel();
        globalPane.setLayout(new BorderLayout());

        // Window
        JFrame frame = new JFrame();
        frame.setIconImage(new ImageIcon("resources/images/logo.png").getImage());
        frame.setTitle("Vokabeltrainer");
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(globalPane);
        frame.setVisible(true);

        // UI Content Pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        globalPane.add(contentPane, BorderLayout.CENTER);

        // Navigation bar
        NavBar nav = new NavBar(globalPane);

        // Create UI's (in a cardPanel later?)
        MainMenu menu = new MainMenu(contentPane);
        //SettingsMenu settings = new SettingsMenu(contentPane);

        // Refresh screen to make stuff show up
        globalPane.revalidate();
        globalPane.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }

    public static void newUI(JPanel content) {
        content.removeAll();
        content.revalidate();
        content.repaint();
    }
}
