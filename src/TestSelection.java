package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class TestSelection extends JPanel {
    private JPanel bodyPanel;
    LessonRoster lessonRoster = new LessonRoster();
    private JPanel buttonsPanel; 
    public boolean isCrossVisible1 = false; // Deklarieren Sie das JPanel als Klassenattribut
    public boolean isCrossVisible2 = false; // Deklarieren Sie das JPanel als Klassenattribut
    public boolean isCrossVisible3 = false; // Deklarieren Sie das JPanel als Klassenattribut
    public boolean isCrossVisible4 = false; // Deklarieren Sie das JPanel als Klassenattribut
    public boolean isCrossVisible5 = false; // Deklarieren Sie das JPanel als Klassenattribut
    public ArrayList<String> selectedElements = new ArrayList<>(lessonRoster.getSelectedElementsAsStringList());



    public TestSelection(JPanel content, Main main) {
        setLayout(new BorderLayout());
        setBorder(new ResponsiveBorder(20, 20, 20, 20));

        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());  

        // Jetzt, nachdem bodyPanel initialisiert wurde, kannst du die Hintergrundfarbe setzen
        bodyPanel.setBackground(Main.BodyColor);

      
        JPanel graphicalContentPanel = lessonRoster.getGraphicalContentPanel();

        
        
       

        bodyPanel.add(graphicalContentPanel, BorderLayout.NORTH);
        graphicalContentPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Setze die gewünschte Größe für den Button
      
        buttonsPanel = new JPanel(new GridBagLayout());

        // Fügen Sie die Buttons zum vorhandenen Panel hinzu
    
     
    
        // Jetzt, nachdem buttonsPanel initialisiert wurde, kannst du die Hintergrundfarbe setzen
        buttonsPanel.setBackground(Main.BodyColor);

        JPanel buttonsPanel1 = new JPanel(new GridBagLayout());
    
        // Jetzt, nachdem buttonsPanel initialisiert wurde, kannst du die Hintergrundfarbe setze
        buttonsPanel1.setBackground(Main.BodyColor);

        
        GridBagConstraints gbc = new GridBagConstraints();
       
    
        // Erstelle und füge vier separate Buttons hinzu

        JCheckBox checkBox1 = new JCheckBox("Alle Übersetzungen abfragen");

        checkBox1.setPreferredSize(new Dimension(1000, 50));
        checkBox1.setBackground(Main.BodyColor);


// Ändere die Schriftgröße, um die Größe der JCheckBox zu beeinflussen
Font checkBoxFont1 = checkBox1.getFont();
checkBox1.setFont(new Font(checkBoxFont1.getName(), checkBoxFont1.getStyle(), 20));  // 20 ist die gewünschte Schriftg
checkBox1.setForeground(Main.TextColor);

       
    
        checkBox1.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected()) {
                    checkBox1.setBackground((Main.BodyColor));
                    isCrossVisible1 = true;
        
        
                } else {
                    checkBox1.setIcon(null);  // Entferne das Icon
                    isCrossVisible1 = false;
                    
                }
            }
        });

        JCheckBox  checkBox2 = new JCheckBox("Reihenfolge der eingegebenen Übersetzungen ignorien");

        checkBox2.setPreferredSize(new Dimension(1000, 50));
        checkBox2.setBackground(Main.BodyColor);

// Ändere die Schriftgröße, um die Größe der JCheckBox zu beeinflussen
Font checkBoxFont2 = checkBox2.getFont();
checkBox2.setFont(new Font(checkBoxFont2.getName(), checkBoxFont2.getStyle(), 20));  // 20 ist die gewünschte Schriftg

checkBox2.setForeground(Main.TextColor);
        
        checkBox2.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
               
    
                if (checkBox2.isSelected()) {
                    checkBox2.setBackground((Main.BodyColor));
                    isCrossVisible2 = true;
        
        
                } else {
                    checkBox2.setIcon(null);  // Entferne das Icon
                    isCrossVisible2 = false;
                    
                }
            }
        });

        JCheckBox checkBox3 = new JCheckBox("Stammformen von Verben abfragen");
        checkBox3.setPreferredSize(new Dimension(1000, 50));
        checkBox3.setBackground(Main.BodyColor);

