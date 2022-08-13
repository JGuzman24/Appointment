package guzman.c195;

import Model.Users;
import helper.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public Label language;
    public Label location;
    public TextField loginUser;
    public TextField loginPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        Locale currentLocale = Locale.getDefault();
        currentLocale = Locale.CANADA_FRENCH;
        //ResourceBundle resource = ResourceBundle.getBundle(currentLocale);
        language.setText(String.valueOf(currentLocale));

        location.setText(String.valueOf(ZoneId.systemDefault()));



    }
    public void login(ActionEvent actionEvent) throws SQLException {

        String username = loginUser.getText();
        System.out.println("Checking User: "+ username);
        String password = loginPassword.getText();
        System.out.println("Checking Password: "+ password);
        DBUser.userCredentials(username, password);

    }

    public void reset(ActionEvent actionEvent) {
        loginUser.setText(null);
        loginPassword.setText(null);
    }
}
