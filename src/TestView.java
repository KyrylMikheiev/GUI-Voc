package src;

import java.awt.GridLayout;
import java.util.ArrayList;
import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;
import javax.swing.JPanel;

public class TestView {
    public  TestView(JPanel content,Main main, ArrayList<String> selectedElements, boolean isCrossVisible1, boolean isCrossVisible2, boolean isCrossVisible3, boolean isCrossVisible4, boolean isCrossVisible5)  {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(1, 2));
        bodyPanel.setBackground(Main.BodyColor);

        ArrayList<Vocab> allVocabs = new ArrayList<>();
        for (int i = 0; i < selectedElements.size(); ++i)
        {
            for (int j = 0; j < VocabParser.getVocabsFromLesson(selectedElements.get(i).substring(8)).size(); ++j)
            {
                allVocabs.add(VocabParser.getVocabsFromLesson(selectedElements.get(i).substring(8)).get(j));
            }
            
        }

        
        content.add(bodyPanel);
    }
}
