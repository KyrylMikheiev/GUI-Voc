package src;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MainMenu {

    private int clickedElement = 0;
    private JPanel body;
    private JPanel contentPane;

    public int run(JPanel content)
    {
        // # create main menu
        body = content;
        contentPane = new JPanel();
        //create elements and add them to the contentPane

        JButton menuButton = new JButton();


        body.add(contentPane);

        return clickedElement;
    }

}
