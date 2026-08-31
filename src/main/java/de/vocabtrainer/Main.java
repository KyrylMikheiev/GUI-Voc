package de.vocabtrainer;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application entry point.
 *
 * <p>JavaFX owns the lifecycle, so startup work happens in
 * {@link #start(Stage)} once the toolkit is running and the primary Stage
 * exists.
 */
public class Main extends Application {

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        App.run(primaryStage);
    }
}
