package Main;

import Model.Appointment;
import Model.Customer;
import helper.DBAppointment;
import helper.DBCustomer;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static Main.CustomerController.clearModCustomer;
import static Main.AppointmentController.clearModAppointment;
import static Main.LoginController.time;

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
    public RadioButton allRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton monthRadioButton;

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        clearModCustomer();

        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Customer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void delCustomer(ActionEvent actionEvent) throws SQLException {
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
                refreshCustomerTable();
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

    public void addAppointment(ActionEvent actionEvent) throws IOException {
        clearModAppointment();

        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Appointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 324, 533);
        stage.setTitle("Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void delAppointment(ActionEvent actionEvent) throws SQLException {
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

    public void modAppointment(ActionEvent actionEvent) throws IOException {
        modifyAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if(modifyAppointment == null){
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Delete Appointment");
            noSelection.setContentText("No Appointment is Selected");
            noSelection.showAndWait();
        }else {
            AppointmentController.loadModAppointment(modifyAppointment);

            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Appointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 324, 533);
            stage.setTitle("Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allRadioButton.setSelected(true);
        refreshCustomerTable();
        checkAppointments();


    }

    public void refreshCustomerTable(){
        DBCustomer.loadAllCustomers();
        DBAppointment.loadAllAppointments();


        customerTable.setItems(DBCustomer.getCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        levelDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));

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

    public void checkAppointments(){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime now = currentTime.toLocalDateTime();
        ObservableList<Appointment> appointments = DBAppointment.getAppointments();
        boolean soon = false;
        boolean ongoing = false;
        Appointment close= null;

        for(Appointment A: appointments) {
            if (now.minusSeconds(1).isBefore(A.getStart()) && now.plusSeconds(1).plusMinutes(15).isAfter(A.getStart())) {
                soon = true;
                close = A;
            }else if (now.plusMinutes(15).plusSeconds(1).isAfter(A.getStart()) && now.plusMinutes(15).plusSeconds(1).isBefore(A.getEnd())) {
                ongoing = true;
                close = A;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(soon){
            alert.setTitle("Appointment Soon");
            alert.setContentText("There is an Appointment soon.\n" +
                    "Appointment ID: " + close.getAppointmentID() +"\n" +
                    "At: " + close.getStartTime());
        } else if (ongoing){
            alert.setTitle("Appointment In Progress");
            alert.setContentText("There is an Appointment Currently in progress.\n" +
                    "Appointment ID: " + close.getAppointmentID() +"\n" +
                    "Started At: " + close.getStartTime());
        } else {
                alert.setTitle("No Appointment");
                alert.setContentText("There are no appointments within 15 minutes. ");
            }
        alert.showAndWait();

    }


    public void selectAll(ActionEvent actionEvent) {
        DBAppointment.loadAllAppointments();
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

    public void selectWeek(ActionEvent actionEvent) {
        DBAppointment.loadWeekAppointments();
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

    public void selectMonth(ActionEvent actionEvent) {
        DBAppointment.loadMonthAppointments();
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

    public void reports(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 815, 610);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
