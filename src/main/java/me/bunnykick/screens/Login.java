package me.bunnykick.screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.bunnykick.Proj;
import me.bunnykick.bean.bnLogin;
import me.bunnykick.db.DB;

import javax.security.auth.callback.Callback;
import java.io.IOException;

public class Login {

    private Proj proj;
    private DB db;

    public Login(DB db, Proj proj) {
        this.db = db;
        this.proj = proj;
    }

    public void showLoginScreen(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Login.class.getResource("login-screen.fxml"));

        bnLogin loginController = new bnLogin(proj);
        loader.setController(loginController);

        Scene scene = new Scene(loader.load());
        stage.setTitle("Login - Version " + db.getVersion());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}