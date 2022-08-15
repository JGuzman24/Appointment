package Main;

import helper.DBAppointment;
import helper.DBCustomer;
import helper.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public Label language;
    public Label location;
    public TextField loginUser;
    public TextField loginPassword;

    private static final SimpleDateFormat time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    public Button login;
    public Button reset;
    public Label Title;
    public Label languageLabel;
    public Label sysinfo;
    public Label timezone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Locale currentLocale = Locale.getDefault();
        ResourceBundle resource = ResourceBundle.getBundle("Main.lang", currentLocale);

        Title.setText(resource.getString("Login"));
        login.setText(resource.getString("Login"));
        reset.setText(resource.getString("Reset"));
        languageLabel.setText(resource.getString("Language"));
        timezone.setText(resource.getString("Timezone"));
        sysinfo.setText(resource.getString("Systeminformation"));
        loginUser.setPromptText(resource.getString("Username"));
        loginPassword.setPromptText(resource.getString("Password"));



        language.setText(String.valueOf(currentLocale.getLanguage()));

        location.setText(String.valueOf(ZoneId.systemDefault()));





    }
    public void login(ActionEvent actionEvent) throws IOException {
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter loginActivity = new PrintWriter(fileWriter);

        String username = loginUser.getText();
        System.out.println("Checking User: "+ username);
        String password = loginPassword.getText();
        System.out.println("Checking Password: "+ password);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Boolean verified = DBUser.userCredentials(username, password);

        String loginAttempt = "Login attempt at: " + time.format(currentTime) + " -- Username: " + username + " -- Password: " + password + " -- Valid: " + verified;

        System.out.println("Logging: " + loginAttempt);

        loginActivity.println(loginAttempt);
        loginActivity.close();


        if(verified) {

            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }

    }

    public void reset(ActionEvent actionEvent) {
        loginUser.setText(null);
        loginPassword.setText(null);
    }
}
