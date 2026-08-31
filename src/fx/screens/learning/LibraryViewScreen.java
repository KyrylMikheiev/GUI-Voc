package src.fx.screens.learning;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.util.List;

import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;
import src.fx.FxApp;
import src.fx.ui.FxScreen;
import src.fx.ui.Lessons;

/**
 * Controller for {@code LibraryView.fxml}.
 *
 * <p>The lesson list comes from {@link Lessons#all()} and is indexed directly,
 * so the arrows and the combo box always cover exactly the lessons that exist.
 * The pre-JavaFX version hardcoded a count of 44 here, which made later lessons
 * unreachable and made the previous-button skip the last one.
 */
public class LibraryViewScreen extends FxScreen {

    private List<String> lessons;

    @FXML private ComboBox<String> lessonBox;
    @FXML private VBox vocabBox;

    @Override
    protected String fxmlPath() {
        return "LibraryView.fxml";
    }

    @FXML
    private void initialize() {
        lessons = Lessons.all();

        lessonBox.setItems(FXCollections.observableArrayList(
                lessons.stream().map(l -> "Lektion " + l).toList()));
        lessonBox.getSelectionModel().selectedIndexProperty()
                .addListener((obs, old, index) -> showLesson(index.intValue()));
        lessonBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void onPrevious() {
        step(-1);
    }

    @FXML
    private void onNext() {
        step(1);
    }

    /** Wraps in both directions over the real lesson count. */
    private void step(int delta) {
        if (lessons.isEmpty()) {
            return;
        }
        int size = lessons.size();
        int next = (lessonBox.getSelectionModel().getSelectedIndex() + delta + size) % size;
        lessonBox.getSelectionModel().select(next);
    }

    private void showLesson(int index) {
        vocabBox.getChildren().clear();
        if (index < 0 || index >= lessons.size()) {
            return;
        }

        for (Vocab vocab : VocabParser.getVocabsFromLesson(lessons.get(index))) {
            Button button = new Button(
                    vocab.getBasicForm() + " - " + String.join(", ", vocab.getGerman()));
            button.getStyleClass().add("app-button");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(e -> FxApp.switchScreen(new VocabViewScreen(vocab)));
            vocabBox.getChildren().add(button);
        }
    }
}
