package src.fx.screens.learning;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import VocabAPI.WordTypes.Adjective;
import VocabAPI.WordTypes.Noun;
import VocabAPI.WordTypes.Verb;
import VocabAPI.WordTypes.Vocab;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code VocabView.fxml}, replacing
 * {@link src.ui.screens.learning.VocabView}.
 *
 * <p>The Swing screen built a fixed 6x7 table for verbs and a separate one for
 * adjectives, and showed an empty grid for nouns and for any word whose forms
 * the parser could not generate. Here the table is built from whatever forms the
 * word actually has, and a word with none says so.
 */
public class VocabViewScreen extends FxScreen {

    private static final String[] PRONOUNS =
            {"Ich", "Du", "Er/Sie/Es", "Wir", "Ihr", "Sie"};
    private static final String[] TENSES =
            {"Präsens", "Imperfekt", "Perfekt", "Plusquamperfekt", "Futur I", "Futur II"};
    private static final String[] CASES =
            {"Nominativ", "Genitiv", "Dativ", "Akkusativ", "Ablativ"};

    private final Vocab vocab;

    @FXML private Label wordLabel;
    @FXML private Label translationLabel;
    @FXML private Label emptyLabel;
    @FXML private HBox genderBox;
    @FXML private ToggleButton maskulinumButton;
    @FXML private ToggleButton femininumButton;
    @FXML private TableView<List<String>> formsTable;

    public VocabViewScreen(Vocab vocab) {
        this.vocab = vocab;
    }

    @Override
    protected String fxmlPath() {
        return "VocabView.fxml";
    }

    @FXML
    private void initialize() {
        wordLabel.setText(vocab.getBasicForm());
        translationLabel.setText(String.join(", ", vocab.getGerman()));

        if (vocab instanceof Verb verb) {
            showVerb(verb);
        } else if (vocab instanceof Adjective) {
            genderBox.setVisible(true);
            genderBox.setManaged(true);
            onGenderChanged();
        } else if (vocab instanceof Noun noun) {
            showDeclension(noun.getDeklination());
        } else {
            showEmpty("Für dieses Wort sind keine Formen hinterlegt.");
        }
    }

    /** Adjective forms differ per gender; the Swing buttons did nothing. */
    @FXML
    private void onGenderChanged() {
        Adjective adjective = (Adjective) vocab;
        HashMap<String, ArrayList<String>> forms;
        if (maskulinumButton.isSelected()) {
            forms = adjective.getMaskulinum();
        } else if (femininumButton.isSelected()) {
            forms = adjective.getFemininum();
        } else {
            forms = adjective.getNeutrum();
        }
        showDeclension(forms);
    }

    private void showVerb(Verb verb) {
        List<List<String>> tenseColumns = List.of(
                verb.getPraesens(), verb.getImperfekt(), verb.getPerfekt(),
                verb.getPlusquamperfekt(), verb.getFuturI(), verb.getFuturII());

        List<List<String>> rows = new ArrayList<>();
        for (int row = 0; row < PRONOUNS.length; row++) {
            List<String> cells = new ArrayList<>();
            cells.add(PRONOUNS[row]);
            for (List<String> forms : tenseColumns) {
                cells.add(forms != null && row < forms.size() ? forms.get(row) : "");
            }
            rows.add(cells);
        }

        List<String> headers = new ArrayList<>();
        headers.add("Indikativ Aktiv");
        headers.addAll(List.of(TENSES));
        buildTable(headers, rows);
    }

    /** Renders a {"Singular": [...], "Plural": [...]} map as case rows. */
    private void showDeclension(HashMap<String, ArrayList<String>> forms) {
        if (forms == null || forms.isEmpty()) {
            // The parser only generates forms for some declension patterns.
            showEmpty("Für diese Form sind keine Deklinationen hinterlegt.");
            return;
        }

        List<String> singular = forms.getOrDefault("Singular", new ArrayList<>());
        List<String> plural = forms.getOrDefault("Plural", new ArrayList<>());

        List<List<String>> rows = new ArrayList<>();
        for (int row = 0; row < CASES.length; row++) {
            rows.add(List.of(
                    CASES[row],
                    row < singular.size() ? singular.get(row) : "",
                    row < plural.size() ? plural.get(row) : ""));
        }
        buildTable(List.of("Kasus", "Singular", "Plural"), rows);
    }

    /** Each row is a list of cells; column i reads index i of its row. */
    private void buildTable(List<String> headers, List<List<String>> rows) {
        emptyLabel.setVisible(false);
        emptyLabel.setManaged(false);
        formsTable.setVisible(true);
        formsTable.setManaged(true);

        formsTable.getColumns().clear();
        for (int i = 0; i < headers.size(); i++) {
            final int columnIndex = i;
            TableColumn<List<String>, String> column = new TableColumn<>(headers.get(i));
            column.setCellValueFactory(data -> new ReadOnlyStringWrapper(
                    columnIndex < data.getValue().size() ? data.getValue().get(columnIndex) : ""));
            column.setSortable(false);
            formsTable.getColumns().add(column);
        }
        formsTable.setItems(FXCollections.observableArrayList(rows));
        formsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void showEmpty(String message) {
        emptyLabel.setText(message);
        emptyLabel.setVisible(true);
        emptyLabel.setManaged(true);
        formsTable.setVisible(false);
        formsTable.setManaged(false);
    }
}
