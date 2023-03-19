module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.bunnykick.fx to javafx.fxml;
    exports me.bunnykick.fx;
}