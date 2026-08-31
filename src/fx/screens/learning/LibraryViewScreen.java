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
 * Controller for {@code LibraryView.fxml}, replacing
 * {@link src.ui.screens.learning.LibraryView}.
 *
 * <p>Three bugs in the Swing version are fixed by construction here:
 * <ul>
 *   <li>the lesson count was hardcoded to 44 in the next-button and the combo
 *       box, so lessons past that index were unreachable and the label showed
 *       the index rather than the lesson name;</li>
 *   <li>the previous-button wrapped to {@code lessons.size() - 2}, skipping the
 *       last lesson;</li>
 *   <li>the combo box was added to the panel on each click and opened by a
 *       100 ms Timer.</li>
 * </ul>
 * All three follow from indexing {@link Lessons#all()} directly.
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
