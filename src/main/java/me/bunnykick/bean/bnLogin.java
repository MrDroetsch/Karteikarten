package me.bunnykick.bean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import me.bunnykick.Proj;
import me.bunnykick.screens.Login;
import me.bunnykick.db.User;
import me.bunnykick.sql.ConnectionPool;
import me.bunnykick.sql.ConnectionPoolException;
import me.bunnykick.sql.MyConnection;
import me.bunnykick.sql.MyStatement;
import me.bunnykick.utils.Dialogues;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class bnLogin implements Initializable {

    private Proj proj;

    // root
    @FXML
    private GridPane grid;

    // headerLabel
    @FXML
    private Text headerLabel;

    // labels
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label errorLabel;

    // TextFields
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;

    // Buttons
    @FXML
    private Button signInButton;

    public bnLogin(Proj proj) {
        this.proj = proj;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText("Willkommen");
        userNameLabel.setText("User Name:");
        passwordLabel.setText("Passwort:");
        signInButton.setText("Einloggen");
        signInButton.setOnAction(this::handleLoginButton);
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        String userName = userNameTextField.getText();
        String password = passwordField.getText();

        User user;

        try(MyConnection con = ConnectionPool.getInstance().getConnection(); MyStatement stmt = con.createStatement()) {
            user = User.getUserFromDB(stmt, userName);
        } catch(SQLException | ConnectionPoolException e) {
            Dialogues.showErrorMessage("Fehler bei der Verbindung mit der Datenbank!", e);
            return;
        }

        if(user == null || !user.isCorrectPW(password)) {
            Dialogues.showDialog("Falscher Username oder Passwort!", Alert.AlertType.ERROR);
            return;
        }

        try {
            proj.showMainView();
        } catch (IOException e) {
            Dialogues.showErrorMessage("Fehler beim Wechseln der Scene! ", e);
            // return;
        }
    }
}