package src;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class LearningSelection {
    public LearningSelection(JPanel content, Main main) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);
        
        JButton lektion1 = new JButton("Lektion 1");
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == lektion1) {
                    main.newLearningView("1");
                }
            }
        };
        lektion1.addActionListener(actionListener);
        bodyPanel.add(lektion1);

        content.add(bodyPanel);
    }
}
