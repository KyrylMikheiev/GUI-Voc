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
import javax.swing.JPanel;
import javax.swing.border.Border;


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