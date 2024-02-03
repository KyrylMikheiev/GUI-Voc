package src;

import javax.swing.*;

import VocabAPI.Vocab;
import VocabAPI.VocabParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class LessonRoster extends JFrame {
    private DefaultListModel<String> availableListModel;
    private DefaultListModel<String> selectedListModel;
    private JList<String> availableList;
    private JList<String> selectedList;

    public LessonRoster() {
        setTitle("Roster Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Main.BodyColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        JLabel availableLabel = createStyledLabel("Verfügbare Lektionen");
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(25, 50, 5, 5);
        add(availableLabel, gbc);

        JLabel selectedLabel = createStyledLabel("Ausgewählte Lektionen");
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(25, 5, 5, 50);
        add(selectedLabel, gbc);

        availableListModel = new DefaultListModel<>();
         ArrayList<String> lessons = new ArrayList<>();
        for (Vocab i: VocabParser.getAllVocabs()) {
            if (!lessons.contains(i.getLesson())) {
                lessons.add(i.getLesson());
            }
        }


        // sort lessons alphabetically
        lessons.sort((s1, s2) -> {
            // Check if both s1 and s2 are numbers
            if (s1.matches("\\d+") && s2.matches("\\d+")) {
                int num1 = Integer.parseInt(s1);
                int num2 = Integer.parseInt(s2);
                return Integer.compare(num1, num2);
            } else {
                // If at least one of them is not a number, sort them in a case-insensitive manner
                return s1.compareToIgnoreCase(s2);
            }
        });

        for (String lesson: lessons) {
            availableListModel.addElement("Lektion " + lesson);

        }

        selectedListModel = new DefaultListModel<>();

        availableList = createStyledList(availableListModel, 25); // Schriftgröße 20 für availableList
        selectedList = createStyledList(selectedListModel, 25); // Schriftgröße 20 für selectedList

        JButton addButton = createStyledButton("Add ->");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move selected items from available list to selected list
                for (String selectedItem : availableList.getSelectedValuesList()) {
                    selectedListModel.addElement(selectedItem);
                    availableListModel.removeElement(selectedItem);

                    List<String> selectedElements = getSelectedElements();
                    System.out.println("Ausgewählte Elemente: " + selectedElements);
                }
            }


        });

        JButton removeButton = createStyledButton("<- Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move selected items from selected list to available list
                for (String selectedItem : selectedList.getSelectedValuesList()) {
                    availableListModel.addElement(selectedItem);
                    selectedListModel.removeElement(selectedItem);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(addButton, BorderLayout.NORTH);
        buttonPanel.add(Box.createVerticalStrut(50)); // Vertikaler Abstand
        buttonPanel.add(removeButton, BorderLayout.SOUTH);
        buttonPanel.setBackground(Main.BodyColor);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(50, 50, 50, 50);
        add(new JScrollPane(availableList), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 5, 10, 5); // Hier können Sie den Abstand einstellen
        add(buttonPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(50, 50, 50, 50);
        add(new JScrollPane(selectedList), gbc);

        

        // Setzen Sie die bevorzugte Größe der Buttons
        addButton.setPreferredSize(new Dimension(100, 150));
        removeButton.setPreferredSize(new Dimension(100, 150));

        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Main.defaultButton);
        button.setForeground(Main.TextColor);
        button.setFocusPainted(false); // Entfernt den visuellen Fokus-Rand
        return button;
    }

    private JList<String> createStyledList(DefaultListModel<String> model, int fontSize) {
        JList<String> list = new JList<>(model);
        list.setBackground(Main.BodyColor);
        list.setForeground(Main.TextColor);
        list.setSelectionBackground(Main.defaultButton); // Hintergrundfarbe für ausgewählte Elemente
        list.setSelectionForeground(Main.BodyColor); // Vordergrundfarbe für ausgewählte Elemente
        list.setBorder(BorderFactory.createLineBorder(Main.TextColor)); // Umrandungsfarbe

        // Setze die Schriftgröße für jedes Element
        Font listFont = list.getFont();
        list.setFont(new Font(listFont.getName(), listFont.getStyle(), fontSize));

        return list;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        label.setForeground(Main.TextColor);
        return label;
    }

    public List<String> getSelectedElements() {
        List<String> selectedElements = new ArrayList<>();

        for (int i = 0; i < selectedListModel.getSize(); i++) {
            selectedElements.add(selectedListModel.getElementAt(i));
        }

        return selectedElements;
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LessonRoster());
    }
}
