package src;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import VocabAPI.WordTypes.Vocab;
import VocabAPI.VocabParser;


public abstract class LessonSelector {
    public LessonSelector(JPanel content, Main main) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(Main.BodyColor);
        
        //System.out.println("Lessons: " + VocabParser.getAllVocabs());

        //get all lessons
        ArrayList<String> lessons = new ArrayList<>();
        for (Vocab i: VocabParser.getAllVocabs()) {
            if (!lessons.contains(i.getLesson())) {
                lessons.add(i.getLesson());
            }
        }

        JLabel title = new JLabel("Bitte wählen Sie eine Lektion aus:");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        title.setForeground(Main.TextColor);
        bodyPanel.add(title);
        
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Main.BodyColor);
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        //create buttons for each lesson
        for (String lesson: lessons) {
            JButton button = new JButton("Lektion " + lesson);
            button.setBackground(Main.defaultButton);
            button.setForeground(Main.TextColor);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    execute(lesson);
                }
            });
            buttonPanel.add(button);
        }
        bodyPanel.add(buttonPanel);
        FasterScrollPane scrollPane = new FasterScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bodyPanel.add(scrollPane);
        
        
        content.add(bodyPanel);
    }
    abstract void execute(String lesson);
}
