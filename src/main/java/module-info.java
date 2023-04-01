module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens me.bunnykick.bean to javafx.fxml;
    exports me.bunnykick.bean;
    exports me.bunnykick;
    opens me.bunnykick to javafx.fxml;
    exports me.bunnykick.screens;
    opens me.bunnykick.screens to javafx.fxml;
}