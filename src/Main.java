package src;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import VocabParsing.Vocab;
import minigames.GameM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Main {

    //button colors
    public static Color defaultButton = Color.decode("#4d6190");
    public static Color hoverButton = Color.decode("#4255ff");
    public static Color clickButton = Color.decode("#2f3990");
    public static Color BodyColor = Color.decode("#111827");
    public static Color TextColor = Color.decode("#f9fafb");
    private JFrame frame;
    private JPanel contentPane;
    
    public Main() {
        //new Database();
        // Set the cross-platform look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Global Content pane (including navbar)
        JPanel globalPane = new JPanel();
        globalPane.setLayout(new BorderLayout());

        // Window
        frame = new JFrame();
        frame.setIconImage(new ImageIcon("resources/images/logo.png").getImage());
        frame.setTitle("Vokabeltrainer");
        //default settings
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        //for testing gui
        // frame.setSize(850, 800);
        // frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth(), 200);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(globalPane);
        
        // temp?: press esc to quit
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Quit the application
            }
        };
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        frame.setVisible(true);

        // UI Content Pane
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        globalPane.add(contentPane, BorderLayout.CENTER);

        // Navigation bar
        new NavBar(globalPane, this);

        // create the mainMenu
        new MainMenu(contentPane, this);


        // Refresh screen to make stuff show up
        globalPane.revalidate();
        globalPane.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }

    public void newUI() {
        try {
            contentPane.removeAll();
        }
        catch (Exception e) {
        }
    }
    public void repaint() {
        contentPane.revalidate();
        contentPane.repaint();
    }
    public void newMainMenu() {
        newUI();
        new MainMenu(contentPane, this);
        repaint();
    }
    public void newSettingsMenu() {
        newUI();
        new Settings(contentPane, this);
        repaint();
    }
    public void newLearningView(String lektion) {
        newUI();
        new LearningView(contentPane, lektion, this);
        repaint();
    }
    public void newLearningSelection() {
        newUI();
        new LearningSelection(contentPane, this);
        repaint();
    }
    public void newLibraryMenu() {
        newUI();
        new LibraryView(contentPane, this);
        repaint();
    }
    public void newGamesMenu() {
        newUI();
        new GameSelection(contentPane, this);
        repaint();
    }
    public void newTestView(String lektion, int type) {
        newUI();
        new Test(contentPane, lektion, type);
        repaint();
    }
    public void newTextChecker() {
        newUI();
        new TextChecker(contentPane);
        repaint();
    }
    public void newTestSelection() {
        newUI();
        new TestSelection(contentPane);
        repaint();
    }
    public void newCredits(JPanel content) {
        newUI();
        new Credits(content);
        repaint();
    }
    public void newSetup() {
        newUI();
        new Setup(contentPane);
        repaint();
    }
    public void newPrivacyStatement() {
        newUI();
        new PrivacyStatement(contentPane);
        repaint();
    }
    public void newVocabView(Vocab v) {
        newUI();
        new VocabView(contentPane, v);
        repaint();
    }
    public void newGameM(String lektion) {
        newUI();
        new GameM(contentPane, lektion);
        repaint();
    }
    public void newMemorySelection() {
        newUI();
        new MemorySelection(contentPane, this);
        repaint();
    }

    public JFrame getFrame() {
        return frame;
    }
}
