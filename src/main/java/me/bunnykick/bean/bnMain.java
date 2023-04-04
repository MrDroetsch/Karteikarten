package me.bunnykick.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.bunnykick.db.Note;
import me.bunnykick.db.User;
import me.bunnykick.sql.ConnectionPool;
import me.bunnykick.sql.ConnectionPoolException;
import me.bunnykick.sql.MyConnection;
import me.bunnykick.sql.MyStatement;
import me.bunnykick.utils.Dialogues;
import me.bunnykick.utils.DummyNote;
import me.bunnykick.utils.Month;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static me.bunnykick.utils.Const.MainView.*;

public class bnMain implements Initializable {

    private User user;

    public bnMain(User user) {
        this.user = user;
    }

    @FXML
    private BorderPane root;

    @FXML
    private Text headerLabel;

    @FXML
    private TreeTableView<Note> treeView;
    @FXML
    TreeTableColumn<Note, String> dateCol;
    @FXML
    TreeTableColumn<Note, String> headerCol;

    // Root
    TreeItem<Note> years = new TreeItem<>(new DummyNote("Jahre"));

    // Years
    TreeItem<Note> y23 = new TreeItem<>(new DummyNote("2023"));

    // Months
    TreeItem<Note> jan23 = new TreeItem<>(new DummyNote(TXT_JAN));
    TreeItem<Note> feb23 = new TreeItem<>(new DummyNote(TXT_FEB));
    TreeItem<Note> mar23 = new TreeItem<>(new DummyNote(TXT_MAR));
    TreeItem<Note> apr23 = new TreeItem<>(new DummyNote(TXT_APR));
    TreeItem<Note> may23 = new TreeItem<>(new DummyNote(TXT_MAY));
    TreeItem<Note> jun23 = new TreeItem<>(new DummyNote(TXT_JUN));
    TreeItem<Note> jul23 = new TreeItem<>(new DummyNote(TXT_JUL));
    TreeItem<Note> aug23 = new TreeItem<>(new DummyNote(TXT_AUG));
    TreeItem<Note> sep23 = new TreeItem<>(new DummyNote(TXT_SEP));
    TreeItem<Note> oct23 = new TreeItem<>(new DummyNote(TXT_OCT));
    TreeItem<Note> nov23 = new TreeItem<>(new DummyNote(TXT_NOV));
    TreeItem<Note> dec23 = new TreeItem<>(new DummyNote(TXT_DEC));

    @FXML
    private Button addNote;
    @FXML
    private Button remNote;
    @FXML
    private Button openNote;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText(HEADER_TEXT);
        addNote.setText(BTN_ADD_TEXT);
        remNote.setText(BTN_REM_TEXT);
        openNote.setText(BTN_OPEN_TEXT);

        addNote.setOnAction(this::handleAddNoteButton);
        remNote.setOnAction(this::handleRemoveNoteButton);

        initTreeView();
    }

    private void initTreeView() {
        // root
        treeView.setRoot(years);

        // Layer 1
        years.getChildren().add(y23);

        // Layer2
        y23.getChildren().addAll(jan23, feb23, mar23, apr23, may23, jun23, jul23, aug23, sep23, oct23, nov23, dec23);

        years.setExpanded(true);
        y23.setExpanded(true);

        dateCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getContent()));
        headerCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getTitle()));

        treeView.setShowRoot(false);
    }

    public void addNote(Note note, Month month) {
        switch(month) {
            case JANUARY -> addNote(note, jan23);
            case FEBRUARY -> addNote(note, feb23);
            case MARCH -> addNote(note, mar23);
            case APRIL -> addNote(note, apr23);
            case MAY -> addNote(note, may23);
            case JUNE -> addNote(note, jun23);
            case JULY -> addNote(note, jul23);
            case AUGUST -> addNote(note, aug23);
            case SEPTEMBER -> addNote(note, sep23);
            case OCTOBER -> addNote(note, oct23);
            case NOVEMBER -> addNote(note, nov23);
            case DECEMBER -> addNote(note, dec23);
        }
    }

    public void addNote(Note note, TreeItem<Note> parent) {
        parent.getChildren().add(new TreeItem<>(note));
    }

    private boolean isRightLayer(TreeItem<Note> selected) {
        return selected != null &&
                (selected.equals(jan23) ||
                selected.equals(feb23) ||
                selected.equals(mar23) ||
                selected.equals(apr23) ||
                selected.equals(may23) ||
                selected.equals(jun23) ||
                selected.equals(jul23) ||
                selected.equals(aug23) ||
                selected.equals(sep23) ||
                selected.equals(oct23) ||
                selected.equals(nov23) ||
                selected.equals(dec23));
    }

    public void handleAddNoteButton(ActionEvent event) {
        TreeItem<Note> selectedParent = treeView.getSelectionModel().getSelectedItem();
        if(!isRightLayer(selectedParent)) {
            Dialogues.showDialog("Bitte wähle zunächst einen Monat aus!");
            return;
        }

        Date create = new Date(System.currentTimeMillis());
        Note note = new Note("Titel", create, user.getId());
        System.out.println(note);

        try(MyConnection connection = ConnectionPool.getInstance().getConnection(); MyStatement stmt = connection.createStatement()) {
            note.save(stmt);
            connection.commit();
            addNote(note, selectedParent);
            selectedParent.setExpanded(true);
        } catch(SQLException | ConnectionPoolException e) {
            Dialogues.showErrorMessage("Fehler bei der Verbindung mit der Datenbank!", e);
        }
    }

    public void handleRemoveNoteButton(ActionEvent event) {
        Dialogues.showDialog("Kommt bald! TODO: DB umstrukturieren\nSelected: " + jan23.equals(treeView.getSelectionModel().getSelectedItem()), Alert.AlertType.INFORMATION);
    }

}
