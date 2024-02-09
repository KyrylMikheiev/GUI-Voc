package src;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;

public class TestView {
    private ArrayList<Vocab> selectedVocabs;
    private int currentVocabIndex;
    private JTextField[] translationTextFields;
    private JTextField nominativeTextField;
    private JTextField dativeTextField;

    public TestView(JPanel content, Main main, ArrayList<String> selectedElements, boolean isCrossVisible1, boolean isCrossVisible2, boolean isCrossVisible3, boolean isCrossVisible4, boolean isCrossVisible5) {
        selectedVocabs = new ArrayList<>();
        currentVocabIndex = 0;

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBackground(Main.BodyColor);
        System.out.println(selectedElements);
        ArrayList<Vocab> allVocabs = new ArrayList<>();
        for (int i = 0; i < selectedElements.size(); ++i) {
            allVocabs.addAll(VocabParser.getVocabsFromLesson(selectedElements.get(i).substring(8)));
        }

        // shuffle vocabs
        Collections.shuffle(allVocabs);

        // select ten random vocabs
        selectedVocabs.addAll(allVocabs.subList(0, Math.min(10, allVocabs.size())));

        // set up text fields for each translation
        if (!selectedVocabs.isEmpty()) {
            Vocab selectedVocab = selectedVocabs.get(currentVocabIndex); // currently selected vocab
            int anzahlÜbersetzungen = selectedVocab.getGerman().size(); // number of german translations for the selected vocab
            translationTextFields = new JTextField[anzahlÜbersetzungen]; // array of translation fields

            // create text fields for each translation
            for (int i = 0; i < anzahlÜbersetzungen; i++) {
                translationTextFields[i] = new JTextField(); 
            }
        }

        nominativeTextField = new JTextField();
        dativeTextField = new JTextField();

        // create panel for translation fields
        JPanel translationPanel = new JPanel();
        translationPanel.setLayout(new BoxLayout(translationPanel, BoxLayout.Y_AXIS));
        translationPanel.setBackground(Main.BodyColor);

        // create labels and text fields for translations
        for (int i = 0; i < translationTextFields.length; i++) {
            JPanel translationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            translationRow.setBackground(Main.BodyColor);
            translationRow.add(new JLabel("Übersetzung " + (i + 1) + ":"));
            translationRow.add(translationTextFields[i]);
            translationPanel.add(translationRow);
        }

        //  add panel for the translations
        bodyPanel.add(translationPanel, BorderLayout.WEST);

        // add labels and text fields for the  current vocab
        updateVocabFields(bodyPanel);

        // create panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Main.BodyColor);

        // create buttons for previous and next vocab
        JButton previousButton = new JButton("Vorherige Vokabel");
        previousButton.addActionListener(e -> previousVocab());

        JButton nextButton = new JButton("Nächste Vokabel");
        nextButton.addActionListener(e -> nextVocab());

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        // ????
        bodyPanel.add(buttonPanel, BorderLayout.SOUTH);

        content.add(bodyPanel);
    }
//UPDATE!!!!!!!!!!!
//update the GUI components in the bodyPanel
private void updateVocabFields(JPanel bodyPanel) {

    // create a JLabel with the name of the vocab
    JLabel vocabsListLabel = new JLabel(selectedVocabs.get(0).getBasicForm());
    vocabsListLabel.setFont(vocabsListLabel.getFont().deriveFont(35f));

    // center text (x axis) 
    vocabsListLabel.setHorizontalAlignment(SwingConstants.CENTER);
    vocabsListLabel.setBackground(Main.BodyColor);
    bodyPanel.add(vocabsListLabel, BorderLayout.NORTH);

    Vocab currentVocab = selectedVocabs.get(currentVocabIndex);

    // JPanel for forms
    JPanel nominativeDativePanel = new JPanel(new GridLayout(2, 1)); 
    nominativeDativePanel.setBackground(Main.BodyColor);
    nominativeDativePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
    
    // create panel for "nominativ"
JPanel nominativePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
nominativePanel.setBackground(Main.BodyColor);
JLabel nominativeLabel = new JLabel("Nominativ Plural auf Latein:");
nominativePanel.add(nominativeLabel);
nominativeTextField.setPreferredSize(new Dimension(200, 20)); 
nominativePanel.add(nominativeTextField);

// create panel for "dativ"
JPanel dativePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
dativePanel.setBackground(Main.BodyColor);
JLabel dativeLabel = new JLabel("Dativ Singular auf Latein:");
dativePanel.add(dativeLabel);
dativeTextField.setPreferredSize(new Dimension(200, 20)); 
dativePanel.add(dativeTextField);

// add both panels
nominativeDativePanel.add(nominativePanel);
nominativeDativePanel.add(dativePanel);
bodyPanel.add(nominativeDativePanel, BorderLayout.EAST);

    


   // create panel for translations
JPanel translationPanel = new JPanel();
translationPanel.setLayout(new BoxLayout(translationPanel, BoxLayout.Y_AXIS));
translationPanel.setBackground(Main.BodyColor);

// Erstelle Labels und Textfelder für die Übersetzungen
for (int i = 0; i < translationTextFields.length; i++) {
    JPanel translationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    translationRow.setBackground(Main.BodyColor);
    JLabel translationLabel = new JLabel("Übersetzung " + (i + 1) + ":");
    translationRow.add(translationLabel);
    translationTextFields[i].setPreferredSize(new Dimension(200, 20)); // Anpassen der Größe hier
    translationRow.add(translationTextFields[i]);
    translationPanel.add(translationRow);
}

// add panel for translations
bodyPanel.add(translationPanel, BorderLayout.WEST);


    bodyPanel.revalidate();
    bodyPanel.repaint();
}

    // check if all fields are filled in
    private boolean isTranslationValid(String translation) {
        return !translation.trim().isEmpty();
    }

    private boolean isNominativeValid(String nominative) {
        return !nominative.trim().isEmpty();
    }

    private boolean isDativeValid(String dative) {
        return !dative.trim().isEmpty();
    }

    private boolean areInputsValid() {
        for (JTextField textField : translationTextFields) {
            if (!isTranslationValid(textField.getText())) {
                return false;
            }
        }
        return isNominativeValid(nominativeTextField.getText()) && isDativeValid(dativeTextField.getText());
    }


// return error message if fields are not filled in
    private void nextVocab() {
        if (!areInputsValid()) {
            JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        currentVocabIndex++;
        if (currentVocabIndex >= selectedVocabs.size()) {
            currentVocabIndex = 0;
        }
        updateVocabFields((JPanel) translationTextFields[0].getParent());
    }

    private void previousVocab() {
        if (!areInputsValid()) {
            JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        currentVocabIndex--;
        if (currentVocabIndex < 0) {
            currentVocabIndex = selectedVocabs.size() - 1;
        }
        updateVocabFields((JPanel) translationTextFields[0].getParent());
    }

}
