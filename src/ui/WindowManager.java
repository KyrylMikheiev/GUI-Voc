package src.ui;

import javax.swing.*;

import src.ui.screens._BaseScreen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class WindowManager {
    private JFrame frame;
    private JPanel globalPane;
    private JPanel contentPane;

    private _BaseScreen currentScreen;
    private Stack<_BaseScreen> screenHistory;

    //private NavBar navBar;

    public WindowManager() {

        // Set the cross-platform look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.emptyHistory();
        this.createFrame();
        this.createGlobalPane();
        this.createContentPane();

        frame.setVisible(true);
    }

    private void createFrame() {
        // Window
        frame = new JFrame();
        frame.setIconImage(new ImageIcon("resources/images/simplelogo.png").getImage());
        frame.setTitle("Vokabeltrainer");
        //default settings
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
    }

    private void emptyHistory() {
        // Init screenHistory
        this.screenHistory = new Stack<_BaseScreen>();
    }

    private void createGlobalPane() {
        if (this.frame == null) {
            createFrame();
        }
        // Global Content pane
        globalPane = new JPanel();
        globalPane.setLayout(new BorderLayout());
        frame.setContentPane(globalPane);
    }

    private void createContentPane() {
        if (this.globalPane == null) {
            createGlobalPane();
        }

        // UI Content Pane
        contentPane = new JPanel(new BorderLayout());
        globalPane.add(contentPane, BorderLayout.CENTER);
    }

    public void goBack() {
        if (this.screenHistory.size() >= 1) {
            currentScreen = this.screenHistory.pop();
            this.repaint();
        }
    }

    public void pushNewScreen(_BaseScreen screen) {
        if (this.currentScreen != null && this.currentScreen.storeInHistory()) {
            this.screenHistory.push(currentScreen);
        }
        currentScreen = screen;
        this.repaint();
    }

    public void updateColors() {
        this.currentScreen.rebuildUI();
        this.repaint();
    }

    private void repaint() {
        this.createGlobalPane();
        if (currentScreen.hasNavbar()) {
            NavBar nav = new NavBar();
            this.globalPane.add(nav.get(), BorderLayout.NORTH);
        }
        this.createContentPane();
        this.globalPane.revalidate();
        this.globalPane.repaint();

        this.contentPane.add(currentScreen.getContentPanel(), BorderLayout.CENTER);
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    public void forceOverrideScreen(_BaseScreen screen) {
        this.currentScreen = screen;
        this.emptyHistory();
        this.repaint();
    }

    public int[] getFrameSize() {
        return new int[]{frame.getWidth(), frame.getHeight()};
    }
}

