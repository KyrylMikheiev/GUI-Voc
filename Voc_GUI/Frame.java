import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Frame {
    
    private JFrame frame;
    private JPanel contentPane;
    private JPanel navigation;
    private JButton plusButton;
    private Color BLUE;
    private JPanel middlePanel;
    
    public Frame() {
        BLUE = new Color(31, 38, 59);

        plusButton = new JButton();
        plusButton.setBackground(new Color(119, 52, 235));        
        plusButton.setLayout(new BorderLayout());
        ImageIcon plus = new ImageIcon("plus.png");
        Image originalImage = plus.getImage();
        Image resizedImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel label = new JLabel(resizedIcon);
        label.setBorder(BorderFactory.createEmptyBorder());
        plusButton.add(label);
        plusButton.setBorder(BorderFactory.createEmptyBorder());

        JTextArea textArea = new JTextArea();
        textArea.setSize(100, 20);
        textArea.setLayout(null);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Arial", 20, 40));
        textArea.setBackground(Color.PINK);

        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(3, 1));
        middlePanel.setBackground(Color.GREEN);
        JPanel panel1 = new JPanel();
        panel1.setBackground(BLUE);
        JPanel panel2 = new JPanel();
        panel2.setBackground(BLUE);
        middlePanel.add(panel1);
        middlePanel.add(textArea);
        //middlePanel.add(panel2);

        navigation = new JPanel();
        navigation.setLayout(new GridLayout(0, 3));
        navigation.setBackground(Color.RED);
        navigation.add(plusButton);
        navigation.add(middlePanel);

        contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(3, 1));
        contentPane.setSize(1000, 700);
        contentPane.setBackground(BLUE);
        contentPane.add(navigation);

        frame = new JFrame();
        frame.setSize(1300, 900);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setLocationRelativeTo(null);
        frame.add(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }



}
