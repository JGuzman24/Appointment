package Main;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    public ComboBox selectMonth;
    public TableView monthReportTable;
    public TableColumn monthType;
    public TableColumn monthTotal;
    public TableView contactReportTable;
    public TableColumn contactAppID;
    public TableColumn contactTitle;
    public TableColumn contactType;
    public TableColumn contactDescription;
    public TableColumn contactStart;
    public TableColumn contactEnd;
    public TableColumn contactCustomerID;
    public ComboBox selectContact;
    public TableView countryReportTable;
    public TableColumn monthType1;
    public TableColumn monthTotal1;
    public ComboBox selectCountry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }

    public void monthReport(ActionEvent actionEvent) {
        setVis(selectMonth);
    }

    public void contactReport(ActionEvent actionEvent) {
        setVis(selectContact);

    }

    public void countryReport(ActionEvent actionEvent) {
        setVis(selectCountry);
    }

    public interface box {
        void setVisible(ComboBox box);
    }

    public void setVis(ComboBox box){
        box invis = (x) -> {
            selectContact.setVisible(false);
            selectCountry.setVisible(false);
            selectMonth.setVisible(false);
            x.setVisible(true);
        };
        invis.setVisible(box);
    }
}
