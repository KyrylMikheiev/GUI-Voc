package src.fx.screens.learning;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import VocabAPI.WordTypes.Adjective;
import VocabAPI.WordTypes.Verb;
import VocabAPI.WordTypes.Vocab;
import src.fx.ui.FxScreen;

/**
 * Controller for {@code VocabView.fxml}.
 *
 * <p>A conjugation table for verbs and a declension table for adjectives.
 * Other word types get no table.
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
    @FXML private HBox genderBox;
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
        } else if (vocab instanceof Adjective adjective) {
            genderBox.setVisible(true);
            genderBox.setManaged(true);
            // The gender switch is not wired up; only Maskulinum is shown.
            showDeclension(adjective.getMaskulinum());
        }
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
        if (forms == null) {
            // The parser generates forms for only some declension patterns;
            // the rest get an empty grid.
            forms = new HashMap<>();
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

}
