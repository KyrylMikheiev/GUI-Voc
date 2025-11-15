package src.ui.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import src.ui.ColorManager;
import src.ui.helper.*;
import src.ui.screens._BaseScreen;
import VocabAPI.WordTypes.Vocab;
import VocabAPI.VocabParser;

public abstract class LessonSelector extends _BaseScreen {

    public LessonSelector() {
        super();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(ColorManager.bodyPrimary());
        
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
        title.setForeground(ColorManager.text());
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
        buttonPanel.setBackground(ColorManager.bodyPrimary());
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(ColorManager.buttonHover());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(ColorManager.buttonDefault());
            }
        };
        //create buttons for each lesson
        for (String lesson: lessons) {
            JButton button = new JButton("Lektion " + lesson);
            button.setBackground(ColorManager.buttonDefault());
            button.setForeground(ColorManager.text());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    execute(lesson);
                }
            });
            button.addMouseListener(mouseListener);
            buttonPanel.add(button);
        }
        bodyPanel.add(buttonPanel);
        FasterScrollPane scrollPane = new FasterScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bodyPanel.add(scrollPane);

        return bodyPanel;
    }

    abstract public void execute(String lesson);
}