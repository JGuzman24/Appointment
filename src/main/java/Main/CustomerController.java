package Main;

import Model.Country;
import Model.Customer;
import Model.Division;
import helper.DBCustomer;
import helper.DBDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.ResourceBundle;

/** Handles all functions for the Customer frame
 * Works both with new customers and modifying old ones
 */
public class CustomerController implements Initializable {
    public TextField customerID;
    public TextField name;
    public TextField address;
    public TextField postalCode;
    public TextField phoneNumber;

    private static Customer modCustomer;
    public ComboBox<Country> country;
    public ComboBox<Division> division;

    private static ObservableList<Country> countries;
    private static ObservableList<Division> divisions;

    /** Initializes the Customer frame
     * Fields are blank other than ID for new customers
     * Fields are filled completely for a customer to be modified
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearCustomerFields();

        customerID.setText(Integer.toString(DBCustomer.getNextCustomerID()));

        country.setItems(DBDivision.loadAllCountries());
        country.setPromptText("Country");
        division.setPromptText("State/Province");
        division.setItems(DBDivision.loadAllDivisions());

        if (modCustomer != null){
            customerID.setText(Integer.toString(modCustomer.getCustomerID()));
            name.setText(modCustomer.getCustomerName());
            address.setText(modCustomer.getAddress());
            postalCode.setText(modCustomer.getPostalCode());
            phoneNumber.setText(modCustomer.getPhoneNumber());
            division.getSelectionModel().select(DBDivision.getDivision(modCustomer.getDivisionID()));
            country.getSelectionModel().select(DBDivision.getCountry(modCustomer.getDivisionID()));

        }
    }

    /** Saves a customer
     * @param actionEvent
     * @throws IOException
     */
    public void saveCustomer(ActionEvent actionEvent) throws IOException{
        try {

            int saveID = Integer.parseInt(customerID.getText());
            String saveName = name.getText();
            String saveAddress = address.getText();
            String savePostalCode = postalCode.getText();
            String savePhoneNumber = phoneNumber.getText();
            int saveDivisionID = division.getSelectionModel().getSelectedItem().getId();

            if (saveName == "" || saveAddress == "" || savePostalCode == "" || savePhoneNumber == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Parameters");
                alert.setContentText("Please make sure all fields are filled");
                alert.showAndWait();
            } else {

                if (modCustomer == null) {
                    Customer newCustomer = new Customer(saveID, saveName, saveAddress, savePostalCode, savePhoneNumber, saveDivisionID);
                    DBCustomer.addCustomer(newCustomer);
                } else {
                    DBCustomer.modCustomer(saveID, saveName, saveAddress, savePostalCode, savePhoneNumber, saveDivisionID);
                }


                FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }


        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in text field.");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Field");
            alert.setContentText("One or more fields is empty.");
            alert.showAndWait();
        }

    }

    /** Returns to main menu
     * @param actionEvent
     * @throws IOException
     */
    public void returnToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


    /** MModify Customer getter
     * @param customer
     */
    public static void loadModCustomer(Customer customer){
        modCustomer = customer;
    }

    public void countrySelected(ActionEvent actionEvent) {
        try{
            int countryID = country.getSelectionModel().getSelectedItem().getId();
            division.setItems(DBDivision.narrowDivisions(countryID));
        } catch (NullPointerException e) {

        }

    }

    /** Clears all possible fields for the customer
     * ID is not changeable
     */
    public void clearCustomerFields(){
        name.setText(null);
        address.setText(null);
        postalCode.setText(null);
        phoneNumber.setText(null);
        country.setItems(DBDivision.loadAllCountries());
        country.setPromptText("Country");
        division.setPromptText("State/Province");
        division.setItems(DBDivision.loadAllDivisions());
    }

    /** Makes the modifiable null
     *
     */
    public static void clearModCustomer(){
        modCustomer = null;
    }


}
