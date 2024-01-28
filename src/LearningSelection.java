package src;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import VocabParsing.Vocab;
import VocabParsing.VocabParser;


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

        // sort lessons alphabetically
        lessons.sort((s1, s2) -> {
            // Check if both s1 and s2 are numbers
            if (s1.matches("\\d+") && s2.matches("\\d+")) {
                int num1 = Integer.parseInt(s1);
                int num2 = Integer.parseInt(s2);
                return Integer.compare(num1, num2);
            } else {
                // If at least one of them is not a number, sort them in a case-insensitive manner
                return s1.compareToIgnoreCase(s2);
            }
        });

        //create buttons for each lesson
        for (String lesson: lessons) {
            JButton button = new JButton(lesson);
            button.setBackground(Main.defaultButton);
            button.setForeground(Main.TextColor);
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
