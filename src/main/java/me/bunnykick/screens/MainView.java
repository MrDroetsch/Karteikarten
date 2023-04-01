package me.bunnykick.screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.bunnykick.Proj;
import me.bunnykick.bean.bnMain;
import me.bunnykick.db.DB;

import java.io.IOException;

public class MainView {

    private Proj proj;
    private DB db;
    private Stage stage;

    public MainView(DB db, Proj proj) {
        this.db = db;
        this.proj = proj;
    }

    public void showMainView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Login.class.getResource("main-view.fxml"));

        bnMain mainView = new bnMain();
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
