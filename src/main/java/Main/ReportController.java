package Main;

import Model.Appointment;
import Model.Contact;
import Model.Country;
import helper.DBAppointment;
import helper.DBCustomer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

interface box {
    void setVisible(ComboBox box);
}

interface table {
    void setVisible(TableView table);
}

public class ReportController implements Initializable {
    public ComboBox<String> selectMonth;
    public TableView<Country> monthReportTable;
    public TableColumn monthType;
    public TableColumn monthTotal;
    public TableView<Appointment> contactReportTable;
    public TableColumn contactAppID;
    public TableColumn contactTitle;
    public TableColumn contactType;
    public TableColumn contactDescription;
    public TableColumn contactStart;
    public TableColumn contactEnd;
    public TableColumn contactCustomerID;
    public ComboBox<Contact> selectContact;
    public TableView<Country> countryReportTable;
    public TableColumn country;
    public TableColumn countryTotal;
    public ComboBox<Country> selectCountry;

    box vis = (x) -> {
        selectContact.setVisible(false);
        selectCountry.setVisible(false);
        selectMonth.setVisible(false);
        x.setVisible(true);
    };

    table invis = (x) -> {
        contactReportTable.setVisible(false);
        countryReportTable.setVisible(false);
        monthReportTable.setVisible(false);
        x.setVisible(true);
    };



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMonth.setItems(DBAppointment.getMonths());
        selectContact.setItems(DBAppointment.getContacts());
        selectCountry.setItems(DBCustomer.getCountries());

    }

    public void monthReport(ActionEvent actionEvent) {
        vis.setVisible(selectMonth);
        invis.setVisible(monthReportTable);

    }

    public void contactReport(ActionEvent actionEvent) {
        vis.setVisible(selectContact);
        invis.setVisible(contactReportTable);

    }

    public void countryReport(ActionEvent actionEvent) {
        vis.setVisible(selectCountry);
        invis.setVisible(countryReportTable);
    }

    public void loadMonthReport(ActionEvent actionEvent) {
        String month = selectMonth.getSelectionModel().getSelectedItem();
        DBAppointment.loadMonthReport(month);
        monthReportTable.setItems(DBAppointment.getMonthReport());
        monthType.setCellValueFactory(new PropertyValueFactory<>("name"));
        monthTotal.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    public void loadContactReport(ActionEvent actionEvent) {
        Contact contact = selectContact.getSelectionModel().getSelectedItem();
        DBAppointment.loadContactReport(contact);
        contactReportTable.setItems(DBAppointment.getContactReport());
        contactAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactType.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        contactEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));

    }

    public void loadCountryReport(ActionEvent actionEvent) {
        int countryID = selectCountry.getSelectionModel().getSelectedItem().getId();
        DBAppointment.loadCountryReport(countryID);
        countryReportTable.setItems(DBAppointment.getCountryReport());
        country.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryTotal.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    public void returnToMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
