package src;

import javax.swing.*;

import VocabParsing.Vocab;
import VocabParsing.VocabParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class LibraryView {
    private ArrayList<String> lessons;
    private int currentIndex;
    private ArrayList<JButton> vocabButtons = new ArrayList<>();
    private JPanel bodyPanel;
   


    
    public LibraryView(JPanel content, Main main) {
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBorder(new ResponsiveBorder(20, 20, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);

        lessons = new ArrayList<>();
        for (Vocab i : VocabParser.getAllVocabs()) {
            if (!lessons.contains(i.getLesson())) {
                lessons.add(i.getLesson());
            }
        }

        lessons.sort((s1, s2) -> {
            if (s1.matches("\\d+") && s2.matches("\\d+")) {
                int num1 = Integer.parseInt(s1);
                int num2 = Integer.parseInt(s2);
                return Integer.compare(num1, num2);
            } else {
                return s1.compareToIgnoreCase(s2);
            }
        });

        currentIndex = 0;
        
            JScrollBar scrollbar = new JScrollBar();
            scrollbar.setBounds(420, 300, 400, 25);
            bodyPanel.add(scrollbar);

            bodyPanel.revalidate();
            bodyPanel.repaint();

            bodyPanel.add(scrollbar);
     
        updateLessonPanel(bodyPanel, main);
        content.add(bodyPanel);
    }

    private void updateLessonPanel(JPanel bodyPanel, Main main) {
        bodyPanel.removeAll();
        JButton nextLessonButton = new JButton(">");
        nextLessonButton.setBounds(860, 75, 75, 50);
        
        nextLessonButton.setFont(new Font(Font.SANS_SERIF, 0, 20));
        nextLessonButton.setFocusPainted(false);
        nextLessonButton.setBackground(Main.defaultButton);
        nextLessonButton.setForeground(Main.TextColor);

        nextLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex + 1) % lessons.size();
                updateLessonPanel(bodyPanel, main);
            }
        });

        JButton prevLessonButton = new JButton("<");
        prevLessonButton.setBounds(325, 75, 75, 50);
        prevLessonButton.setFont(new Font(Font.SANS_SERIF, 0, 20));
        prevLessonButton.setFocusPainted(false);
        prevLessonButton.setBackground(Main.defaultButton);
        prevLessonButton.setForeground(Main.TextColor);

        prevLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex - 1 + lessons.size()) % lessons.size();
                updateLessonPanel(bodyPanel, main);
            }
        });

        JComboBox<Integer> lektionComboBox = createLektionComboBoxPanel(main);
        lektionComboBox.setSelectedIndex(currentIndex);  // Setze den Index erneut
        
        JButton lektionButton = new JButton("Lektion " + currentIndex);
        lektionButton.setBounds(420, 75, 420, 50);
        lektionButton.setFont(new Font(Font.SANS_SERIF, 0, 20));
        lektionButton.setFocusPainted(false);
        lektionButton.setBackground(Main.defaultButton);
        lektionButton.setForeground(Main.TextColor);
        lektionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bodyPanel.add(lektionComboBox);
                Timer timer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        lektionComboBox.setPopupVisible(true);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
        
        
       
        JPanel vocabButtonsPanel = createVocabButtonsPanel(main);
        bodyPanel.add(vocabButtonsPanel);

        FasterScrollPane fasterScrollPane = new FasterScrollPane(vocabButtonsPanel);
        bodyPanel.add(fasterScrollPane, BorderLayout.CENTER);
        
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(prevLessonButton);
            buttonPanel.add(lektionButton);
            buttonPanel.add(nextLessonButton);
            bodyPanel.add(buttonPanel, BorderLayout.NORTH);

            bodyPanel.revalidate();
            bodyPanel.repaint();
        }
        
        private JPanel createVocabButtonsPanel(Main main) {
            JPanel vocabButtonsPanel = new JPanel();
            vocabButtonsPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            vocabButtonsPanel.setBackground(Main.BodyColor);
        
            String currentLesson = lessons.get(currentIndex);
        
            for (Vocab vocab : VocabParser.getVocabsFromLesson(currentLesson)) {
                JButton vocabButton = new JButton(vocab.getBasicForm() + " - " + vocab.getGerman().toString().replace("[", "").replace("]", ""));
                vocabButton.setFont(new Font(Font.SANS_SERIF, 0, 12));
                vocabButton.setFocusPainted(false);
                vocabButton.setBackground(Main.defaultButton);
                vocabButton.setForeground(Main.TextColor);
                vocabButton.addActionListener(e -> main.newVocabView(vocab));
        
                gbc.gridx = 0;
                gbc.gridy++;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0; // Setze weightx auf 1.0
                vocabButtonsPanel.add(vocabButton, gbc);
        
                if (vocab.getLesson().equals(currentLesson)) {
                    vocabButtons.add(vocabButton);
                }
            }
        
            return vocabButtonsPanel;
        }
      
        private JComboBox<Integer> createLektionComboBoxPanel(Main main) {
            JComboBox<Integer> lektionComboBox = new JComboBox<>();
            for (int i = 0; i <= 44; i++) {
                lektionComboBox.addItem(i);
            }
            lektionComboBox.setSelectedIndex(currentIndex);
        
            lektionComboBox.addActionListener(e -> {
                JComboBox<?> source = (JComboBox<?>) e.getSource();
                int selectedIndex = source.getSelectedIndex();
        
                if (selectedIndex != currentIndex) {
                    currentIndex = selectedIndex;
    
                    SwingUtilities.invokeLater(() -> {
                        updateLessonPanel(bodyPanel, main);
                    });
                }
            });
        
            return lektionComboBox;
        }
        
       
        
       
        
        
        
        
        
        
        
        
}

                                   
 
       
                           

       

