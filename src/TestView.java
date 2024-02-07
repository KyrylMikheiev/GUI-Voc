package src;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        bodyPanel.setLayout(new GridLayout(5, 2));
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

        // Erstelle Textfelder für die Übersetzungen
        if (!selectedVocabs.isEmpty()) {
        Vocab firstVocab = selectedVocabs.get(0);
        int anzahlÜbersetzungen = 0;
        for (Vocab v: selectedVocabs) {
            anzahlÜbersetzungen += v.getGerman().size();
        }
        translationTextFields = new JTextField[anzahlÜbersetzungen];
        for (int i = 0; i < anzahlÜbersetzungen; i++) {
        translationTextFields[i] = new JTextField();
        bodyPanel.add(new JLabel("Übersetzung " + (i+1) + ":"));
        bodyPanel.add(translationTextFields[i]);
    }
}

        for (int i = 0; i < translationTextFields.length; i++) {
            translationTextFields[i] = new JTextField();
            bodyPanel.add(new JLabel("Übersetzung " + (i+1) + ":"));
            bodyPanel.add(translationTextFields[i]);
        }

        nominativeTextField = new JTextField();
        dativeTextField = new JTextField();

        // Füge Labels und Textfelder für die aktuelle Vokabel hinzu
        updateVocabFields(bodyPanel);

        // Erstelle Buttons für Vor- und Zurück-Navigation
        JButton previousButton = new JButton("Vorherige Vokabel");
        previousButton.addActionListener(e -> previousVocab());

        JButton nextButton = new JButton("Nächste Vokabel");
        nextButton.addActionListener(e -> nextVocab());

        bodyPanel.add(previousButton);
        bodyPanel.add(nextButton);

        content.add(bodyPanel);
    }

    private void updateVocabFields(JPanel bodyPanel) {
        bodyPanel.removeAll();
        Vocab currentVocab = selectedVocabs.get(currentVocabIndex);

        // Erstelle Labels und Textfelder für die Übersetzungen
        for (int i = 0; i < translationTextFields.length; i++) {
            bodyPanel.add(new JLabel("Übersetzung " + (i+1) + ":"));
            bodyPanel.add(translationTextFields[i]);
        }

        JLabel nominativeLabel = new JLabel("Nominativ Plural auf Latein:");
        bodyPanel.add(nominativeLabel);
        bodyPanel.add(nominativeTextField);

        JLabel dativeLabel = new JLabel("Dativ Singular auf Latein:");
        bodyPanel.add(dativeLabel);
        bodyPanel.add(dativeTextField);

        bodyPanel.revalidate();
        bodyPanel.repaint();
    }

    private void previousVocab() {
        currentVocabIndex--;
        if (currentVocabIndex < 0) {
            currentVocabIndex = selectedVocabs.size() - 1;
        }
        updateVocabFields((JPanel) translationTextFields[0].getParent());
    }

    private void nextVocab() {
        currentVocabIndex++;
        if (currentVocabIndex >= selectedVocabs.size()) {
            currentVocabIndex = 0;
        }
        updateVocabFields((JPanel) translationTextFields[0].getParent());
    }
}
