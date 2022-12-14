package Main;

import Model.Country;
import Model.Division;
import Model.User;
import helper.DBDivision;
import helper.DBUser;
import helper.JDBC;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Main
 *
 */
public class main extends Application {
    /** Starts the JavaFX login screen
     * loads system locale and language
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Locale currentLocale = Locale.getDefault();
        ResourceBundle resource = ResourceBundle.getBundle("Main.lang", currentLocale);

        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 270, 429);
        stage.setTitle(resource.getString("Login"));
        stage.setScene(scene);
        stage.show();


    }

    /** Launches program and opens database connection
     * @param args
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        System.out.println(Locale.getDefault());

        launch();

        JDBC.closeConnection();
    }


}