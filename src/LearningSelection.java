package src;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import VocabParser.Vocab;
import VocabParser.VocabParser;


public class LearningSelection {
    public LearningSelection(JPanel content, Main main) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);
        
        //get all lessons
        ArrayList<String> lessons = new ArrayList<>();
        for (Vocab i: VocabParser.getAllVocabs()) {
            if (!lessons.contains(i.getLesson())) {
                lessons.add(i.getLesson());
            }
        }

        //create buttons for each lesson
        for (String lesson: lessons) {
            JButton button = new JButton(lesson);
            button.setBackground(Main.defaultButton);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    main.newLearningView(lesson);
                }
            });
            bodyPanel.add(button);
        }


        content.add(bodyPanel);
    }
}
