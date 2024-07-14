package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import VocabAPI.VocabParser;
import restAPI.APIClient;

public class LearningView {
    private JPanel content;
    private JPanel bodyPanel;
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
        
        //------------------------------------------------------------
        //----------------start of "bodyPanel_contentUp"----------------
        //------------------------------------------------------------
        //      2 inhirated objects: 
        //          1) contentUp_ProgressText;
        //          2) contentUp_LessonAndProgressBar 
        //------------------------------------------------------------- 
        JPanel bodyPanel_contentUp = new JPanel();
        bodyPanel_contentUp.setLayout(new GridLayout(2, 1));
        bodyPanel_contentUp.setBackground(Main.BodyColor);
        bodyPanel_contentUp.setOpaque(false);
        

        //--------------from "bodyPanel_contentUp":
        //              contentUp_ProgressText
        //
        //              1 inhirated object:
        //                  1) progress
        //---------------------------------------------------
        JPanel contentUp_ProgressText = new JPanel();
        contentUp_ProgressText.setOpaque(false);   

        //progress  
        JLabel progress = new JLabel("Progress:");
        progress.setFont(new Font(Font.SANS_SERIF, 0, 28));
        progress.setForeground(Main.TextColor);
        //end of progress

        contentUp_ProgressText.add(progress);
        //-----------end of contentUp_ProgressText----------------
        
        

        //--------------from "bodyPanel_contentUp":
        //              contentUp_LessonAndProgressBar
        //
        //            3 inhirated objects: 
        //              1) lessonNumberPanel; 
        //              2) progressBarPanel; 
        //              3) amountFailsPanel
        //-------------------------------------------------------------------------
        JPanel contentUp_LessonAndProgressBar = new JPanel();    
        contentUp_LessonAndProgressBar.setOpaque(false);         
        contentUp_LessonAndProgressBar.setLayout(new GridLayout(1, 3));
        
        //---------------From "contentUp_LessonAndProgressBar": 
        //           lessonNumberPanel
        //
        //           1 inhirated object: 
        //              1) lessonNumber

        JPanel lessonNumberPanel = new JPanel();
        lessonNumberPanel.setOpaque(false);
        lessonNumberPanel.setLayout(new GridLayout());
        
        //lessonNumber
        JLabel lessonNumber = new JLabel("Lektion "+ lektion);
        lessonNumber.setFont(new Font(Font.SANS_SERIF, 0, 28));
        lessonNumber.setHorizontalAlignment(JLabel.CENTER);
        lessonNumber.setForeground(Main.TextColor);
        //end of lessonNumber
        
