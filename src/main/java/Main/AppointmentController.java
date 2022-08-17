package Main;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import helper.DBAppointment;
import helper.DBCustomer;
import helper.DBDivision;
import javafx.collections.ObservableList;
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
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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

    public int conflictID;

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
        clearAppointmentFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBAppointment.loadAllContacts();
        clearAppointmentFields();

        LocalDate rand = LocalDate.of(2022, 01, 01);
        LocalTime estStart = LocalTime.of(8,0);
        ZoneId estID = ZoneId.of("America/New_York");
        ZonedDateTime begin = ZonedDateTime.of(rand, estStart, estID );
        LocalTime estEnd = LocalTime.of(22, 0);
        ZonedDateTime finish = ZonedDateTime.of(rand, estEnd, estID);
        ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

        Instant estBegin = begin.toInstant();
        Instant estFinish = finish.toInstant();
        ZonedDateTime startLocal = estBegin.atZone(localZone);
        ZonedDateTime endLocal = estFinish.atZone(localZone);





        contactCombo.setItems(DBAppointment.getContacts());

        LocalTime start = startLocal.toLocalTime();
        LocalTime endStart = startLocal.toLocalTime().plusMinutes(15);
        LocalTime end = endLocal.toLocalTime();


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
            startTime.getSelectionModel().select(modAppointment.getStart().toLocalTime());
            endTime.getSelectionModel().select(modAppointment.getEnd().toLocalTime());


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

            System.out.println("Format for start save: " + start);
            System.out.println("Format for end save: " + end);

            if(end.isBefore(start)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Time Error");
                alert.setContentText("Start Time must be before End Time.");
                alert.showAndWait();
            }else {

                if(noConflict(start, end, appointmentID)){
                    if (title == "" || description == "" || location == "" || type == "") {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Empty Parameters");
                        alert.setContentText("Please make sure all fields are filled");
                        alert.showAndWait();
                    } else {
                        if (modAppointment == null) {

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
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment Conflict");
                    alert.setContentText("Appointment conflicts with other Appointment: " + conflictID);
                    alert.showAndWait();
                }
            }


        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in text field.");
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Field");
            alert.setContentText("One or more fields is empty.");
            alert.showAndWait();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Customer or User");
            alert.setContentText("Customer or User Does not exist.");
            alert.showAndWait();;
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

    public Boolean noConflict(LocalDateTime start, LocalDateTime end, int appointmentID){
        ObservableList<Appointment> appointments = DBAppointment.getAppointments();
        for (Appointment A: appointments){
            if(start.isAfter(A.getStart()) && start.isBefore(A.getEnd()) ){
                conflictID = A.getAppointmentID();
                if(conflictID != appointmentID){
                    return false;
                }
            }
            if(end.isAfter(A.getStart()) && end.isBefore(A.getEnd())){
                conflictID = A.getAppointmentID();
                if(conflictID != appointmentID){
                    return false;
                }
            }
        }
        return true;
    }
}
