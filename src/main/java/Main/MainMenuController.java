package Main;

import Model.Appointment;
import Model.Customer;
import helper.DBAppointment;
import helper.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public TableView<Customer> customerTable;
    public TableColumn <Customer, Integer> customerID;
    public TableColumn <Customer, String> customerName;
    public TableColumn <Customer, String> customerAddress;
    public TableColumn <Customer, String> postalCode;
    public TableColumn <Customer, String> phoneNumber;
    public TableColumn <Customer, String> levelDivision;
    public TableColumn <Customer, String> customerCountry;

    private static Customer modifyCustomer;

    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> appointmentID;
    public TableColumn<Appointment, String> title;
    public TableColumn<Appointment, String> description;
    public TableColumn<Appointment, String> location;
    public TableColumn<Appointment, Integer> contact;
    public TableColumn<Appointment, String> type;
    public TableColumn<Appointment, Timestamp> startTime;
    public TableColumn<Appointment, Timestamp> endTime;
    public TableColumn<Appointment, Integer> appCustomerID;
    public TableColumn<Appointment, Integer> userID;

    private static Appointment modifyAppointment;

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        CustomerController.loadModCustomer(null);

        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Customer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void delCustomer(ActionEvent actionEvent) {
        modifyCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(modifyCustomer == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Delete Customer");
            noSelection.setContentText("No Customer is Selected");
            noSelection.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete selected Customer?");
            alert.setTitle("Delete Customer");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                DBCustomer.deleteCustomer(modifyCustomer);
            }
        }
    }

    public void modCustomer(ActionEvent actionEvent) throws IOException {
        modifyCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(modifyCustomer == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Delete Customer");
            noSelection.setContentText("No Customer is Selected");
            noSelection.showAndWait();
        }else {
            CustomerController.loadModCustomer(modifyCustomer);

            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Customer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 320, 400);
            stage.setTitle("Customer");
            stage.setScene(scene);
            stage.show();
        }

    }

    public void addAppointment(ActionEvent actionEvent) {
    }

    public void delAppointment(ActionEvent actionEvent) {
        modifyAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if(modifyAppointment == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Delete Appointment");
            noSelection.setContentText("No Appointment is Selected");
            noSelection.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete selected Appointment?");
            alert.setTitle("Delete Appointment");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                DBAppointment.deleteAppointment(modifyAppointment);
            }
        }
    }

    public void modAppointment(ActionEvent actionEvent) {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBCustomer.loadAllCustomers();
        DBAppointment.loadAllAppointments();

        customerTable.getItems().clear();
        customerTable.setItems(DBCustomer.getCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        levelDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

        appointmentTable.getItems().clear();
        appointmentTable.setItems(DBAppointment.getAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));


    }


}
