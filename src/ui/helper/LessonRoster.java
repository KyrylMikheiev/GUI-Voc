package src.ui.helper;

import javax.swing.*;

import VocabAPI.WordTypes.Vocab;
import src.ui.ColorManager;
import VocabAPI.VocabParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import src.ui.helper.*;

public class LessonRoster {
    private DefaultListModel<String> availableListModel;
    private DefaultListModel<String> selectedListModel;
    private JList<String> availableList;
    private JList<String> selectedList;
    private JPanel graphicalContentPanel;
    private DefaultListModel<String> originalAvailableListModel;
    

    public LessonRoster() {
        graphicalContentPanel = createGraphicalContentPanel();
    }

    public JPanel getGraphicalContentPanel() {
        return graphicalContentPanel;
    }

    public List<String> getSelectedElements() {
        List<String> selectedElements = new ArrayList<>();

        for (int i = 0; i < selectedListModel.getSize(); i++) {
            selectedElements.add(selectedListModel.getElementAt(i));
        }

        return selectedElements;
    }

    private void createLists() {
        availableListModel = new DefaultListModel<>();
        selectedListModel = new DefaultListModel<>();
        originalAvailableListModel = new DefaultListModel<>();  // Neu hinzugefügt

        ArrayList<String> lessons = new ArrayList<>();
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

        for (String lesson : lessons) {
            availableListModel.addElement("Lektion " + lesson);
            originalAvailableListModel.addElement("Lektion " + lesson);  // Neu hinzugefügt
        }

        availableList = createStyledList(availableListModel, 25);
        selectedList = createStyledList(selectedListModel, 25);
    }

    private JPanel createGraphicalContentPanel() {
        createLists();
    
        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBorder(new ResponsiveBorder(50, 50, 1, 50));
        containerPanel.setBackground(ColorManager.bodyPrimary());
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
    
        JLabel availableLabel = createStyledLabel("Verfügbare Lektionen");
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(-45, 50, 5, 10);
        containerPanel.add(availableLabel, gbc);
    
        JLabel selectedLabel = createStyledLabel("Ausgewählte Lektionen");
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(-45, 10, 5, 50);
        containerPanel.add(selectedLabel, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 25);
        containerPanel.add(new JScrollPane(availableList), gbc);
    
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 25, 10, 15);
        containerPanel.add(new JScrollPane(selectedList), gbc);
    
        JButton addButton = createStyledButton("Add ->");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String selectedItem : availableList.getSelectedValuesList()) {
                    selectedListModel.addElement(selectedItem);
                    availableListModel.removeElement(selectedItem);
                }
            }
        });
    
        JButton removeButton = createStyledButton("<- Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedValues = selectedList.getSelectedValuesList();
        
                for (String selectedItem : selectedValues) {
                    int originalIndex = originalAvailableListModel.indexOf(selectedItem);
                    if (originalIndex != -1) {
                        availableListModel.add(originalIndex, selectedItem);
                    }
                    selectedListModel.removeElement(selectedItem);
                }
            }
        });

        removeButton.setPreferredSize(new Dimension(100, 125)); // Hier kannst du die gewünschten Dimensionen setzen
        addButton.setPreferredSize(new Dimension(100, 125)); // Hier kannst du die gewünschten Dimensionen setzen
        addButton.setFont(new Font("SansSerif", Font.PLAIN, 12)); // Hier kannst du die Schriftgröße anpassen
        removeButton.setFont(new Font("SansSerif", Font.PLAIN, 12)); // Hier kannst du die Schriftgröße anpassen


        
    
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(addButton, BorderLayout.NORTH);
        buttonPanel.add(Box.createVerticalStrut(25));
        buttonPanel.add(removeButton, BorderLayout.SOUTH);
        buttonPanel.setBackground(ColorManager.bodyPrimary());
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        containerPanel.add(buttonPanel, gbc);
    
        return containerPanel;
    }
    

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ColorManager.buttonDefault());
        button.setForeground(ColorManager.text());
        button.setFocusPainted(false);
        return button;
    }

    private JList<String> createStyledList(DefaultListModel<String> model, int fontSize) {
        JList<String> list = new JList<>(model);
        list.setBackground(ColorManager.bodyPrimary());
        list.setForeground(ColorManager.text());
        list.setSelectionBackground(ColorManager.buttonDefault());
        list.setSelectionForeground(ColorManager.bodyPrimary());
        list.setBorder(BorderFactory.createLineBorder(ColorManager.text()));

        Font listFont = list.getFont();
        list.setFont(new Font(listFont.getName(), listFont.getStyle(), fontSize));

        return list;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        label.setForeground(ColorManager.text());
        return label;
    }


    public ArrayList<String> getSelectedElementsAsStringList() {
        List<String> selectedElements = getSelectedElements();
        return new ArrayList<>(selectedElements);
    }
    
}
