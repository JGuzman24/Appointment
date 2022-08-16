package Main;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import helper.DBAppointment;
import helper.DBCustomer;
import helper.DBDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    public TextField appointmentIDField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public TextField customerIDField;
    public TextField userIDField;
    public ComboBox <Contact> contactCombo;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public Button cancelButton;

    public static Appointment modAppointment;
    public ComboBox <LocalTime> startTime;
    public ComboBox <LocalTime> endTime;

    public static void loadModAppointment(Appointment modifyAppointment) {
        modAppointment = modifyAppointment;
    }

    public void contactComboSelect(ActionEvent actionEvent) {
    }

    public void startDatePickerSelect(ActionEvent actionEvent) {
    }

    public void endDatePickerSelect(ActionEvent actionEvent) {
    }

    public void clearButton(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBAppointment.loadAllContacts();
        clearAppointmentFields();

        contactCombo.setItems(DBAppointment.getContacts());

        LocalTime start = LocalTime.of(8,0);
        LocalTime endStart = LocalTime.of(8,15);
        LocalTime end = LocalTime.of(17,0);

        startTime.getItems().add(start);
        while (start.isBefore(end.minusMinutes(15).plusSeconds(1))){
            startTime.getItems().add(start);
            start = start.plusMinutes(15);
        }
        endTime.getItems().add(endStart);
        while (endStart.isBefore(end.plusSeconds(1))){
            endTime.getItems().add(endStart);
            endStart = endStart.plusMinutes(15);
        }



        appointmentIDField.setText(Integer.toString(DBAppointment.getNextAppointmentID()));

        if (modAppointment != null){
            appointmentIDField.setText(Integer.toString(modAppointment.getAppointmentID()));
            titleField.setText(modAppointment.getTitle());
            descriptionField.setText(modAppointment.getDescription());
            locationField.setText(modAppointment.getLocation());
            typeField.setText(modAppointment.getType());
            startDatePicker.setValue(LocalDate.from(modAppointment.getStartTime().toLocalDateTime()));
            endDatePicker.setValue(LocalDate.from(modAppointment.getEndTime().toLocalDateTime()));
            customerIDField.setText(Integer.toString(modAppointment.getCustomerID()));
            userIDField.setText(Integer.toString(modAppointment.getUserID()));
            contactCombo.getSelectionModel().select(modAppointment.getContact());


        }
    }

    public void saveAppointment(ActionEvent actionEvent) throws IOException {
        try {
            System.out.println("On Save: appointment ID set to: " + appointmentIDField.getText());

            int appointmentID = Integer.parseInt(appointmentIDField.getText());
            String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            int customerID = Integer.parseInt(customerIDField.getText());
            int userID = Integer.parseInt(userIDField.getText());
            Contact contact = contactCombo.getSelectionModel().getSelectedItem();
            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), startTime.getSelectionModel().getSelectedItem());
            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), endTime.getSelectionModel().getSelectedItem());

            if (modAppointment== null) {

                    Appointment newAppointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contact);
                    DBAppointment.addAppointment(newAppointment);
                } else {
                    DBAppointment.modAppointment(appointmentID, title, description, location, type, start, end, customerID, userID, contact);
                }


                FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();


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

    private void clearAppointmentFields() {
        titleField.setText(null);
        descriptionField.setText(null);
        locationField.setText(null);
        typeField.setText(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        customerIDField.setText(null);
        userIDField.setText(null);
        contactCombo.setItems(DBAppointment.getContacts());
    }
    public static void clearModAppointment(){
        modAppointment = null;
    }

    public void startTimeCombo(ActionEvent actionEvent) {
    }

    public void endTimeCombo(ActionEvent actionEvent) {
    }
}
