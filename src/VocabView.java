package src;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import VocabAPI.WordTypes.Adjective;
import VocabAPI.WordTypes.Verb;
import VocabAPI.WordTypes.Vocab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VocabView {
    String[] pronomen = {"Ich", "Du", "Er/Sie/Es", "Wir", "Ihr", "Sie"};
    String[] zeitformen = {"Indikativ Aktiv", "Präsens", "Imperfekt", "Perfekt", "Plusquamperfekt", "Futur I", "Futur II"};
    int numRows = pronomen.length;
    int numCols = zeitformen.length;
    private final DefaultTableModel tableModel;
    private static JTable table1;
    private static JPanel mainPanel;
    private final JTable table;
    private JPanel bodyPanel;

    public VocabView(JPanel content, Vocab v) {
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.setBorder(new ResponsiveBorder(20, 20, 20, 20));
        bodyPanel.setBackground(Main.BodyColor);

        // Set up the table model
        tableModel = new DefaultTableModel(numRows, 0);

        // Populate the table with headers and data
        for (int i = 0; i < numCols; i++) {
            tableModel.addColumn(zeitformen[i]);
        }
        for (int i = 0; i < numRows; i++) {
            tableModel.setValueAt(pronomen[i], i, 0);
        }

        table = new JTable(tableModel);
        table.setRowHeight(30); // Set the row height
        table.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font and size

        // Set custom cell renderer to change background and text color
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Set background color
                if (row % 2 == 0) {
                    component.setBackground(Main.defaultButton);
                } else {
                    component.setBackground(Main.BodyColor);
                }
                // Set text color
                component.setForeground(Main.TextColor);
                return component;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);
        table.setBackground(Main.BodyColor);
        table.setForeground(Main.TextColor);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Main.BodyColor); // Set header background color
        header.setForeground(Main.TextColor); // Set header text color
        header.setFont(new Font("Arial", Font.BOLD, 20));

        bodyPanel.add(header, BorderLayout.NORTH);
        bodyPanel.add(table, BorderLayout.CENTER);

        if (v instanceof Verb) {
            displayConjugations((Verb) v);
            System.out.println("hello");
        } else if (v instanceof Adjective) {
            displayAdjectiveFormsTableLayout((Adjective) v);
            System.out.println("hello");
        }

        content.add(bodyPanel);
    }

    private void displayConjugations(Verb verb) {
        ArrayList<String> PräsentsConjugations = verb.getPraesens();
        ArrayList<String> ImperfektConjugations = verb.getImperfekt();
        ArrayList<String> PerfektConjugations = verb.getPerfekt();
        ArrayList<String> PlusquamperfektConjugations = verb.getPlusquamperfekt();
        ArrayList<String> FuturIConjugations = verb.getFuturI();
        ArrayList<String> FuturIIConjugations = verb.getFuturII();

        for (int i = 0; i < PräsentsConjugations.size(); i++) {
            tableModel.setValueAt(PräsentsConjugations.get(i), i, 1);
        }
        for (int i = 0; i < ImperfektConjugations.size(); i++) {
            tableModel.setValueAt(ImperfektConjugations.get(i), i, 2);
        }
        for (int i = 0; i < PerfektConjugations.size(); i++) {
            tableModel.setValueAt(PerfektConjugations.get(i), i, 3);
        }
        for (int i = 0; i < PlusquamperfektConjugations.size(); i++) {
            tableModel.setValueAt(PlusquamperfektConjugations.get(i), i, 4);
        }
        for (int i = 0; i < FuturIConjugations.size(); i++) {
            tableModel.setValueAt(FuturIConjugations.get(i), i, 5);
        }
        for (int i = 0; i < FuturIIConjugations.size(); i++) {
            tableModel.setValueAt(FuturIIConjugations.get(i), i, 6);
        }
    }

    private void displayAdjectiveFormsTableLayout(Adjective adjective) {
        bodyPanel.removeAll();

        // Erstelle ein Panel für die Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Main.BodyColor);

        // Erstelle die Buttons
        JButton button1 = new JButton("Maskulinum");
        JButton button2 = new JButton("Femininum");
        JButton button3 = new JButton("Neutrum");

        // Füge ActionListener zu den Buttons hinzu
        button1.addActionListener(new ButtonListener());
        button2.addActionListener(new ButtonListener());
        button3.addActionListener(new ButtonListener());

        // Füge die Buttons zum Button-Panel hinzu
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        // Erstelle das Haupt-Panel für die GUI
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Main.BodyColor);

        // Füge das Button-Panel zum Haupt-Panel hinzu
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Erstelle eine Standard-Tabelle beim Start
        createTable("Maskulinum");

        // Füge das Haupt-Panel zum Hauptfenster hinzu und mache es sichtbar
        bodyPanel.add(mainPanel);
        bodyPanel.setBackground(Main.BodyColor);
        bodyPanel.setVisible(true);
    }

    private static void createTable(String gender) {
        // Erstelle eine Tabelle mit 5 Zeilen (für die Kasus) und 3 Spalten (für die Steigerungen)
        String[] steigerungen = {gender, "Positiv", "Komparativ", "Superlativ"};
        String[] kasus = {"Nominativ", "Genitiv", "Dativ", "Akkusativ", "Ablativ"};
        DefaultTableModel model = new DefaultTableModel(kasus.length, steigerungen.length);
        model.setColumnIdentifiers(steigerungen);

        // Setze die Zeilenbezeichnungen (Kasus)
        for (int i = 0; i < kasus.length; i++) {
            model.setValueAt(kasus[i], i, 0);
        }

        table1 = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table1);

        int topInset = 10;
        int leftInset = 100;
        int bottomInset = -200;
        int rightInset = 100;
        scrollPane.setBorder(BorderFactory.createEmptyBorder(topInset, leftInset, bottomInset, rightInset));
        scrollPane.setBackground(Main.BodyColor);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Aktualisiere das Haupt-Panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    static class ButtonListener implements ActionListener {
        // Bestimme die Daten für die neue Tabelle je nach geklicktem Button
        @Override
        public void actionPerformed(ActionEvent e) {
            // Bestimme das Geschlecht je nach geklicktem Button
            String gender = "";
            if (e.getActionCommand().equals("Maskulinum")) {
                gender = "Maskulinum";
            } else if (e.getActionCommand().equals("Femininum")) {
                gender = "Femininum";
            } else if (e.getActionCommand().equals("Neutrum")) {
                gender = "Neutrum";
            }

            // Entferne die alte Tabelle, falls vorhanden
            Component[] components = mainPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JScrollPane) {
                    mainPanel.remove(component);
                }
            }

            // Erstelle die neue Tabelle mit dem entsprechenden Geschlecht
            createTable(gender);

            // Aktualisiere das Haupt-Panel
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }
}
