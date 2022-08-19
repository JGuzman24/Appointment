package helper;


import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Handles functions between users and the database
 *
 */
public class DBUser {
    private static String currentUser = "";

    /** Verifies user credentials to log in
     * @param username
     * @param password
     * @return
     */
    public static boolean userCredentials(String username, String password){

        Locale currentLocale = Locale.getDefault();
        ResourceBundle resource = ResourceBundle.getBundle("Main.lang", currentLocale);
        System.out.println("Validating Credentials");

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT User_Name FROM users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("User_Name").equals(username)) {
                    System.out.println("Checking password for user: " + username);
                    try {
                        PreparedStatement pass = JDBC.getConnection().prepareStatement("SELECT Password FROM users WHERE User_Name = ?");
                        pass.setString(1, username);
                        ResultSet passFound = pass.executeQuery();
                        passFound.next();
                        if (passFound.getString("Password").equals(password)){
                            System.out.println("Password verified");
                            currentUser = username;
                            return true;
                        }

                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Password");
                        alert.setContentText("Incorrect Password");
                        alert.showAndWait();
                    }
                }


            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setTitle(resource.getString(resource.getString("invalidlogin")));
            alert.setContentText("User not found");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(resource.getString("invalidlogin"));
        alert.setHeaderText(resource.getString("Error"));
        alert.setContentText(resource.getString("Erroruser"));
        alert.showAndWait();
        return false;
    }

    /** Current User getter
     * @return current user
     */
    public static String getCurrentUser(){
        return currentUser;
    }
}