        lessonNumberPanel.add(lessonNumber);
        //--------------end of lessonNumberPanel----------------
        
        
        //--------------From "contentUp_LessonAndProgressBar": 
        //              progressBarPanel
        //
        //           1 inhirated object: 
        //              1) progressBar
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new GridLayout());
        progressBarPanel.setOpaque(false);
        
        //progressBar
        progressBar = new JProgressBar();
        progressBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        progressBar.setOpaque(false);
        progressBar.setStringPainted(true);
        progressBar.setUI(new CustomProgressBarUI());
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setForeground(Color.WHITE);
        //end of progressBar
        
        progressBarPanel.add(progressBar);
        //--------------end of progressBarPanel----------------
        

        
        //--------------From "contentUp_LessonAndProgressBar": 
        //              amountFailsPanel
        //----------------------------------------
        JPanel amountFailsPanel = new JPanel();
        amountFailsPanel.setOpaque(false);
        //end of amountFailsPanel
        
        contentUp_LessonAndProgressBar.add(lessonNumberPanel);
        contentUp_LessonAndProgressBar.add(progressBarPanel);
        contentUp_LessonAndProgressBar.add(amountFailsPanel);
        //--------------end of contentUp_LessonAndProgressBar----------------
        
        bodyPanel_contentUp.add(contentUp_ProgressText);
        bodyPanel_contentUp.add(contentUp_LessonAndProgressBar);    
        
        //--------------end of bodyPanel_contentUp-------------------
        
        





        
        
        JPanel crossPanel = new JPanel();
        crossPanel.setOpaque(false);
        crossPanel.setLayout(new BorderLayout());
        
        JPanel flashcardPanel = new JPanel();
        flashcardPanel.setOpaque(false);
        flashcardPanel.setLayout(new BorderLayout());

        JPanel checkmarkPanel = new JPanel();      
        checkmarkPanel.setOpaque(false);
        checkmarkPanel.setLayout(new BorderLayout());

        JLabel backArrowLabel = new JLabel("Eine Aktion zurück");
        backArrowLabel.setFont(new Font(Font.SANS_SERIF, 0, 18));
        backArrowLabel.setForeground(Main.TextColor);

        JPanel backArrowPanel = new JPanel();
        backArrowPanel.setOpaque(false);
        backArrowPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        backArrowPanel.setLayout(new BorderLayout(20, 0));

        //---------------backArrowButton---------------
        JPanel backArrowButtonPanel = new JPanel(); 
        backArrowButtonPanel.setOpaque(false);
        backArrowButtonPanel.setLayout(new BorderLayout());
        backArrowButtonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JButton backArrowButton = new JButton();
        backArrowButton.setBorder(null);
        backArrowButton.setBorder(BorderFactory.createEmptyBorder(200, 0, 200, 0));
        backArrowButton.setContentAreaFilled(false);
        backArrowButton.setFocusPainted(false);
        backArrowButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                showPreviousVocab();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backArrowButton.setContentAreaFilled(true);
                backArrowButton.setBackground(Main.HoverButton);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backArrowButton.setContentAreaFilled(false);
            }
        });
        Image image = new ImageIcon("./resources/images/arrow.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        backArrowButton.setIcon(new ImageIcon(image)); 
        backArrowButtonPanel.add(backArrowButton, BorderLayout.CENTER);
        backArrowPanel.add(backArrowLabel, BorderLayout.CENTER);
        backArrowPanel.add(backArrowButtonPanel, BorderLayout.WEST);

        JButton cross = new JButton();
        Image crossImage = new ImageIcon("./resources/images/close.png").getImage().getScaledInstance(main.getFrame().getWidth() / 5, main.getFrame().getHeight() / 3, Image.SCALE_SMOOTH);
        cross.setIcon(new ImageIcon(crossImage));
        cross.setBorder(null);
        cross.setContentAreaFilled(false);
        cross.setFocusPainted(false);
        cross.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    falseVocab();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    cross.setContentAreaFilled(true);
                    cross.setBackground(Main.HoverButton);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    cross.setContentAreaFilled(false);
                }
        });
        crossPanel.add(cross, BorderLayout.CENTER);



        //----adding to leftPanel_Center

        
        phrase = new JLabel("Phrase");
        phrase.setForeground(Main.TextColor);
        phrase.setFont(new Font(Font.SANS_SERIF, 0, 28));

        JPanel flashcard = new JPanel();
        flashcard.add(phrase);
        flashcard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                flipFlashcard();
            }
        });
        flashcard.setBackground(Main.DefaultButton);
        flashcardPanel.add(flashcard, BorderLayout.CENTER);
        
        checkmarkPanel.setLayout(new BorderLayout());
        JButton checkmark = new JButton();
        Image checkmarkImage = new ImageIcon("./resources/images/checkmark.png").getImage().getScaledInstance(main.getFrame().getWidth() / 5, main.getFrame().getHeight() / 3, Image.SCALE_SMOOTH);
        checkmark.setIcon(new ImageIcon(checkmarkImage));
        checkmark.setBorder(null);
        checkmark.setContentAreaFilled(false);
        checkmark.setFocusPainted(false);
        checkmark.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    trueVocab();
                }  
                @Override
                public void mouseEntered(MouseEvent e) {
                    checkmark.setContentAreaFilled(true);
                    checkmark.setBackground(Main.HoverButton);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    checkmark.setContentAreaFilled(false);
                }
        });
        checkmarkPanel.add(checkmark, BorderLayout.CENTER);

        JPanel bodyPanel_contentDown = new JPanel();
        bodyPanel_contentDown.setBackground(Main.BodyColor);
        bodyPanel_contentDown.setLayout(new BorderLayout()); 

        JPanel contentDown_center = new JPanel();
        contentDown_center.setOpaque(false);
        contentDown_center.setLayout(new GridLayout(1, 3));
        contentDown_center.add(crossPanel);
        contentDown_center.add(flashcardPanel);
        contentDown_center.add(checkmarkPanel);

        JPanel emptyPanelSouth = new JPanel();
        emptyPanelSouth.setOpaque(false);
        emptyPanelSouth.setPreferredSize(new Dimension(200, 90)); 

        JPanel emptyPanelNorth = new JPanel();
        emptyPanelNorth.setOpaque(false);
        emptyPanelNorth.setPreferredSize(new Dimension(200, 70));
        emptyPanelNorth.setLayout(new BorderLayout());
        emptyPanelNorth.add(backArrowPanel, BorderLayout.WEST);


        bodyPanel_contentDown.add(emptyPanelNorth, BorderLayout.NORTH);
        bodyPanel_contentDown.add(contentDown_center, BorderLayout.CENTER);
        bodyPanel_contentDown.add(emptyPanelSouth, BorderLayout.SOUTH);

        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBackground(Main.BodyColor);
        bodyPanel.add(bodyPanel_contentUp, BorderLayout.NORTH);
        bodyPanel.add(bodyPanel_contentDown, BorderLayout.CENTER);

        
        content.add(bodyPanel, BorderLayout.CENTER);
    }

    private void flipFlashcard() {
        isFront = !isFront;
        updateFlashcard();
    }

    private void showPreviousVocab() {
        if (currentVocabIndex > 0) {
            currentVocabIndex--;
            isFront = true;
            updateFlashcard();
            updateProgressBar();

            //try to remove the vocab from wrongVocabs/rightVocabs
            if (wrongVocabs.indexOf(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex + 1).getID()) != -1) 
                wrongVocabs.remove(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex + 1).getID());
            if (rightVocabs.indexOf(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex + 1).getID()) != -1) 
                rightVocabs.remove(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex + 1).getID());

        }
    }

    private void showNextVocab() {
        // Assuming VocabParser.getVocabsFromLesson(lektion) returns a list of vocabs
        // and lektion is correctly initialized.
        if (currentVocabIndex < VocabParser.getVocabsFromLesson(lektion).size() - 1) {
            currentVocabIndex++;
            isFront = true;
            updateFlashcard();
            updateProgressBar();
        }
    }

    private void updateProgressBar() {
        int progress = (currentVocabIndex) * 100 / (VocabParser.getVocabsFromLesson(lektion).size() - 1);
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
        wrongVocabs.add(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex).getID());
        showNextVocab();
    }

    private void trueVocab() {
        rightVocabs.add(VocabParser.getVocabsFromLesson(lektion).get(currentVocabIndex).getID());
        showNextVocab();
    }

    private boolean uploadData() {
        //connect to rest api and upload the vocab data
        return APIClient.updateUserVocabStats(wrongVocabs, rightVocabs);
    }
    private void showFinishedScreen() {
        // show finished screen
        // show right/all ratio
        // show wrong vocabs
        // show button to go to start screen
        // show button to re-learn wrong vocabs

        bodyPanel.removeAll();
        bodyPanel.revalidate();
        bodyPanel.repaint();

        JPanel congratulationScreen = new JPanel();
        congratulationScreen.setBackground(Main.BodyColor);
        congratulationScreen.setLayout(new GridLayout(3,1));

        JLabel congratulations = new JLabel("<html>" +"Herzlich Glückwunsch! Du hast die Lektion " + lektion + " beendet!" + "<br>" + "Hier sind deine Ergebnisse:" + "</html>");
        congratulations.setFont(new Font("Arial", Font.BOLD, 20));
        congratulations.setForeground(Main.TextColor);
        congratulations.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setOpaque(false);
        statisticsPanel.setLayout(new GridLayout(2,1, 0, 30));
        statisticsPanel.setBorder(new ResponsiveBorder(20, 420, 50, 300));

        JLabel rightVocabsLabel = new JLabel("Richtig: " + rightVocabs.size());
        rightVocabsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rightVocabsLabel.setForeground(Main.TextColor);
        // rightVocabsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel wrongVocabsLabel = new JLabel("Falsch: " + wrongVocabs.size());
        wrongVocabsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        wrongVocabsLabel.setForeground(Main.TextColor);
        // wrongVocabsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(1, 2, 50, 0));
        buttonsPanel.setBorder(new ResponsiveBorder(90, 420, 90, 300));

        JButton backMainMenu = new JButton("Zurück zum Hauptmenu");
        JButton relearnVocabs = new JButton("Falsche Vocabs lernen");

        backMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bodyPanel.removeAll();
                bodyPanel.revalidate();
                bodyPanel.repaint();
                main.newMainMenu();
            }
        });

        relearnVocabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: make relearn screen
                // pass wrongVocabs into new learningView
            }
        });

        buttonsPanel.add(relearnVocabs);
        buttonsPanel.add(backMainMenu);

        statisticsPanel.add(rightVocabsLabel);
        statisticsPanel.add(wrongVocabsLabel);

        congratulationScreen.add(congratulations);
        congratulationScreen.add(statisticsPanel);
        congratulationScreen.add(buttonsPanel);


        bodyPanel.add(congratulationScreen);

        bodyPanel.revalidate();
        bodyPanel.repaint();


    }
}
