package src;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import VocabParsing.Vocab;
import VocabParsing.VocabParser;
public class LearningView {
    

    public LearningView(JPanel content, String lektion) {
        
        // JPanel bodyPanel = new JPanel();
        // bodyPanel.setLayout(new GridLayout(10, 4, 20, 20));
        // bodyPanel.setBackground(Main.BodyColor);

        // //communicate with vocab api (sql?)
        // for (Vocab i: VocabParser.getVocabsFromLesson(lektion)) {
        //     bodyPanel.add(new JLabel(i.getGerman().get(0)));
        // }

        // content.add(bodyPanel);
        JPanel contentUp_ProgressText = new JPanel();
        contentUp_ProgressText.setOpaque(false);
        JLabel progress = new JLabel("Progress");
        progress.setForeground(Main.TextColor);
        contentUp_ProgressText.add(progress);
        
        JPanel contentUp_LessonAndProgressBar = new JPanel();
        contentUp_LessonAndProgressBar.setOpaque(false);
    
        contentUp_LessonAndProgressBar.setLayout(new GridLayout(1, 3));

        JPanel lessonNumberPanel = new JPanel();
        lessonNumberPanel.setOpaque(false);
        JLabel lessonNumber = new JLabel("Lektion");
        lessonNumber.setForeground(Main.TextColor);
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setOpaque(false);
        JLabel progressBar = new JLabel("ProgressBar");
        progressBar.setForeground(Main.TextColor);
        JPanel amountFailsPanel = new JPanel();
        amountFailsPanel.setOpaque(false);

        progressBarPanel.add(progressBar);
        lessonNumberPanel.add(lessonNumber);
        contentUp_LessonAndProgressBar.add(lessonNumberPanel);
        contentUp_LessonAndProgressBar.add(progressBarPanel);
        contentUp_LessonAndProgressBar.add(amountFailsPanel);


        JPanel bodyPanel_contentUp = new JPanel();
        bodyPanel_contentUp.setLayout(new GridLayout(2, 1));
        bodyPanel_contentUp.setBackground(Main.BodyColor);
        bodyPanel_contentUp.setOpaque(false);
        bodyPanel_contentUp.add(contentUp_ProgressText);
        bodyPanel_contentUp.add(contentUp_LessonAndProgressBar);
        
        JPanel bodyPanel_contentDown = new JPanel();
        bodyPanel_contentDown.setBackground(Main.BodyColor);
        bodyPanel_contentDown.setLayout(new GridLayout(1, 3));
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBackground(Main.BodyColor);
        bodyPanel.add(bodyPanel_contentUp, BorderLayout.NORTH);
        bodyPanel.add(bodyPanel_contentDown, BorderLayout.CENTER);

        
        content.add(bodyPanel, BorderLayout.CENTER);
    }
}
