package Main;

import Model.Appointment;
import helper.DBAppointment;
import helper.DBCustomer;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    public ComboBox selectMonth;
    public TableView<Appointment> monthReportTable;
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
    public ComboBox selectContact;
    public TableView<Appointment> countryReportTable;
    public TableColumn country;
    public TableColumn countryTotal;
    public ComboBox selectCountry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectContact.setItems(DBAppointment.getContacts());
        selectCountry.setItems(DBCustomer.getCountries());



    }
    public void refreshTable(ObservableList<Appointment> appointments , TableView<Appointment> appTable){

    }

    public void monthReport(ActionEvent actionEvent) {
        setBoxVis(selectMonth);
        setTableVis(monthReportTable);

    }

    public void contactReport(ActionEvent actionEvent) {
        setBoxVis(selectContact);
        setTableVis(contactReportTable);

    }

    public void countryReport(ActionEvent actionEvent) {
        setBoxVis(selectCountry);
        setTableVis(countryReportTable);
    }

    public interface box {
        void setVisible(ComboBox box);
    }

    public void setBoxVis(ComboBox box){
        box invis = (x) -> {
            selectContact.setVisible(false);
            selectCountry.setVisible(false);
            selectMonth.setVisible(false);
            x.setVisible(true);
        };
        invis.setVisible(box);
    }

    public interface table {
        void setVisible(TableView table);
    }

    public void setTableVis(TableView table){
        table invis = (x) -> {
            contactReportTable.setVisible(false);
            countryReportTable.setVisible(false);
            monthReportTable.setVisible(false);
            x.setVisible(true);
        };
        invis.setVisible(table);
    }
}
