package src;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import VocabAPI.WordTypes.Adjective;
import VocabAPI.WordTypes.Verb;
import VocabAPI.WordTypes.Vocab;

import java.awt.*;
import java.util.ArrayList;


public class VocabView {
    String[] pronomen = {"Ich", "Du", "Er/Sie/Es", "Wir", "Ihr", "Sie"};
    String[] zeitformen = {"Indikativ Aktiv", "Präsens", "Imperfekt", "Perfekt", "Plusquamperfekt", "Futur I", "Futur II"};
    String[] steigerungen = {"Positiv", "Komparativ", "Superlativ"};
    int numRows = pronomen.length;
    int numCols = zeitformen.length;
    int numCols1 = steigerungen.length;
    private final DefaultTableModel tableModel;
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
                }
                else {
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

        //JScrollPane scrollPane = new JScrollPane(table);
        //bodyPanel.add(scrollPane, BorderLayout.CENTER);
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
        bodyPanel.removeAll(); // Entferne alle Komponenten aus dem alten bodyPanel
        tableModel.setColumnCount(0); // Entferne alle Spalten aus dem alten tableModel
        
        
        
    
        // Füge die neuen Spalten für Steigerungen hinzu
        for (int i = 0; i < numCols1; i++) {
            tableModel.addColumn(steigerungen[i]);
        }

        
        JTableHeader header = table.getTableHeader();
        header.setBackground(Main.BodyColor); // Set header background color
        header.setForeground(Main.TextColor); // Set header text color
        header.setFont(new Font("Arial", Font.BOLD, 20));
    
        // Weitere Anpassungen...
       
        // Füge die Tabelle zum alten bodyPanel hinzu
        bodyPanel.add(header, BorderLayout.NORTH);
        bodyPanel.add(table, BorderLayout.CENTER);
        bodyPanel.revalidate(); // Aktualisiere das Layout des bodyPanel
    }
    

}
