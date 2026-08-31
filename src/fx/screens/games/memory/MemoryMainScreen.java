package src.fx.screens.games.memory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import VocabAPI.VocabParser;
import VocabAPI.WordTypes.Vocab;
import src.fx.FxApp;
import src.fx.screens.StartPageScreen;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code MemoryMain.fxml}, replacing
 * {@link src.ui.screens.games.memory.MemoryMain}.
 *
 * <p>Rules are unchanged: the board is shown face-up until the first click,
 * then hidden; a mismatched pair is turned back on the next click and counts a
 * fail; matching all pairs wins.
 *
 * <p>The Swing version placed pairs by drawing random slots in a
 * {@code while (!placed)} loop that retried until it hit two free ones, and
 * compared strings with {@code ==}. This shuffles a list of slots instead and
 * tracks pair membership by index, which is what {@code boardQ} was for.
 */
public class MemoryMainScreen extends FxScreen {

    private static final int COLUMNS = 4;

    private final String lesson;

    /** Pair index per slot; two slots share an index when they match. */
    private int[] pairOf;
    /** Face text per slot. */
    private String[] faces;
    private boolean[] solved;
    private Button[] buttons;

    private boolean boardShown;
    private int firstPick = -1;
    private int secondPick = -1;
    private boolean awaitingFlipBack;
    private int fails;

    @FXML private VBox menuPane;
    @FXML private VBox instructionsPane;
    @FXML private GridPane boardPane;
    @FXML private VBox winPane;
    @FXML private TextField amountField;
    @FXML private Label failsLabel;

    public MemoryMainScreen(String lesson) {
        this.lesson = lesson;
        setKeepInHistory(false);
    }

    @Override
    protected String fxmlPath() {
        return "MemoryMain.fxml";
    }

    @FXML
    private void onInstructions() {
        show(instructionsPane);
    }

    @FXML
    private void onBackToMenu() {
        show(menuPane);
    }

    @FXML
    private void onExit() {
        FxApp.switchScreen(new StartPageScreen());
    }

    @FXML
    private void onStart() {
        List<Vocab> available = VocabParser.getVocabsFromLesson(lesson);

        int pairs;
        try {
            pairs = Integer.parseInt(amountField.getText());
        } catch (NumberFormatException e) {
            pairs = 1;
        }
        pairs = Math.min(pairs, available.size());
        if (pairs < 1) {
            pairs = 1;
        }

        buildBoard(available, pairs);
        show(boardPane);
    }

    private void buildBoard(List<Vocab> available, int pairs) {
        int slots = pairs * 2;
        pairOf = new int[slots];
        faces = new String[slots];
        solved = new boolean[slots];
        buttons = new Button[slots];

        // Deal each pair into two random slots.
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < slots; i++) {
            order.add(i);
        }
        Collections.shuffle(order);

        for (int pair = 0; pair < pairs; pair++) {
            Vocab vocab = available.get(pair);
            int german = order.get(pair * 2);
            int latin = order.get(pair * 2 + 1);

            faces[german] = vocab.getGerman().get(0);
            faces[latin] = vocab.getBasicForm();
            pairOf[german] = pair;
            pairOf[latin] = pair;
        }

        boardPane.getChildren().clear();
        boardPane.getColumnConstraints().clear();
        boardPane.getRowConstraints().clear();

        int rows = (int) Math.ceil(slots / (double) COLUMNS);
        for (int c = 0; c < COLUMNS; c++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / COLUMNS);
            boardPane.getColumnConstraints().add(cc);
        }
        for (int r = 0; r < rows; r++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / rows);
            boardPane.getRowConstraints().add(rc);
        }

        for (int i = 0; i < slots; i++) {
            final int slot = i;
            Button button = new Button(faces[i]);
            button.getStyleClass().add("app-button");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMaxHeight(Double.MAX_VALUE);
            button.setMinSize(0, 0);
            button.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            button.setWrapText(true);
            button.setOnAction(e -> onSlotClicked(slot));
            buttons[i] = button;
            boardPane.add(button, i % COLUMNS, i / COLUMNS);
        }

        // The board starts face-up; the first click hides it.
        boardShown = true;
        firstPick = -1;
        secondPick = -1;
        awaitingFlipBack = false;
        fails = 0;
    }

    private void onSlotClicked(int slot) {
        // A pending mismatch is turned back before this click is handled.
        if (awaitingFlipBack) {
            hide(firstPick);
            hide(secondPick);
            fails++;
            firstPick = -1;
            secondPick = -1;
            awaitingFlipBack = false;
        }

        if (boardShown) {
            for (Button button : buttons) {
                button.setText("");
            }
            boardShown = false;
            return;
        }

        if (solved[slot]) {
            return;
        }

        if (firstPick == -1) {
            buttons[slot].setText(faces[slot]);
            firstPick = slot;
            return;
        }

        if (slot == firstPick) {
            // Clicking the same card again counts as a miss, as in Swing.
            secondPick = slot;
            awaitingFlipBack = true;
            return;
        }

        buttons[slot].setText(faces[slot]);

        if (pairOf[slot] == pairOf[firstPick]) {
            solved[slot] = true;
            solved[firstPick] = true;
            firstPick = -1;
            if (allSolved()) {
                failsLabel.setText("times failed: " + fails);
                show(winPane);
            }
        } else {
            secondPick = slot;
            awaitingFlipBack = true;
        }
    }

    private void hide(int slot) {
        if (slot >= 0 && !solved[slot]) {
            buttons[slot].setText("");
        }
    }

    private boolean allSolved() {
        for (boolean done : solved) {
            if (!done) {
                return false;
            }
        }
        return true;
    }

    /** Shows one state pane and hides the others. */
    private void show(javafx.scene.Node target) {
        for (javafx.scene.Node pane : new javafx.scene.Node[]{
                menuPane, instructionsPane, boardPane, winPane}) {
            boolean visible = pane == target;
            pane.setVisible(visible);
            pane.setManaged(visible);
        }
    }
}
