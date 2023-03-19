package me.bunnykick.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Proj extends Application {

    private FXMLLoader currentLoader;
    private Scene currentScene;

    @Override
    public void start(Stage stage) throws IOException {
        currentLoader = new FXMLLoader(Proj.class.getResource("login-screen.fxml"));
        currentScene = new Scene(currentLoader.load());
        stage.setTitle("Login");
        stage.setScene(currentScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}