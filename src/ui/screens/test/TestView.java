package src.ui.screens.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.ArrayList;

import src.ui.helper.*;
import src.ui.ColorManager;
import src.ui.screens._BaseScreen;

import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;

public class TestView extends _BaseScreen {
    private ArrayList<Vocab> selectedVocabs;
    private int currentVocabIndex;

    public TestView(ArrayList<String> selectedElements, boolean isCrossVisible1, boolean isCrossVisible2, boolean isCrossVisible3, boolean isCrossVisible4, boolean isCrossVisible5) {
        super(false);

        selectedVocabs = new ArrayList<>();
        currentVocabIndex = 0;

        ArrayList<Vocab> allVocabs = new ArrayList<>();
        for (int i = 0; i < selectedElements.size(); ++i) {
            allVocabs.addAll(VocabParser.getVocabsFromLesson(selectedElements.get(i).substring(8)));
        }
        Collections.shuffle(allVocabs);
        selectedVocabs.addAll(allVocabs.subList(0, Math.min(10, allVocabs.size())));

        this.rebuildUI();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBackground(ColorManager.bodyPrimary());

        Vocab currentVocab = selectedVocabs.get(currentVocabIndex); // currently selected vocab
        int anzahlÜbersetzungen = currentVocab.getGerman().size(); // number of german translations for the selected vocab
        JTextField[] translationTextFields = new JTextField[anzahlÜbersetzungen]; // array of translation fields

        // create text fields for each translation
        for (int i = 0; i < anzahlÜbersetzungen; i++) {
            translationTextFields[i] = new JTextField(); 
        }

        //TODO: make them konjugation fields when vocab is verb
        JTextField nominativeTextField = new JTextField();
        JTextField dativeTextField = new JTextField();

        // create panel for translation fields
        JPanel translationPanel = new JPanel();
        translationPanel.setLayout(new BoxLayout(translationPanel, BoxLayout.Y_AXIS));
        translationPanel.setBackground(ColorManager.bodyPrimary());

        // create labels and text fields for translations
        for (int i = 0; i < translationTextFields.length; i++) {
            JPanel translationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            translationRow.setBackground(ColorManager.bodyPrimary());
            translationRow.add(new JLabel("Übersetzung " + (i + 1) + ":"));
            translationRow.add(translationTextFields[i]);
            translationPanel.add(translationRow);
        }

        // add panel for the translations
        bodyPanel.add(translationPanel, BorderLayout.WEST);

        // add labels and text fields for the  current vocab
        // create a JLabel with the name of the vocab
        JLabel vocabsListLabel = new JLabel(currentVocab.getBasicForm());
        vocabsListLabel.setFont(vocabsListLabel.getFont().deriveFont(35f));
        vocabsListLabel.setForeground(ColorManager.text());

        // center text (x axis) 
        vocabsListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        vocabsListLabel.setBackground(ColorManager.bodyPrimary());
        bodyPanel.add(vocabsListLabel, BorderLayout.NORTH);

        // JPanel for forms
        JPanel nominativeDativePanel = new JPanel(new GridLayout(2, 1)); 
        nominativeDativePanel.setBackground(ColorManager.bodyPrimary());
        nominativeDativePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        // create panel for "nominativ"
        JPanel nominativePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nominativePanel.setBackground(ColorManager.bodyPrimary());
        JLabel nominativeLabel = new JLabel("Nominativ Plural auf Latein:");
        nominativeLabel.setForeground(ColorManager.text());
        nominativePanel.add(nominativeLabel);
        nominativeTextField.setPreferredSize(new Dimension(200, 20)); 
        nominativeTextField.setBackground(ColorManager.buttonDefault());
        nominativeTextField.setForeground(ColorManager.text());
        nominativePanel.add(nominativeTextField);

        // create panel for "dativ"
        JPanel dativePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dativePanel.setBackground(ColorManager.bodyPrimary());
        JLabel dativeLabel = new JLabel("Dativ Singular auf Latein:");
        dativeLabel.setForeground(ColorManager.text());
        dativePanel.add(dativeLabel);
        dativeTextField.setPreferredSize(new Dimension(200, 20)); 
        dativeTextField.setBackground(ColorManager.buttonDefault());
        dativeTextField.setForeground(ColorManager.text());
        dativePanel.add(dativeTextField);

        // add both panels
        nominativeDativePanel.add(nominativePanel);
        nominativeDativePanel.add(dativePanel);
        bodyPanel.add(nominativeDativePanel, BorderLayout.EAST);




           // create panel for translations
        JPanel translationPanel2 = new JPanel();
        translationPanel2.setLayout(new BoxLayout(translationPanel2, BoxLayout.Y_AXIS));
        translationPanel2.setBackground(ColorManager.bodyPrimary());

        // Erstelle Labels und Textfelder für die Übersetzungen
        for (int i = 0; i < translationTextFields.length; i++) {
            JPanel translationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            translationRow.setBackground(ColorManager.bodyPrimary());
            JLabel translationLabel = new JLabel("Übersetzung " + (i + 1) + ":");
            translationLabel.setForeground(ColorManager.text());
            translationRow.add(translationLabel);
            translationTextFields[i].setPreferredSize(new Dimension(200, 20)); // Anpassen der Größe hier
            translationTextFields[i].setBackground(ColorManager.buttonDefault());
            translationTextFields[i].setForeground(ColorManager.text());
            translationRow.add(translationTextFields[i]);
            translationPanel2.add(translationRow);
        }

        // add panel for translations
        bodyPanel.add(translationPanel2, BorderLayout.WEST);

        // create panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(ColorManager.bodyPrimary());

        // create buttons for next vocab
        JButton nextButton = new JButton("Nächste Vokabel");
        nextButton.addActionListener(e -> {
            currentVocabIndex++;
            if (currentVocabIndex >= selectedVocabs.size()) {
                currentVocabIndex = 0;
            }
            this.rebuildUI();
        });
        nextButton.setBackground(ColorManager.buttonDefault());
        nextButton.setForeground(ColorManager.text());
        nextButton.setMaximumSize(new Dimension(400, 50));
        

        //buttonPanel.add(nextButton);
        buttonPanel.add(nextButton);

        // add panel for the buttons
        bodyPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bodyPanel;
    }
}