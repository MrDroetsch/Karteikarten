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
import me.bunnykick.utils.Dialogues;

import java.net.URL;
import java.util.ResourceBundle;

import static me.bunnykick.utils.Const.MainView.*;

public class bnMain implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Text headerLabel;

    @FXML
    private TreeTableView<String> treeView;
    @FXML
    TreeTableColumn<String, String> dateCol;
    @FXML
    TreeTableColumn<String, String> headerCol;

    // Root
    TreeItem<String> years = new TreeItem<>("Jahre");

    // Years
    TreeItem<String> y23 = new TreeItem<>("2023");

    // Months
    TreeItem<String> jan23 = new TreeItem<>(TXT_JAN);
    TreeItem<String> feb23 = new TreeItem<>(TXT_FEB);
    TreeItem<String> mar23 = new TreeItem<>(TXT_MAR);
    TreeItem<String> apr23 = new TreeItem<>(TXT_APR);
    TreeItem<String> may23 = new TreeItem<>(TXT_MAY);
    TreeItem<String> jun23 = new TreeItem<>(TXT_JUN);
    TreeItem<String> jul23 = new TreeItem<>(TXT_JUL);
    TreeItem<String> aug23 = new TreeItem<>(TXT_AUG);
    TreeItem<String> sep23 = new TreeItem<>(TXT_SEP);
    TreeItem<String> oct23 = new TreeItem<>(TXT_OCT);
    TreeItem<String> nov23 = new TreeItem<>(TXT_NOV);
    TreeItem<String> dec23 = new TreeItem<>(TXT_DEC);

    @FXML
    private Button addNote;
    @FXML
    private Button remNote;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText(HEADER_TEXT);
        addNote.setText(BTN_ADD_TEXT);
        remNote.setText(BTN_REM_TEXT);

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

        dateCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
    }

    public void handleAddNoteButton(ActionEvent event) {
        Dialogues.showDialog("Kommt bald! TODO: DB umstrukturieren", Alert.AlertType.INFORMATION);
    }

    public void handleRemoveNoteButton(ActionEvent event) {
        Dialogues.showDialog("Kommt bald! TODO: DB umstrukturieren\nSelected: " + jan23.equals(treeView.getSelectionModel().getSelectedItem()), Alert.AlertType.INFORMATION);
    }

}
