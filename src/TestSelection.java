package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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

        JButton button1 = new JButton("");
        button1.setPreferredSize(new Dimension(50, 50));
        button1.setBackground(Main.defaultButton);
        button1.setForeground(Main.TextColor);
    
        button1.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                isCrossVisible1 = !isCrossVisible1;
    
                if (isCrossVisible1) {
                    // Ändere die Hintergrundfarbe des Buttons auf Weiß
                    button1.setBackground(Main.defaultButton);
    
                    // Zeige das Kreuz auf dem Button
                    button1.setText("");
                    button1.setIcon(new ImageIcon(createCrossImage()));
                } else {
                    // Setze die Hintergrundfarbe des Buttons zurück
                    button1.setBackground(Main.defaultButton);
    
                    // Entferne das Kreuz vom Button
                    button1.setText("");
                    button1.setIcon(null);
                }
            }
        });

        JButton button2 = new JButton("");
        button2.setPreferredSize(new Dimension(50, 50));
        button2.setBackground(Main.defaultButton);
        button2.setForeground(Main.TextColor);
    
        button2.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                isCrossVisible2 = !isCrossVisible2;
    
                if (isCrossVisible2) {
                    // Ändere die Hintergrundfarbe des Buttons auf Weiß
                    button2.setBackground(Main.defaultButton);
    
                    // Zeige das Kreuz auf dem Button
                    button2.setText("");
                    button2.setIcon(new ImageIcon(createCrossImage()));
                } else {
                    // Setze die Hintergrundfarbe des Buttons zurück
                    button2.setBackground(Main.defaultButton);
    
                    // Entferne das Kreuz vom Button
                    button2.setText("");
                    button2.setIcon(null);
                }
            }
        });

        JButton button3 = new JButton("");
        button3.setPreferredSize(new Dimension(50, 50));
        button3.setBackground(Main.defaultButton);
        button3.setForeground(Main.TextColor);
    
        button3.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                isCrossVisible3 = !isCrossVisible3;
    
                if (isCrossVisible3) {
                    // Ändere die Hintergrundfarbe des Buttons auf Weiß
                    button3.setBackground(Main.defaultButton);
    
                    // Zeige das Kreuz auf dem Button
                    button3.setText("");
                    button3.setIcon(new ImageIcon(createCrossImage()));
                } else {
                    // Setze die Hintergrundfarbe des Buttons zurück
                    button3.setBackground(Main.defaultButton);
    
                    // Entferne das Kreuz vom Button
                    button3.setText("");
                    button3.setIcon(null);
                }
            }
        });

        JButton button4 = new JButton("");
        button4.setPreferredSize(new Dimension(50, 50));
        button4.setBackground(Main.defaultButton);
        button4.setForeground(Main.TextColor);
    
        button4.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                isCrossVisible4 = !isCrossVisible4;
    
                if (isCrossVisible4) {
                    // Ändere die Hintergrundfarbe des Buttons auf Weiß
                    button2.setBackground(Main.defaultButton);
    
                    // Zeige das Kreuz auf dem Button
                    button4.setText("");
                    button4.setIcon(new ImageIcon(createCrossImage()));
                } else {
                    // Setze die Hintergrundfarbe des Buttons zurück
                    button4.setBackground(Main.defaultButton);
    
                    // Entferne das Kreuz vom Button
                    button4.setText("");
                    button4.setIcon(null);
                }
            }
        });

        JButton button5 = new JButton("");
        button5.setPreferredSize(new Dimension(50, 50));
        button5.setBackground(Main.defaultButton);
        button5.setForeground(Main.TextColor);
    
        button4.addActionListener(new ActionListener() {
          
    
            @Override
            public void actionPerformed(ActionEvent e) {
                isCrossVisible5 = !isCrossVisible5;
    
                if (isCrossVisible5) {
                    // Ändere die Hintergrundfarbe des Buttons auf Weiß
                    button5.setBackground(Main.defaultButton);
    
                    // Zeige das Kreuz auf dem Button
                    button5.setText("");
                    button5.setIcon(new ImageIcon(createCrossImage()));
                } else {
                    // Setze die Hintergrundfarbe des Buttons zurück
                    button5.setBackground(Main.defaultButton);
    
                    // Entferne das Kreuz vom Button
                    button5.setText("");
                    button5.setIcon(null);
                }
            }
        });


      

        JLabel label1 = new JLabel("Alle Übersetzungen abfragen");
        label1.setForeground(Main.TextColor);
        label1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        JLabel label2 = new JLabel("Reihenfologe der eingegebenen Übersetzungen ignorieren");
        label2.setForeground(Main.TextColor);
        label2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        JLabel label3 = new JLabel("Stamm formen von Verben abfragen");
        label3.setForeground(Main.TextColor);
        label3.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        JLabel label4 = new JLabel("zufällige Deklination abfragen");
        label4.setForeground(Main.TextColor);
        label4.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        JLabel label5 = new JLabel("zufällige Kunjugationen abfragen");
        label5.setForeground(Main.TextColor);
        label5.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

       



      
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
    
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 75, 5, 10);
        
        gbc.gridx = 0;
        buttonsPanel.add(button1, gbc);

        gbc.gridx = 1;

        buttonsPanel.add(label1, gbc);
        
        

        // Setze die gewünschten Positionen für Button 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(button2, gbc);

        gbc.gridx = 1;

        buttonsPanel.add(label2, gbc);
    
        // Setze die gewünschten Positionen für Button 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonsPanel.add(button3, gbc);


        gbc.gridx = 1;

        buttonsPanel.add(label3, gbc);
    
        // Setze die gewünschten Positionen für Button 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonsPanel.add(button4, gbc); 

         
        gbc.gridx = 1;

        buttonsPanel.add(label4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        buttonsPanel.add(button5, gbc);
         
                         
        gbc.gridx = 1;

        buttonsPanel.add(label5, gbc);

        

     

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

  
    
   
    

    private Image createCrossImage() {
        // Erstelle ein Bild mit einem roten Kreuz
        int size = 50;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Setze die Hintergrundfarbe auf Rot
        
    
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
    
        // Zeichne das Kreuz auf das Bild
        g2d.drawLine(5, 5, size - 5, size - 5);
        g2d.drawLine(size - 5, 5, 5, size - 5);
        g2d.dispose();
    
        return image;
    }

    
}
