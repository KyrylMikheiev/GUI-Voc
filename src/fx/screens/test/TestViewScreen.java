package src.fx.screens.test;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code TestView.fxml}.
 *
 * <p>Ten words are drawn at random from the chosen lessons and
 * "Nächste Vokabel" cycles through them. Answers are not checked; the input
 * fields are never read, as before the JavaFX port.
 */
public class TestViewScreen extends FxScreen {

    private static final int TEST_SIZE = 10;

    private final List<Vocab> vocabs = new ArrayList<>();
    private final TestOptions options;

    private int currentIndex;

    @FXML private Label wordLabel;
    @FXML private VBox translationBox;
    @FXML private TextField nominativeField;
    @FXML private TextField dativeField;

    public TestViewScreen(List<String> selectedLessons, TestOptions options) {
        this.options = options;

        List<Vocab> all = new ArrayList<>();
        for (String entry : selectedLessons) {
            // Entries are "Lektion <name>"; strip the prefix.
            all.addAll(VocabParser.getVocabsFromLesson(entry.substring("Lektion ".length())));
        }
        Collections.shuffle(all);
        vocabs.addAll(all.subList(0, Math.min(TEST_SIZE, all.size())));
    }

    @Override
    protected String fxmlPath() {
        return "TestView.fxml";
    }

    @FXML
    private void initialize() {
        showCurrent();
    }

    @FXML
    private void onNext() {
        currentIndex++;
        if (currentIndex >= vocabs.size()) {
            currentIndex = 0;
        }
        showCurrent();
    }

    private void showCurrent() {
        if (vocabs.isEmpty()) {
            wordLabel.setText("Keine Vokabeln in den gewählten Lektionen.");
            translationBox.getChildren().clear();
            return;
        }

        Vocab vocab = vocabs.get(currentIndex);
        wordLabel.setText(vocab.getBasicForm());

        // One row per German translation.
        translationBox.getChildren().clear();
        for (int i = 0; i < vocab.getGerman().size(); i++) {
            Label label = new Label("Übersetzung " + (i + 1) + ":");
            label.getStyleClass().add("text-label");

            TextField field = new TextField();
            field.getStyleClass().add("test-field");
            field.setPrefWidth(200);

            HBox row = new HBox(10, label, field);
            row.setAlignment(Pos.CENTER_LEFT);
            translationBox.getChildren().add(row);
        }

        nominativeField.clear();
        dativeField.clear();
    }

    /** The options are carried but not yet acted on. */
    public TestOptions getOptions() {
        return options;
    }
}
