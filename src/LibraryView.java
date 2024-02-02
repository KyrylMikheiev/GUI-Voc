package src;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import VocabParsing.VocabParser;
import VocabParsing.Vocab;

public class LibraryView {
    public LibraryView(JPanel content, Main main) {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout());
        bodyPanel.setBackground(Main.BodyColor);

        //get all lessons
        ArrayList<String> lessons = new ArrayList<>();
        for (Vocab i: VocabParser.getAllVocabs()) {
            if (!lessons.contains(i.getLesson())) {
                lessons.add(i.getLesson());
            }
        }
        //sort lessons alphabetically
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

        // set up stuff
        JPanel vocabPanel = new JPanel();
        vocabPanel.setLayout(new GridLayout(0, 1, 0, 10));
        vocabPanel.setBackground(Main.BodyColor);

        // put all lesson titles and the vocabs of it into a scrollable panel
        for (String lesson : lessons) {
            JLabel title = new JLabel("Lektion " + lesson);
            title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            title.setForeground(Main.TextColor);
            vocabPanel.add(title);
            for (Vocab i : VocabParser.getVocabsFromLesson(lesson)) {
                JButton vocab = new JButton(i.getBasicForm() + " - " + i.getGerman().toString().replace("[", "").replace("]", ""));
                vocab.setBackground(Main.defaultButton);
                vocab.setForeground(Main.TextColor);
                vocab.setHorizontalAlignment(SwingConstants.LEFT); // Set text alignment to left
                vocab.addActionListener(e -> main.newVocabView(i));
                vocabPanel.add(vocab); // No need for BorderLayout.EAST
            }
        }
        bodyPanel.add(vocabPanel);

        // scroll stuff
        FasterScrollPane scrollPane = new FasterScrollPane(vocabPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bodyPanel.add(scrollPane);


        content.add(bodyPanel);
    }
}
