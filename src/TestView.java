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

        // Vokabeln mischen
        Collections.shuffle(allVocabs);

        // Ersten zehn zufälligen Vokabeln auswählen
        selectedVocabs.addAll(allVocabs.subList(0, Math.min(10, allVocabs.size())));

        // Erstelle Textfelder für die Übersetzungen einer einzelnen Vokabel
        if (!selectedVocabs.isEmpty()) {
            Vocab selectedVocab = selectedVocabs.get(currentVocabIndex); // Die aktuell ausgewählte Vokabel
            int anzahlÜbersetzungen = selectedVocab.getGerman().size(); // Anzahl der deutschen Übersetzungen dieser Vokabel
            translationTextFields = new JTextField[anzahlÜbersetzungen]; // Array für die Übersetzungsfelder

            // Schleife zum Erstellen der Textfelder für die Übersetzungen
            for (int i = 0; i < anzahlÜbersetzungen; i++) {
                translationTextFields[i] = new JTextField(); // Neues Textfeld für eine Übersetzung
            }
        }

        nominativeTextField = new JTextField();
        dativeTextField = new JTextField();

        // Erstelle Panel für die Übersetzungen
        JPanel translationPanel = new JPanel();
        translationPanel.setLayout(new BoxLayout(translationPanel, BoxLayout.Y_AXIS));
        translationPanel.setBackground(Main.BodyColor);

        // Erstelle Labels und Textfelder für die Übersetzungen
        for (int i = 0; i < translationTextFields.length; i++) {
            JPanel translationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            translationRow.setBackground(Main.BodyColor);
            translationRow.add(new JLabel("Übersetzung " + (i + 1) + ":"));
            translationRow.add(translationTextFields[i]);
            translationPanel.add(translationRow);
        }

        // Füge Panel für die Übersetzungen hinzu
        bodyPanel.add(translationPanel, BorderLayout.WEST);

        // Füge Labels und Textfelder für die aktuelle Vokabel hinzu
        updateVocabFields(bodyPanel);

        // Erstelle Panel für die Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Main.BodyColor);

        // Erstelle Buttons für Vor- und Zurück-Navigation
        JButton previousButton = new JButton("Vorherige Vokabel");
        previousButton.addActionListener(e -> previousVocab());

        JButton nextButton = new JButton("Nächste Vokabel");
        nextButton.addActionListener(e -> nextVocab());

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        // Füge Panel für die Buttons hinzu
        bodyPanel.add(buttonPanel, BorderLayout.SOUTH);

        content.add(bodyPanel);
    }
//UPDATE!!!!!!!!!!!
private void updateVocabFields(JPanel bodyPanel) {
    // Anzeige der gesamten selectedVocabs-Liste
    JLabel vocabsListLabel = new JLabel("Gesamte Vokabeln: " + selectedVocabs.toString());
    bodyPanel.add(vocabsListLabel, BorderLayout.NORTH);

    Vocab currentVocab = selectedVocabs.get(currentVocabIndex);

    JPanel nominativeDativePanel = new JPanel(new GridLayout(2, 1)); // GridLayout für 2 Reihen und 2 Spalten
    nominativeDativePanel.setBackground(Main.BodyColor);
    nominativeDativePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Abstand um das Panel hinzufügen
    
    // Erstelle Panel für Nominativ
JPanel nominativePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
nominativePanel.setBackground(Main.BodyColor);
JLabel nominativeLabel = new JLabel("Nominativ Plural auf Latein:");
nominativePanel.add(nominativeLabel);
nominativeTextField.setPreferredSize(new Dimension(200, 20)); // Anpassen der Größe hier
nominativePanel.add(nominativeTextField);

// Erstelle Panel für Dativ
JPanel dativePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
dativePanel.setBackground(Main.BodyColor);
JLabel dativeLabel = new JLabel("Dativ Singular auf Latein:");
dativePanel.add(dativeLabel);
dativeTextField.setPreferredSize(new Dimension(200, 20)); // Anpassen der Größe hier
dativePanel.add(dativeTextField);

// Füge Panel für Nominativ und Dativ hinzu
nominativeDativePanel.add(nominativePanel);
nominativeDativePanel.add(dativePanel);
bodyPanel.add(nominativeDativePanel, BorderLayout.EAST);

    


    // Füge das Translation Panel hinzu
    JPanel translationPanel = new JPanel();
    translationPanel.setLayout(new BoxLayout(translationPanel, BoxLayout.Y_AXIS));
    translationPanel.setBackground(Main.BodyColor);

    // Erstelle Labels und Textfelder für die Übersetzungen
for (int i = 0; i < translationTextFields.length; i++) {
    JPanel translationRow = new JPanel(new BorderLayout());
    translationRow.setBackground(Main.BodyColor);
    JLabel translationLabel = new JLabel("Übersetzung " + (i + 1) + ":");
    JTextField translationTextField = new JTextField(20); // Ändere die Größe des Textfelds hier
    translationRow.add(translationLabel, BorderLayout.WEST);
    translationRow.add(translationTextField, BorderLayout.CENTER);
    translationPanel.add(translationRow);
}


    // Füge das Translation Panel hinzu
    bodyPanel.add(translationPanel, BorderLayout.WEST);

    bodyPanel.revalidate();
    bodyPanel.repaint();
}


    private boolean isTranslationValid(String translation) {
        // Hier kannst du deine Validierungslogik für die Übersetzungen implementieren
        // Zum Beispiel könntest du prüfen, ob die Übersetzung nicht leer ist
        return !translation.trim().isEmpty();
    }

    private boolean isNominativeValid(String nominative) {
        // Hier kannst du deine Validierungslogik für den Nominativ implementieren
        // Zum Beispiel könntest du prüfen, ob der Nominativ nicht leer ist
        return !nominative.trim().isEmpty();
    }

    private boolean isDativeValid(String dative) {
        // Hier kannst du deine Validierungslogik für den Dativ implementieren
        // Zum Beispiel könntest du prüfen, ob der Dativ nicht leer ist
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
