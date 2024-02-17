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

public class Main {

    //button colors Lightmode
    public static Color DefaultButton = Color.decode("#B7B7B7");
    public static Color HoverButton = Color.decode("#E1E1E1");
    public static Color ClickButton = Color.decode("#FAFAFA");
    public static Color BodyColor = Color.decode("#FFFFFF");
    public static Color SecondBodyColor = Color.decode("#DDDDDD");
    public static Color TextColor = Color.decode("#000000");

    //button colors Darkmode
    private Color defaultButtonDarkMode = Color.decode("#4d6190");
    private Color hoverButtonDarkMode = Color.decode("#4255ff");
    private Color clickButtonDarkMode = Color.decode("#2f3990");
    private Color bodyColorDarkMode = Color.decode("#111827");
    public static Color SecondBodyColorDarkMode = Color.decode("#1f2937");
    private Color textColorDarkMode = Color.decode("#f9fafb");
    private Color defaultButtonLightMode = Color.decode("#B7B7B7");
    private Color hoverButtonLightMode = Color.decode("#E1E1E1");
    private Color clickButtonLightMode = Color.decode("#FAFAFA");
    private Color BodyColorLightMode = Color.decode("#FFFFFF");
    private Color SecondBodyColorLightMode = Color.decode("#DDDDDD");
    private Color TextColorLightMode = Color.decode("#000000");

    private JFrame frame;
    private JPanel contentPane; 
    private boolean isDarkmode = false;

    private NavBar navBar;

    public Main() {
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
    //public void newTestView(ArrayList<String> selectedElements, boolean isCrossVisible1, boolean isCrossVisible2, boolean isCrossVisible3, boolean isCrossVisible4, boolean isCrossVisible5) {
    //newUI();
    //new TestView(contentPane, this, );
    //repaint();
//}
    public void newTextChecker() {
        newUI();
        new TextChecker(contentPane);
        repaint();
    }
    public void newTestSelection() {
        newUI();
        new TestSelection(contentPane, this);
        repaint();
    }
    public void newCredits(JPanel content) {
        newUI();
        new Credits(content);
        repaint();
    }
    public void newSetup() {
        newUI();
        new Setup().startScreen(contentPane, this);
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
        new GameM(contentPane, lektion, this);
        repaint();
    }
    public void newMemorySelection() {
        newUI();
        new MemorySelection(contentPane, this);
        repaint();
    }
    public void newSearchView(String query) {
        newUI();
        new SearchView(contentPane, this, query);
        repaint();
    }
    public void newCustomLessonView() {
        newUI();
        new CustomLessonView(contentPane, this);
        repaint();
    }

    public JFrame getFrame() {
        return frame;
    }
    public NavBar getNavBar() {
        return navBar;
    }

    public void newTestView(ArrayList<String> selectedElements, boolean isCrossVisible1, boolean isCrossVisible2,
            boolean isCrossVisible3, boolean isCrossVisible4, boolean isCrossVisible5) {
        newUI();
        new TestView(contentPane, null, selectedElements, isCrossVisible1, isCrossVisible2, isCrossVisible3, isCrossVisible4, isCrossVisible5);
        repaint();
    }
}
