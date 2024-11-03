package src;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import VocabAPI.WordTypes.Vocab;
import minigames.GameM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Stack;

import java.lang.reflect.*;


public class Main {

    //button colors Lightmode
    public static Color DefaultButton = Color.decode("#B7B7B7");
    public static Color HoverButton = Color.decode("#E1E1E1");
    public static Color ClickButton = Color.decode("#FAFAFA");
    public static Color BodyColor = Color.decode("#FFFFFF");
    public static Color SecondBodyColor = Color.decode("#DDDDDD");
    public static Color TextColor = Color.decode("#000000");

    //button colors Darkmode
    public Color defaultButtonDarkMode = Color.decode("#4d6190");
    public Color hoverButtonDarkMode = Color.decode("#4255ff");
    public Color clickButtonDarkMode = Color.decode("#2f3990");
    public static Color bodyColorDarkMode = Color.decode("#111827");
    public static Color SecondBodyColorDarkMode = Color.decode("#1f2937");
    public static Color textColorDarkMode = Color.decode("#f9fafb");

    private JFrame frame;
    private JPanel contentPane; 
    private boolean isDarkmode = false;

    private NavBar navBar;

    private Stack<JPanel> navigationHistory = new Stack<>();

    public Main() {
            
        System.setProperty("file.encoding", "UTF-8");

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

        // UI Content Pane
        contentPane = new JPanel(new BorderLayout());
        globalPane.add(contentPane, BorderLayout.CENTER);

        // Navigation bar
        navBar = new NavBar(globalPane, this);

        // create the mainMenu
        this.newSetup();
        repaint();

        // Refresh screen to make stuff show up
        globalPane.revalidate();
        globalPane.repaint();

        frame.setVisible(true);

    }


    private void activateDarkMode() {
        DefaultButton = defaultButtonDarkMode;
        HoverButton = hoverButtonDarkMode;
        ClickButton = clickButtonDarkMode;
        BodyColor = bodyColorDarkMode;
        SecondBodyColor = SecondBodyColorDarkMode;
        TextColor = textColorDarkMode;

        // Weitere Dark-Mode-Anpassungen hier...
    }

    private void deactivateDarkMode() {
        DefaultButton = defaultButtonLightMode;
        HoverButton = hoverButtonLightMode;
        ClickButton = clickButtonLightMode;
        BodyColor = BodyColorLightMode;
        SecondBodyColor = SecondBodyColorLightMode;
        TextColor = TextColorLightMode;

        // Weitere Anpassungen für den normalen Modus hier...
    }

    public void toggleDarkmode(){
        if (isDarkmode) {
            deactivateDarkMode();
        } else {
            activateDarkMode();
        }
        isDarkmode = !isDarkmode;
    }
    public boolean getDarkmodeState(){
        return isDarkmode;
    }


    public static void main(String[] args) {
        new Main();
    }

    public JPanel newUI() {
        navigationHistory.push(new JPanel(new BorderLayout()));
        return navigationHistory.peek();
    }
    public void goBack() {
        if (navigationHistory.size() > 2) {
            navigationHistory.pop();
            repaint();
        }
    }
    public void removeAll() {
        try {
            contentPane.removeAll();
        }
        catch (Exception e) {
        }
    }
    public void repaint() {
        removeAll();
        contentPane.add(navigationHistory.peek(), BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }
    public void newMainMenu() {
        new MainMenu(newUI(), this);
        repaint();
    }


    public void newSettingsMenu() {
        
        new Settings(newUI(), this);
        
        repaint();
    }
    public void newLearningView(String lektion) {
        
        new LearningView(newUI(), lektion, this);
        
        repaint();
    }
    public void newWrongLearningView(ArrayList<Integer> wrongVocabs) {
        
        new LearningView(newUI(), wrongVocabs, this);
        repaint();
    }
    public void newLearningSelection() {
        
        new LearningSelection(newUI(), this);
        
        repaint();
    }
    public void newLibraryMenu() {
        
        new LibraryView(newUI(), this);
        
        repaint();
    }
    public void newGamesMenu() {
        
        new GameSelection(newUI(), this);
        
        repaint();
    }
    public void newTextChecker() {
        
        new TextChecker(newUI());
        
        repaint();
    }
    public void newTestSelection() {
        
        new TestSelection(newUI(), this);
        
        repaint();
    }
    public void newCredits() {
        
        new Credits(newUI());
        
        repaint();
    }
    public void newSetup() {
        
        new Setup(newUI(), this);
        
        repaint();
    }
    public void newPrivacyStatement() {
        
        new PrivacyStatement(newUI());
        
        repaint();
    }
    public void newVocabView(Vocab v) {
        
        new VocabView(newUI(), v);
        
        repaint();
    }
    public void newGameM(String lektion) {
        
        new GameM(newUI(), lektion, this);
        
        repaint();
    }
    public void newMemorySelection() {
        
        new MemorySelection(newUI(), this);
        
        repaint();
    }
    public void newSearchView(String query) {
        
        new SearchView(newUI(), this, query);
        
        repaint();
    }
    public void newCustomLessonView() {
        
        new CustomLessonView(newUI(), this);
        
        repaint();
    }

    public void newTestView(ArrayList<String> selectedElements, boolean isCrossVisible1, boolean isCrossVisible2,boolean isCrossVisible3, boolean isCrossVisible4, boolean isCrossVisible5) {
        
        new TestView(newUI(), null, selectedElements, isCrossVisible1, isCrossVisible2, isCrossVisible3, isCrossVisible4, isCrossVisible5);
        
        repaint();
    }



    
    public JFrame getFrame() {
        return frame;
    }
    public NavBar getNavBar() {
        return navBar;
    }
}
