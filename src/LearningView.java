package src;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import VocabParser.Vocab;
import VocabParser.VocabParser;
public class LearningView {
    

    public LearningView(JPanel content, String lektion) {
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(10, 4, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);

        //communicate with vocab api (sql?)
        for (Vocab i: VocabParser.getVocabsFromLesson(lektion)) {
            bodyPanel.add(new JLabel(i.getGerman().get(0)));
        }

        content.add(bodyPanel);
    }
}
