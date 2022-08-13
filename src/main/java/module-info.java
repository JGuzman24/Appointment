module guzman.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens Main to javafx.graphics, javafx.fxml, javafx.base;
    opens Model to javafx.graphics, javafx.fxml, javafx.base;
    opens helper to javafx.graphics, javafx.fxml, javafx.base;

    exports Main;
}