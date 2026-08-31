package src.fx.screens.learning;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;
import src.api.APIClient;
import src.fx.FxApp;
import src.fx.screens.StartPageScreen;
import src.fx.ui.FxScreen;
import src.fx.ui.Images;

/**
 * Controller for {@code LearningView.fxml}.
 *
 * <p>The flashcard and the results view are both in the FXML; the controller
 * toggles between them.
 */
public class LearningViewScreen extends FxScreen {

    private final List<Integer> vocabs;
    private final String lesson;

    private final List<Integer> wrongVocabs = new ArrayList<>();
    private final List<Integer> rightVocabs = new ArrayList<>();

    private int currentIndex;
    private boolean showingFront = true;

    /** Vocabulary sorted by ID; the indices in {@link #vocabs} point into this. */
    private List<Vocab> vocabById;

    @FXML private VBox cardView;
    @FXML private VBox resultView;

    @FXML private Label lessonLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Label progressLabel;
    @FXML private Button backArrowButton;
    @FXML private Button crossButton;
    @FXML private Button checkButton;
    @FXML private Button flashcard;

    @FXML private Label congratulationsLabel;
    @FXML private Label rightLabel;
    @FXML private Label wrongLabel;
    @FXML private Label perfectLabel;
    @FXML private Button relearnButton;

    /** Learn a whole lesson. */
    public LearningViewScreen(String lesson) {
        this.lesson = lesson;
        this.vocabs = new ArrayList<>();
        for (Vocab vocab : VocabParser.getVocabsFromLesson(lesson)) {
            vocabs.add(vocab.getID() - 1);
        }
        setKeepInHistory(false);
    }

    /** Re-learn a specific set, used by the "wrong vocabulary" button. */
    public LearningViewScreen(List<Integer> vocabs) {
        this.lesson = "Falsche Vokabeln";
        this.vocabs = new ArrayList<>(vocabs);
        setKeepInHistory(false);
    }

    @Override
    protected String fxmlPath() {
        return "LearningView.fxml";
    }

    @FXML
    private void initialize() {
        vocabById = VocabParser.getAllVocabs().stream()
                .sorted(Comparator.comparingInt(Vocab::getID))
                .collect(Collectors.toList());

        backArrowButton.setGraphic(Images.view("arrowpurple.png", 40, 40));
        crossButton.setGraphic(Images.view("close.png", 160, 160));
        checkButton.setGraphic(Images.view("checkmark.png", 160, 160));

        lessonLabel.setText(lesson.equals("Falsche Vokabeln") ? lesson : "Lektion " + lesson);

        if (vocabs.isEmpty()) {
            showResults();
            return;
        }

        updateCard();
        updateProgress();
    }

    @FXML
    private void onFlip() {
        showingFront = !showingFront;
        updateCard();
    }

    @FXML
    private void onPrevious() {
        if (currentIndex == 0) {
            return;
        }
        currentIndex--;
        showingFront = true;

        // Undo the answer recorded for the card we are stepping back to.
        // Note remove(Object), not remove(int): these lists hold indices, so
        // remove(int) would delete the wrong element.
        Integer vocabId = vocabs.get(currentIndex);
        wrongVocabs.remove(vocabId);
        rightVocabs.remove(vocabId);

        updateCard();
        updateProgress();
    }

    @FXML
    private void onWrong() {
        wrongVocabs.add(vocabs.get(currentIndex));
        advance();
    }

    @FXML
    private void onCorrect() {
        rightVocabs.add(vocabs.get(currentIndex));
        advance();
    }

    @FXML
    private void onRelearn() {
        FxApp.switchScreen(new LearningViewScreen(new ArrayList<>(wrongVocabs)));
    }

    @FXML
    private void onMainMenu() {
        FxApp.switchScreen(new StartPageScreen());
    }

    private void advance() {
        currentIndex++;
        showingFront = true;
        updateProgress();

        if (currentIndex >= vocabs.size()) {
            APIClient.updateUserVocabStats(new ArrayList<>(wrongVocabs),
                    new ArrayList<>(rightVocabs));
            showResults();
        } else {
            updateCard();
        }
    }

    private void updateCard() {
        Vocab vocab = vocabById.get(vocabs.get(currentIndex));
        flashcard.setText(showingFront
                ? vocab.getBasicForm()
                : String.join(", ", vocab.getGerman()));
    }

    private void updateProgress() {
        double fraction = vocabs.isEmpty() ? 1 : (double) currentIndex / vocabs.size();
        progressBar.setProgress(fraction);
        progressLabel.setText(Math.round(fraction * 100) + "%");
    }

    private void showResults() {
        congratulationsLabel.setText(
                "Herzlichen Glückwunsch! Du hast die Lektion " + lesson + " beendet!\n"
                        + "Hier sind deine Ergebnisse:");
        rightLabel.setText("Richtig: " + rightVocabs.size());
        wrongLabel.setText("Falsch: " + wrongVocabs.size());

        boolean perfect = wrongVocabs.isEmpty();
        relearnButton.setDisable(perfect);
        perfectLabel.setVisible(perfect);
        perfectLabel.setManaged(perfect);

        cardView.setVisible(false);
        cardView.setManaged(false);
        resultView.setVisible(true);
        resultView.setManaged(true);
    }
}