// Ändere die Schriftgröße, um die Größe der JCheckBox zu beeinflussen
Font checkBoxFont3 = checkBox3.getFont();
checkBox3.setFont(new Font(checkBoxFont3.getName(), checkBoxFont3.getStyle(), 20));  // 20 ist die gewünschte Schriftg

checkBox3.setForeground(Main.TextColor);
     
    
        checkBox3.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                
    
                if (checkBox3.isSelected()) {
                    checkBox3.setBackground((Main.BodyColor));
                    isCrossVisible3 = true;
        
        
                } else {
                    checkBox3.setIcon(null);  // Entferne das Icon
                    isCrossVisible3 = false;
                    
                }
            }
        });

        JCheckBox checkBox4 = new JCheckBox("zufällige Deklination abfragen");
        checkBox4.setPreferredSize(new Dimension(1000, 50));
        checkBox4.setBackground(Main.BodyColor);

// Ändere die Schriftgröße, um die Größe der JCheckBox zu beeinflussen
Font checkBoxFont4 = checkBox4.getFont();
checkBox4.setFont(new Font(checkBoxFont4.getName(), checkBoxFont4.getStyle(), 20));  // 20 ist die gewünschte Schriftg
checkBox4.setForeground(Main.TextColor);
    
        checkBox4.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                
    
                if (checkBox4.isSelected()) {
                    checkBox4.setBackground((Main.BodyColor));
                    isCrossVisible4 = true;
        
        
                } else {
                    checkBox4.setIcon(null);  // Entferne das Icon
                    isCrossVisible4 = false;
                    
                }
            }
        });

      
        JCheckBox checkBox5 = new JCheckBox("zufällige Kunjugationen abfragen");
        checkBox5.setPreferredSize(new Dimension(1000, 50));
        checkBox5.setBackground((Main.BodyColor));

Font checkBoxFont5 = checkBox5.getFont();
checkBox5.setFont(new Font(checkBoxFont5.getName(), checkBoxFont5.getStyle(), 20));  // 20 ist die gewünschte Schriftgröße
checkBox5.setForeground(Main.TextColor);

checkBox5.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        

        if (checkBox5.isSelected()) {
            checkBox5.setBackground((Main.BodyColor));
            isCrossVisible5 = true;


        } else {
            checkBox5.setIcon(null);  // Entferne das Icon
            isCrossVisible5 = false;
            
        }
    }
});

        
        
        


      
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
    
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 60, 5, 10);
        
        gbc.gridx = 0;
        buttonsPanel.add(checkBox1, gbc);

        gbc.gridx = 1;

        // buttonsPanel.add(label1, gbc);
        
        

        // Setze die gewünschten Positionen für Button 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(checkBox2, gbc);

        gbc.gridx = 1;

       //  buttonsPanel.add(label2, gbc);
    
        // Setze die gewünschten Positionen für Button 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonsPanel.add(checkBox3, gbc);


        gbc.gridx = 1;
;
    
        // Setze die gewünschten Positionen für Button 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonsPanel.add(checkBox4, gbc); 

         
        gbc.gridx = 1;

       

        gbc.gridx = 0;
        gbc.gridy = 4;
        buttonsPanel.add(checkBox5, gbc);
         
                         
        gbc.gridx = 1;

      


        bodyPanel.add(buttonsPanel, BorderLayout.WEST);

        JButton button6 = new JButton("Start");
button6.setPreferredSize(new Dimension(100, 50));
button6.setBackground(Main.defaultButton);
button6.setForeground(Main.TextColor);

buttonsPanel1.setLayout(new GridBagLayout());
GridBagConstraints gbcButton6 = new GridBagConstraints();

// Setze die gewünschte Position für Button 6
gbcButton6.gridx = 0;
gbcButton6.gridy = 1;  // Oder eine andere Zeile, wenn benötigt
gbcButton6.weighty = 1.0;  // Strecke in der vertikalen Richtung
gbcButton6.anchor = GridBagConstraints.SOUTHEAST;  // Ausrichtung unten rechts
gbcButton6.insets = new Insets(0, 0, 10, 10);  // 25 Pixel Abstand zur rechten Wand
buttonsPanel1.add(button6, gbcButton6);

bodyPanel.add(buttonsPanel1, BorderLayout.EAST);



button6.addActionListener(e -> main.newTestView(selectedElements, isCrossVisible1, isCrossVisible2, isCrossVisible3, isCrossVisible4, isCrossVisible5));

        
        content.add(bodyPanel);
    }

}
