package src.ui.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.App;
import src.ui.ColorManager;
import src.ui.screens._BaseScreen;
import src.ui.screens.games.GameSelection;
import src.ui.screens.learning.LearningSelection;
import src.ui.screens.learning.LibraryView;
import src.ui.screens.settings.Settings;
import src.ui.screens.test.TestSelection;
import src.ui.screens.test.TextChecker;
import src.ui.helper.*;

public class StartPage extends _BaseScreen {
    public StartPage() {
        super();
    }

    @Override
    public JPanel createUI() {
        JPanel bodyPanel = new JPanel();
        
        bodyPanel.setLayout(new GridLayout(1, 2, 0, 0));
        bodyPanel.setBackground(ColorManager.bodyPrimary());
        
        JPanel body_contentLeft = new JPanel();
        body_contentLeft.setOpaque(false);
        body_contentLeft.setBorder(new ResponsiveBorder(100, 80, 100, 80));       
        body_contentLeft.setLayout(new GridLayout(4, 1, 0, App.getFrameSize()[1] / 30));
        body_contentLeft.setLayout(new GridLayout(6, 1, 0, 15));
        
        //icons
        ImageIcon learnI, testI, libraryI, textI, gamesI, settingsI;
        learnI = new ImageIcon("resources/images/card-index.png");
        testI = new ImageIcon("resources/images/exam-results.png");
        libraryI = new ImageIcon("resources/images/digital-library.png");
        textI = new ImageIcon("resources/images/text-frame.png");
        gamesI = new ImageIcon("resources/images/gamepad.png");
        settingsI = new ImageIcon("resources/images/settings-gear-icon.png");

        ImageIcon[] icons = {learnI, testI, libraryI, textI, gamesI, settingsI};
        for (ImageIcon icon : icons) {
            icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        }
        
        JButton learn, games, settings, library, test, text;
        learn = new JButton("Lernen", learnI);
        test = new JButton("Testen", testI);
        library = new JButton("Bibliothek", libraryI);
        text = new JButton("Textchecker", textI);
        games = new JButton("Minispiele", gamesI);
        settings = new JButton("Einstellungen", settingsI);


        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setBackground(ColorManager.buttonHover());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setBackground(ColorManager.buttonDefault());
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    e.getComponent().setBackground(ColorManager.buttonClick());
                    //do something
                    if (e.getComponent() == learn) {
                        App.switchScreen(new LearningSelection());
                    }
                    else if (e.getComponent() == library) {
                        App.switchScreen(new LibraryView());
                    }
                    else if (e.getComponent() == games) {
                        App.switchScreen(new GameSelection());
                    }
                    else if (e.getComponent() == settings) {
                        App.switchScreen(new Settings());
                    }
                    else if (e.getComponent() == test) {
                        App.switchScreen(new TestSelection());
                    }
                    else if (e.getComponent() == text) {
                        App.switchScreen(new TextChecker());
                    }
                });
            }

        };

        JButton[] buttons = {learn, test, library, text, games, settings};
        for(JButton button : buttons) {
            button.setBorder(null);
            button.setFont(new Font(Font.SANS_SERIF, 0, 20));
            button.setFocusPainted(false);
            button.setLayout(new BorderLayout());
            button.setBackground(ColorManager.buttonDefault());
            button.setForeground(ColorManager.text());
            button.setHorizontalTextPosition(JButton.RIGHT);
            button.setVerticalTextPosition(JButton.CENTER);
            button.addMouseListener(mouseListener);
            body_contentLeft.add(button);
        }

        JPanel bodyPanel_contentRight = new JPanel();
        bodyPanel_contentRight.setOpaque(false);
        bodyPanel_contentRight.setBorder(new ResponsiveBorder(100, 30, 100, 80));
        bodyPanel_contentRight.setLayout(new BorderLayout());

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.setOpaque(false);
        textAreaPanel.setBorder(BorderFactory.createLineBorder(ColorManager.text(), 3));

        JTextArea textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setBorder(new ResponsiveBorder(10, 10, 10, 10));
        textArea.setFont(new Font(Font.SANS_SERIF, 0, 25));
        textArea.setForeground(ColorManager.text());
        textArea.setText("Hier kommt die Erklärung für jeden Button, wenn man darüber hovert, \nund das Widget Fenster zu");

        textAreaPanel.add(textArea, BorderLayout.CENTER);
        bodyPanel_contentRight.add(textAreaPanel, BorderLayout.CENTER);
        
        bodyPanel.add(body_contentLeft);
        bodyPanel.add(bodyPanel_contentRight);

        return bodyPanel;
    }
}