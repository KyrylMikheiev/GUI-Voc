package src.fx.screens.test;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.ArrayList;
import java.util.List;

import src.fx.FxApp;
import src.fx.ui.FxScreen;
import src.fx.ui.Lessons;

/**
 * Controller for {@code TestSelection.fxml}, replacing
 * {@link src.ui.screens.test.TestSelection} and the LessonRoster helper it used.
 *
 * <p>The five options were five {@code isCrossVisibleN} fields set by five
 * copied listeners; they are now read straight off the check boxes into a
 * {@link TestOptions} record, so the call to TestView no longer passes five
 * unlabelled booleans in a row.
 */
public class TestSelectionScreen extends FxScreen {

    @FXML private ListView<String> availableList;
    @FXML private ListView<String> selectedList;

    @FXML private CheckBox allTranslations;
    @FXML private CheckBox ignoreOrder;
    @FXML private CheckBox verbStems;
    @FXML private CheckBox randomDeclension;
    @FXML private CheckBox randomConjugation;

    @FXML
    private void initialize() {
        availableList.setItems(FXCollections.observableArrayList(
                Lessons.all().stream().map(l -> "Lektion " + l).toList()));
        selectedList.setItems(FXCollections.observableArrayList());

        availableList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @Override
    protected String fxmlPath() {
        return "TestSelection.fxml";
    }

    @FXML
    private void onAdd() {
        move(availableList, selectedList);
    }

    @FXML
    private void onRemove() {
        move(selectedList, availableList);
    }

    /**
     * Moves the selection between the two lists, keeping the destination in the
     * canonical lesson order. LessonRoster kept a third "original" model to
     * restore that order on removal; sorting by the known order is enough.
     */
    private void move(ListView<String> from, ListView<String> to) {
        List<String> moving = new ArrayList<>(from.getSelectionModel().getSelectedItems());
        if (moving.isEmpty()) {
            return;
        }
        from.getItems().removeAll(moving);
        to.getItems().addAll(moving);

        List<String> order = Lessons.all().stream().map(l -> "Lektion " + l).toList();
        to.getItems().sort((a, b) -> Integer.compare(order.indexOf(a), order.indexOf(b)));

        from.getSelectionModel().clearSelection();
    }

    @FXML
    private void onStart() {
        FxApp.switchScreen(new TestViewScreen(
                new ArrayList<>(selectedList.getItems()), new TestOptions(
                allTranslations.isSelected(),
                ignoreOrder.isSelected(),
                verbStems.isSelected(),
                randomDeclension.isSelected(),
                randomConjugation.isSelected())));
    }
}
