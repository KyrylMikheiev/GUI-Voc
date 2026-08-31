package src.fx.ui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Owns the Stage and the single Scene, and switches between screens.
 *
 * <p>A root pane holds the navbar at the top and the current screen in the
 * centre, with a history stack behind the back button. The Scene and its root
 * are created once; a switch only replaces the center node.
 */
public class FxWindowManager {

    private final Stage stage;
    private final BorderPane root;
    private final Deque<FxScreen> screenHistory = new ArrayDeque<>();

    private FxScreen currentScreen;
    private FxNavBar navBar;

    public FxWindowManager(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();
        this.root.getStyleClass().add("body-primary");

        Scene scene = new Scene(root, 1280, 720);
        ThemeManager.install(scene);

        // temp?: press esc to quit
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                javafx.application.Platform.exit();
                System.exit(0);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Vokabeltrainer");
        stage.getIcons().add(Images.load("simplelogo.png"));
        stage.centerOnScreen();
    }

    public void show() {
        stage.show();
    }

    public void goBack() {
        if (!screenHistory.isEmpty()) {
            currentScreen = screenHistory.pop();
            render();
        }
    }

    public void pushNewScreen(FxScreen screen) {
        if (currentScreen != null && currentScreen.storeInHistory()) {
            screenHistory.push(currentScreen);
        }
        currentScreen = screen;
        render();
    }

    public void forceOverrideScreen(FxScreen screen) {
        currentScreen = screen;
        screenHistory.clear();
        render();
    }

    private void render() {
        if (currentScreen.hasNavbar()) {
            if (navBar == null) {
                navBar = new FxNavBar();
            }
            navBar.setBackEnabled(!screenHistory.isEmpty());
            root.setTop(navBar.getView());
        } else {
            root.setTop(null);
        }
        root.setCenter(currentScreen.getView());
    }

    public int[] getFrameSize() {
        return new int[]{(int) stage.getWidth(), (int) stage.getHeight()};
    }
}
