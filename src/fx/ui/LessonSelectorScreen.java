package src.fx.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Base for screens that ask the user to pick a lesson, replacing
 * {@link src.ui.helper.LessonSelector}.
 *
 * <p>The Swing version put the buttons in a {@code GridLayout(4, 1)} and then
 * added the panel to the screen twice — once directly and once inside a scroll
 * pane — so the list rendered above its own scrolling copy. Here the buttons go
 * into a scrolling VBox once.
 */
public abstract class LessonSelectorScreen extends FxScreen {

    @FXML private Label titleLabel;
    @FXML private VBox lessonBox;

    @Override
    protected String fxmlPath() {
        return "LessonSelector.fxml";
    }

    /** The FXML lives beside this base class, not beside the subclass. */
    @Override
    protected Class<?> fxmlOwner() {
        return LessonSelectorScreen.class;
    }

    /** Heading above the list; override to change it. */
    protected String title() {
        return "Bitte wählen Sie eine Lektion aus:";
    }

    @FXML
    private void initialize() {
        titleLabel.setText(title());

        for (String lesson : Lessons.all()) {
            Button button = new Button("Lektion " + lesson);
            button.getStyleClass().add("app-button");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(e -> execute(lesson));
            lessonBox.getChildren().add(button);
        }
    }

    /** Called with the chosen lesson. */
    public abstract void execute(String lesson);
}
