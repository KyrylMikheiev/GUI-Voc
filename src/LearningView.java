package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import VocabParsing.VocabParser;

public class LearningView {
    private JPanel content;
    private String lektion;
    private int currentVocabIndex;
    private JLabel phrase;
    private boolean isFront;
    private Main main;
    private JProgressBar progressBar;
    private ArrayList<Integer> wrongVocabs;
    private ArrayList<Integer> rightVocabs;


    public LearningView(JPanel content, String lektion, Main main) {
        this.content = content;
        this.lektion = lektion;
        this.currentVocabIndex = 0;
        this.isFront = true;
        this.main = main;

        setupUI();
        updateFlashcard();

        wrongVocabs = new ArrayList<>();
        rightVocabs = new ArrayList<>();
    }

    private void setupUI() {
        content.setLayout(new BorderLayout());
        content.setBackground(Main.BodyColor);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBackground(Main.BodyColor);

        JPanel contentUp = new JPanel(new GridLayout(2, 1));
        contentUp.setOpaque(false);

        JLabel progress = new JLabel("Progress:");
        progress.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
        progress.setForeground(Main.TextColor);

        JLabel lessonNumber = new JLabel("Lektion: " + lektion);
        lessonNumber.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
        lessonNumber.setHorizontalAlignment(JLabel.CENTER);
        lessonNumber.setForeground(Main.TextColor);

        progressBar = new JProgressBar();
        progressBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        progressBar.setOpaque(false);
        progressBar.setStringPainted(true);
        progressBar.setUI(new CustomProgressBarUI());
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setForeground(Color.WHITE);

        JButton info = new JButton("Vokabel anzeigen");
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.newVocabView(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex));
            }
        });

        JPanel lessonAndProgressPanel = new JPanel(new GridLayout(1, 3));
        lessonAndProgressPanel.setOpaque(false);
        lessonAndProgressPanel.add(lessonNumber);
        lessonAndProgressPanel.add(progressBar);
        lessonAndProgressPanel.add(new JPanel()); // Empty panel for amount fails
        lessonAndProgressPanel.add(info);

        contentUp.add(progress);
        contentUp.add(lessonAndProgressPanel);

        JPanel contentDown = new JPanel(new GridLayout(1, 3));
        contentDown.setBackground(Main.BodyColor);

        JPanel flashcardPanel = new JPanel(new BorderLayout());
        flashcardPanel.setBackground(Main.BodyColor);

        JPanel flashcardContainer = new JPanel(new BorderLayout());
        flashcardContainer.setBackground(Main.BodyColor);
        flashcardContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        phrase = new JLabel();
        phrase.setForeground(Main.TextColor);
        phrase.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
        phrase.setHorizontalAlignment(JLabel.CENTER);

        flashcardContainer.add(phrase, BorderLayout.CENTER);
        flashcardPanel.add(flashcardContainer, BorderLayout.CENTER);

        JButton flipButton = new JButton("Flip");
        flipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flipFlashcard();
            }
        });

        JButton prevButton = new JButton("false");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                falseVocab();
            }
        });

        JButton nextButton = new JButton("true");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trueVocab();
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPreviousVocab();
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
        buttonsPanel.setBackground(Main.BodyColor);
        buttonsPanel.add(prevButton);
        buttonsPanel.add(flipButton);
        buttonsPanel.add(nextButton);
        bodyPanel.add(buttonsPanel, BorderLayout.SOUTH);

        contentDown.add(new JPanel());
        contentDown.add(flashcardPanel);
        contentDown.add(new JPanel());

        bodyPanel.add(contentUp, BorderLayout.NORTH);
        bodyPanel.add(contentDown, BorderLayout.CENTER);

        content.add(bodyPanel, BorderLayout.CENTER);
    }

    private void flipFlashcard() {
        isFront = !isFront;
        updateFlashcard();
    }

    private void showPreviousVocab() {
        if (currentVocabIndex > 0) {
            currentVocabIndex--;
            updateFlashcard();
            updateProgressBar();
        }
        isFront = true;
        //try to remove the vocab from wrongVocabs/rightVocabs
        if (wrongVocabs.contains(currentVocabIndex)) {
            wrongVocabs.remove(currentVocabIndex);
        }
        if (rightVocabs.contains(currentVocabIndex)) {
            rightVocabs.remove(currentVocabIndex);
        }
    }

    private void showNextVocab() {
        // Assuming VocabParser.getVocabsFromLesson(lektion) returns a list of vocabs
        // and lektion is correctly initialized.
        if (currentVocabIndex < VocabParser.getVocabsFromLesson(lektion).size() - 1) {
            currentVocabIndex++;
            updateFlashcard();
            updateProgressBar();
        }
        isFront = true;
    }

    private void updateProgressBar() {
        int progress = (currentVocabIndex + 1) * 100 / VocabParser.getVocabsFromLesson(lektion).size();
        progressBar.setValue(progress);
        if (progress == 100) {
            // lesson finished, upload data
            uploadData();
            showFinishedScreen();
        }
    }
    private void updateFlashcard() {
        if (isFront) {
            phrase.setText(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex).getBasicForm());
        } else {
            phrase.setText(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex).getGerman().toString().replace("[", "").replace("]", ""));
        }
    }

    private void falseVocab() {
        wrongVocabs.add(currentVocabIndex);
        showNextVocab();
    }

    private void trueVocab() {
        rightVocabs.add(currentVocabIndex);
        showNextVocab();
    }

    private boolean uploadData() {
        try {
            //connect to database/rest api and upload the vocab data
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    private void showFinishedScreen() {
        // show finished screen
        // show right/all ratio
        // show wrong vocabs
        // show button to go to start screen
        // show button to re-learn wrong vocabs
    }
}
