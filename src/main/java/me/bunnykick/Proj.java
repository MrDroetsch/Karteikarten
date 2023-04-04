package me.bunnykick;

import javafx.application.Application;
import javafx.stage.Stage;
import me.bunnykick.db.DB;
import me.bunnykick.db.User;
import me.bunnykick.screens.Login;
import me.bunnykick.screens.MainView;
import me.bunnykick.sql.ConnectionPool;

import java.io.IOException;

public class Proj extends Application {

    private Stage stage;

    private ConnectionPool conPool;
    private DB db;

    private Login login;
    private MainView mainView;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        if(!sqLiteConnect()) System.exit(0);

        showLogin();
    }

    private boolean sqLiteConnect() {
        conPool = ConnectionPool.getInstance();
        try {
            db = new DB(conPool);
            return true;
        } catch (Exception e) {
            System.err.println("MySQL Connection failed: " + e.getMessage());
            return false;
        }
    }

    private void showLogin() throws IOException {
        login = new Login(db, this);
        login.showLoginScreen(stage);
    }

    public void showMainView(User user) throws IOException {
        mainView = new MainView(db, user, this);
        mainView.showMainView(stage);
    }

}
