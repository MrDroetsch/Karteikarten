package me.bunnykick.fx;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    // root
    public GridPane grid;

    // headerLabel
    public Text headerLabel;

    // labels
    public Label userNameLabel;
    public Label passwordLabel;

    // TextFields
    public TextField userNameTextField;
    public PasswordField passwordField;

    // Buttons
    public Button signInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText("Willkommen");
        userNameLabel.setText("User Name:");
        passwordLabel.setText("Passwort:");
        signInButton.setText("Einloggen");
    }

    public void handleLoginButton(ActionEvent actionEvent) {

    }
}