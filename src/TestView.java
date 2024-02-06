package src;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TestView {
    public  TestView(JPanel content,Main main, ArrayList<String> selectedElements, boolean isCrossVisible1, boolean isCrossVisible2, boolean isCrossVisible3, boolean isCrossVisible4, boolean isCrossVisible5)  {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(1, 2));
        bodyPanel.setBackground(Main.BodyColor);

        System.out.println(selectedElements);
        

        content.add(bodyPanel);
    }
}
