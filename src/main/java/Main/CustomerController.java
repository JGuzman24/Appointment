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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearCustomerFields();

        System.out.println("Next available ID: " + DBCustomer.getNextCustomerID());
        customerID.setText(Integer.toString(DBCustomer.getNextCustomerID()));
        System.out.println("customer ID set to: " + customerID.getText());


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

    public void saveCustomer(ActionEvent actionEvent) throws IOException{
        try {
            System.out.println("On Save: customer ID set to: " + customerID.getText());
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
                    System.out.println("New customer: customer ID set to: " + customerID.getText());
                    Customer newCustomer = new Customer(saveID, saveName, saveAddress, savePostalCode, savePhoneNumber, saveDivisionID);
                    DBCustomer.addCustomer(newCustomer);
                } else {
                    System.out.println("Modifying customer: customer ID set to: " + customerID.getText());
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

    }

    public void returnToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }



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

    public static void clearModCustomer(){
        modCustomer = null;
    }

    public void clear(ActionEvent actionEvent) {
        clearCustomerFields();
    }
}
