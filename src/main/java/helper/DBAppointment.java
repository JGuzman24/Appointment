package helper;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBAppointment {

    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();


    private static int nextAppointmentID = 1;

    public static void loadAllAppointments(){
        try {
            appointments.removeAll(appointments);
            String sql = "SELECT * from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Contact contact = getContact(contactID);

                Appointment appointment = new Appointment(appointmentID, title, description, location, type, startTime, endTime, customerID, userID, contact);
                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadAllContacts(){
        try{
            contacts.removeAll(contacts);
            String sql = "SELECT * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");

                Contact contact = new Contact(contactID, name);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Contact getContact(int contactID){
        loadAllContacts();
        for(Contact c: contacts){
            if (c.getContactID() == contactID){
                return c;
            }
        }
        return null;
    }
    public static ObservableList<Contact> getContacts(){
        return contacts;
    }

    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String sql = "Delete from appointments where Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointment.getAppointmentID());

        ps.executeUpdate();
    }

    public static void addAppointment(Appointment appointment) throws SQLException {
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By,Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?,?,?);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointment.getAppointmentID());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setTimestamp(6, appointment.getStartTime());
            ps.setTimestamp(7, appointment.getEndTime());
            ps.setString(8, DBUser.getCurrentUser());
            ps.setString(9, DBUser.getCurrentUser());
            ps.setInt(10, appointment.getCustomerID());
            ps.setInt(11, appointment.getUserID());
            ps.setInt(12, appointment.getContact().getContactID());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Appointment getAppointment(int id){
        for(Appointment c : appointments){
            if (c.getAppointmentID() == id){
                return c;
            }
        }
        return null;
    }

    public static void modAppointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, Contact contact){
        try{
            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=NOW(), Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(startTime));
            ps.setTimestamp(6, Timestamp.valueOf(endTime));
            ps.setString(7, DBUser.getCurrentUser());
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setInt(10, contact.getContactID());
            ps.setInt(11, appointmentID);


            //appointments.add(customer);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*getCustomer(customerID).setCustomerName(customerName);
        getCustomer(customerID).setAddress(address);
        getCustomer(customerID).setPostalCode(postalCode);
        getCustomer(customerID).setPhoneNumber(phoneNumber);
        getCustomer(customerID).setDivision(division);
        getCustomer(customerID).setCountry(country);
        getCustomer(customerID).setDivisionID(divisionID);

        } finally {

        }*/
    }

    public static int getNextAppointmentID() {

        for(int i = 1; i <= getAppointments().size()+1; i++) {
            if (lookupAppointment(i) == null) {
                nextAppointmentID = i;
                return nextAppointmentID;
            }
        }
        return nextAppointmentID;
    }

    public static Appointment lookupAppointment(int appointmentID){
        for(Appointment p : appointments){
            if(p.getAppointmentID() == appointmentID){
                return p;
            }
        }
        return null;
    }
}
