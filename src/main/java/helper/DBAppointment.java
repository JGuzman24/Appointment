package helper;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBAppointment {

    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static void loadAllAppointments(){
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contact = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp startTime = rs.getTimestamp("Start");
                Timestamp endTime = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointment appointment = new Appointment(appointmentID, title, description, location, contact, type, startTime, endTime, customerID, userID);
                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    public static void deleteAppointment(Appointment appointment){
        appointments.remove(appointment);
    }
}
