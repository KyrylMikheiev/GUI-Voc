package src.ui.screens.learning;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import src.ui.screens._BaseScreen;
import src.App;
import src.ui.ColorManager;
import src.ui.helper.*;
import VocabAPI.VocabParser;
import VocabAPI.WordTypes.*;

public class LibraryView extends _BaseScreen {
    private ArrayList<String> lessons;
    private int currentIndex;
    private ArrayList<JButton> vocabButtons = new ArrayList<>();
    private JPanel bodyPanel;

    public LibraryView() {
        super(false);
        
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBorder(new ResponsiveBorder(20, 20, 20, 20));
        bodyPanel.setBackground(ColorManager.bodyPrimary());

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

        this.rebuildUI();
    }

    @Override
    public JPanel createUI() {
        bodyPanel.removeAll();
        JButton nextLessonButton = new JButton(">");
        nextLessonButton.setBounds(860, 75, 75, 50);
        
        nextLessonButton.setFont(new Font(Font.SANS_SERIF, 0, 20));
        nextLessonButton.setFocusPainted(false);
        nextLessonButton.setBackground(ColorManager.buttonDefault());
        nextLessonButton.setForeground(ColorManager.text());

        nextLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (currentIndex < 44) {
                    currentIndex += 1;
                } else {
                    // Wenn der Index gleich 0 ist, setzen Sie ihn auf das letzte Element der Liste
                    currentIndex = 0;
                }
                rebuildUI();
            }
        });

        JButton prevLessonButton = new JButton("<");
        prevLessonButton.setBounds(325, 75, 75, 50);
        prevLessonButton.setFont(new Font(Font.SANS_SERIF, 0, 20));
        prevLessonButton.setFocusPainted(false);
        prevLessonButton.setBackground(ColorManager.buttonDefault());
        prevLessonButton.setForeground(ColorManager.text());

        prevLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Überprüfen, ob der aktuelle Index größer als 0 ist
                if (currentIndex > 0) {
                    currentIndex = (currentIndex - 1 + lessons.size()) % lessons.size();
                } else {
                    // Wenn der Index gleich 0 ist, setzen Sie ihn auf das letzte Element der Liste
                    currentIndex = lessons.size() - 2;
                }
                rebuildUI();
            }
            
        
        });

        JComboBox<Integer> lektionComboBox = createLektionComboBoxPanel();
        lektionComboBox.setSelectedIndex(currentIndex);  // Setze den Index erneut
        
        JButton lektionButton = new JButton("Lektion " + currentIndex);
        lektionButton.setBounds(420, 75, 420, 50);
        lektionButton.setFont(new Font(Font.SANS_SERIF, 0, 20));
        lektionButton.setFocusPainted(false);
        lektionButton.setBackground(ColorManager.buttonDefault());
        lektionButton.setForeground(ColorManager.text());
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
        
        
        JPanel vocabButtonsPanel = createVocabButtonsPanel();
        bodyPanel.add(vocabButtonsPanel);

        FasterScrollPane fasterScrollPane = new FasterScrollPane(vocabButtonsPanel);
        bodyPanel.add(fasterScrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(prevLessonButton);
        buttonPanel.add(lektionButton);
        buttonPanel.add(nextLessonButton);
        bodyPanel.add(buttonPanel, BorderLayout.NORTH);
        return bodyPanel;
    }

    private JPanel createVocabButtonsPanel() {
        JPanel vocabButtonsPanel = new JPanel();
        vocabButtonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        vocabButtonsPanel.setBackground(ColorManager.bodyPrimary());
        
        String currentLesson = lessons.get(currentIndex);
        
        for (Vocab vocab : VocabParser.getVocabsFromLesson(currentLesson)) {
            JButton vocabButton = new JButton(vocab.getBasicForm() + " - " + vocab.getGerman().toString().replace("[", "").replace("]", ""));
            vocabButton.setFont(new Font(Font.SANS_SERIF, 0, 12));
            vocabButton.setFocusPainted(false);
            vocabButton.setBackground(ColorManager.buttonDefault());
            vocabButton.setForeground(ColorManager.text());
            vocabButton.addActionListener(e -> App.switchScreen(new VocabView(vocab)));
        
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

    private JComboBox<Integer> createLektionComboBoxPanel() {
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
                    this.rebuildUI();
                });
            }
        });
        
        return lektionComboBox;
    }
}