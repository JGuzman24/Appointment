package Main;

import Model.Country;
import Model.User;
import helper.DBCountry;
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

public class main extends Application {
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

    public static void main(String[] args) {
        JDBC.openConnection();
        System.out.println(Locale.getDefault());

        ObservableList<Country> countryList = DBCountry.getAllCountries();
        ObservableList<User> userList = DBUser.getAllUsers();

        for (Country C : countryList){
            System.out.println("Country ID: " + C.getId() + "  Name: " + C.getName());
        }
        for (User U : userList){
            System.out.println("User ID: " + U.getId() + "  Username: " + U.getName() + "  Password: " + U.getPassword());
        }

        launch();

        JDBC.closeConnection();
    }


}