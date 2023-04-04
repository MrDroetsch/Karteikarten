package me.bunnykick.screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.bunnykick.Proj;
import me.bunnykick.bean.bnMain;
import me.bunnykick.db.DB;
import me.bunnykick.db.User;

import java.io.IOException;

public class MainView {

    private Proj proj;
    private DB db;
    private User user;
    private Stage stage;

    public MainView(DB db, User user, Proj proj) {
        this.db = db;
        this.proj = proj;
        this.user = user;
    }

    public void showMainView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Login.class.getResource("main-view.fxml"));

        bnMain mainView = new bnMain(user);
        loader.setController(mainView);

        Scene scene = new Scene(loader.load());
        stage.setTitle("MainView - Version " + db.getVersion());
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        stage.centerOnScreen();
        this.stage = stage;
    }

}
