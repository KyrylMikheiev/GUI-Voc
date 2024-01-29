package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

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
        JLabel progress = new JLabel("Progress:");
        progress.setFont(new Font(Font.SANS_SERIF, 0, 28));
        progress.setForeground(Main.TextColor);

        JPanel contentUp_ProgressText = new JPanel();
        contentUp_ProgressText.setOpaque(false);
        contentUp_ProgressText.add(progress);
        
        JPanel contentUp_LessonAndProgressBar = new JPanel();
        contentUp_LessonAndProgressBar.setOpaque(false);   
        contentUp_LessonAndProgressBar.setLayout(new GridLayout(1, 3));

        JLabel lessonNumber = new JLabel("Lektion");
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        progressBar.setOpaque(false);
        progressBar.setStringPainted(true);
        progressBar.setUI(new CustomProgressBarUI());
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(16);
        progressBar.setForeground(Color.WHITE);
        progressBar.setString("16%");
        
        // JLabel progressBar = new JLabel("ProgressBar");
        // progressBar.setFont(new Font(Font.SANS_SERIF, 0, 28));
        lessonNumber.setForeground(Main.TextColor);
        progressBar.setForeground(Main.TextColor);

        JPanel lessonNumberPanel = new JPanel();
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new GridLayout());
        JPanel amountFailsPanel = new JPanel();
        lessonNumberPanel.setOpaque(false);
        progressBarPanel.setOpaque(false);
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
        
        
        JPanel contentDown_leftEmptyPanel1 = new JPanel();
        JPanel contentDown_leftEmptyPanel2 = new JPanel();
        JPanel flashcardWithArrowsPanel = new JPanel();
        contentDown_leftEmptyPanel1.setOpaque(false);
        contentDown_leftEmptyPanel2.setOpaque(false);
        flashcardWithArrowsPanel.setOpaque(false);
        
        flashcardWithArrowsPanel.setLayout(new BorderLayout());

        JPanel flashcard_UpEmptyPanel = new JPanel();
        JPanel flashcard = new JPanel();
        JPanel arrowsPanel = new JPanel();

        flashcard_UpEmptyPanel.setOpaque(false);
        arrowsPanel.setOpaque(false);

        flashcard_UpEmptyPanel.setPreferredSize(new Dimension(200, 50));
        arrowsPanel.setPreferredSize(new Dimension(200, 70));

        JLabel phrase = new JLabel("Phrase");
        phrase.setForeground(Main.TextColor);
        phrase.setFont(new Font(Font.SANS_SERIF, 0, 28));
        flashcard.add(phrase);
        flashcard.setBackground(Main.defaultButton);
        flashcardWithArrowsPanel.add(flashcard_UpEmptyPanel, BorderLayout.NORTH);
        flashcardWithArrowsPanel.add(flashcard, BorderLayout.CENTER);
        flashcardWithArrowsPanel.add(arrowsPanel, BorderLayout.SOUTH);
        
        JPanel bodyPanel_contentDown = new JPanel();
        bodyPanel_contentDown.setBackground(Main.BodyColor);
        bodyPanel_contentDown.setLayout(new GridLayout(1, 3));
        bodyPanel_contentDown.add(contentDown_leftEmptyPanel1);
        bodyPanel_contentDown.add(flashcardWithArrowsPanel);
        bodyPanel_contentDown.add(contentDown_leftEmptyPanel2);
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBackground(Main.BodyColor);
        bodyPanel.add(bodyPanel_contentUp, BorderLayout.NORTH);
        bodyPanel.add(bodyPanel_contentDown, BorderLayout.CENTER);

        
        content.add(bodyPanel, BorderLayout.CENTER);
    }
}
