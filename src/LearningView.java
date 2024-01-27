package src;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import VocabParser.VocabParser;
public class LearningView {
    

    public LearningView(JPanel content) {
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(4, 2, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);

        //communicate with vocab api (sql?)
        VocabParser vP = new VocabParser();
        JLabel word = new JLabel(vP.parseToVocab().get(0).getGerman().get(0));
        word.setForeground(Main.TextColor);
        bodyPanel.add(word);

        content.add(bodyPanel);
    }
}
