package Main;

import Model.Customer;
import helper.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public TableView<Customer> customerTable;
    public TableView appointmentTable;
    public TableColumn <Customer, Integer> customerID;
    public TableColumn <Customer, String> customerName;
    public TableColumn <Customer, String> customerAddress;
    public TableColumn <Customer, String> postalCode;
    public TableColumn <Customer, String> phoneNumber;
    public TableColumn <Customer, String> levelDivision;
    public TableColumn <Customer, String> customerCountry;

    private static Customer modifyCustomer;

    public void addCustomer(ActionEvent actionEvent) {
    }

    public void delCustomer(ActionEvent actionEvent) {
        modifyCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(modifyCustomer == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("No Customer Selected");
            noSelection.setContentText("No Customer is Selected");
            noSelection.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete selected Customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                DBCustomer.deleteCustomer(modifyCustomer);
            }
        }
    }

    public void modCustomer(ActionEvent actionEvent) {
    }

    public void addAppointment(ActionEvent actionEvent) {
    }

    public void delAppointment(ActionEvent actionEvent) {
    }

    public void modAppointment(ActionEvent actionEvent) {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBCustomer.loadAllCustomers();

        customerTable.setItems(DBCustomer.getCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        levelDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

    }


}
