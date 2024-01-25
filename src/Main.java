package src;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {

    public Main() {

        //content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        //window
        JFrame frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setVisible(true);

        // navigation bar
        NavBar nav = new NavBar(contentPane);

        // create ui's (in a cardPanel later?)
        MainMenu menu = new MainMenu(contentPane);

        //refresh screen to make stuff show up
        contentPane.revalidate();
        contentPane.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }

}