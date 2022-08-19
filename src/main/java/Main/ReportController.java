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

/** Interface for combobox lambda
 *
 */
interface box {
    void setVisible(ComboBox box);
}

/** Interface for tableview lambda
 *
 */
interface table {
    void setVisible(TableView table);
}

/** Handles all functions in the Reports screen
 * contains lambdas for visibility settings on ComboBox and TableView objects
 */
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

    /** Lambda to set ComboBox visibility
     * makes one box visible and the other two invisible
     * helps code in main code much easier to call and read
     */
    box vis = (x) -> {
        selectContact.setVisible(false);
        selectCountry.setVisible(false);
        selectMonth.setVisible(false);
        x.setVisible(true);
    };

    /** Lambda to set TableView visibility
     * makes one table visible and the other two invisible
     * helps code in main code much easier to call and read
     */
    table invis = (x) -> {
        contactReportTable.setVisible(false);
        countryReportTable.setVisible(false);
        monthReportTable.setVisible(false);
        x.setVisible(true);
    };


    /** Initializer for the Reports screen
     * sets list for all combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMonth.setItems(DBAppointment.getMonths());
        selectContact.setItems(DBAppointment.getContacts());
        selectCountry.setItems(DBCustomer.getCountries());

    }

    /** Uses lambdas to set Month box and table visible
     * @param actionEvent month report button
     */
    public void monthReport(ActionEvent actionEvent) {
        vis.setVisible(selectMonth);
        invis.setVisible(monthReportTable);

    }

    /** Uses lambdas to set contact box and table visible
     * @param actionEvent contact report button
     */
    public void contactReport(ActionEvent actionEvent) {
        vis.setVisible(selectContact);
        invis.setVisible(contactReportTable);

    }

    /** Uses lambdas to set Country box and table visible
     * @param actionEvent country report button
     */
    public void countryReport(ActionEvent actionEvent) {
        vis.setVisible(selectCountry);
        invis.setVisible(countryReportTable);
    }

    /** Loads month report table based on selected month from combo box
     * @param actionEvent month combo box selection
     */
    public void loadMonthReport(ActionEvent actionEvent) {
        String month = selectMonth.getSelectionModel().getSelectedItem();
        DBAppointment.loadMonthReport(month);
        monthReportTable.setItems(DBAppointment.getMonthReport());
        monthType.setCellValueFactory(new PropertyValueFactory<>("name"));
        monthTotal.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    /** Loads contact report table from selected contact from combo box
     * @param actionEvent contact combo box selection
     */
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

    /** Loads country report table from selected country in country combo box
     * @param actionEvent country combo box selection
     */
    public void loadCountryReport(ActionEvent actionEvent) {
        int countryID = selectCountry.getSelectionModel().getSelectedItem().getId();
        DBAppointment.loadCountryReport(countryID);
        countryReportTable.setItems(DBAppointment.getCountryReport());
        country.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryTotal.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    /** Returns to main menu screen
     * @param actionEvent back to main menu button
     * @throws IOException
     */
    public void returnToMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
