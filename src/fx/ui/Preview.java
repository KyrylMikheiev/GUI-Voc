package src.fx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Opens a single screen by name, for visual checks. */
public class Preview extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        String which = System.getProperty("screen", "SignUp");
        FxScreen screen = switch (which) {
            case "Login" -> new src.fx.screens.auth.LoginScreen();
            case "SignUp" -> new src.fx.screens.auth.SignUpScreen();
            case "ForgotPassword" -> new src.fx.screens.auth.ForgotPasswordScreen();
            case "Verification" -> new src.fx.screens.auth.VerificationScreen("test@example.com");
            default -> new src.fx.screens.StartPageScreen();
        };
        BorderPane root = new BorderPane(screen.getView());
        root.getStyleClass().add("body-primary");
        Scene scene = new Scene(root, 1280, 720);
        ThemeManager.install(scene);
        if ("dark".equalsIgnoreCase(System.getProperty("vt.theme", ""))) {
            ThemeManager.setMode(ThemeManager.Mode.DARK);
        }
        stage.setScene(scene);
        stage.setTitle("Vokabeltrainer");
        stage.show();
    }
}
