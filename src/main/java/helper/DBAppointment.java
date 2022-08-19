package helper;

import Model.Appointment;
import Model.Contact;
import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This class handles the functions needed for appointments into/from the database
 *
 */
public class DBAppointment {

    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private static ObservableList months = FXCollections.observableArrayList();
    private static ObservableList<Country> monthReport = FXCollections.observableArrayList();
    private static ObservableList<Appointment> contactReport = FXCollections.observableArrayList();
    private static ObservableList<Country> countryReport = FXCollections.observableArrayList();

    private static int nextAppointmentID = 1;


    /** Loads all appointments from the database
     *
     */
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


    /** Loads appointments from the database that match the current week
     *
     */
    public static void loadWeekAppointments(){
        try {
            appointments.removeAll(appointments);
            String sql = "select * from appointments where week(Start)=week(now());";
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

    /** Loads appointments from the database that match the current month
     *
     */
    public static void loadMonthAppointments(){
        try {
            appointments.removeAll(appointments);
            String sql = "select * from appointments where month(Start) = month(now());";
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

    /** Loads appointments that match a particular contact
     *
     * @param contact
     */
    public static void loadContactReport(Contact contact){
        try {
            if (contact != null) {
                contactReport.removeAll(contactReport);
                String sql = "select * from appointments where contact_id = ? order by Start";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setInt(1, contact.getContactID());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
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

                    Appointment appointment = new Appointment(appointmentID, title, description, location, type, startTime, endTime, customerID, userID, contact);
                    contactReport.add(appointment);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /** Loads custom month report
     * uses SQL to load appointments that match selected month
     * @param month
     */
    public static void loadMonthReport(String month){
        try {
            monthReport.removeAll(monthReport);
            String sql = "SELECT Type, COUNT(*)  as Total FROM appointments where MONTHNAME(start) = ? GROUP BY  type";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, month);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String type = rs.getString("Type");
                int total = rs.getInt("Total");

                Country report = new Country(total, type);

                monthReport.add(report);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Loads custom country report
     * uses SQL to load custom table that takes selected country
     * @param countryID
     */
    public static void loadCountryReport(int countryID){
        try {
            countryReport.removeAll(countryReport);
            String sql = "select a.Type, Count(*) as Total\n" +
                    "from appointments as a\n" +
                    "inner join customers as s\n" +
                    "on a.Customer_ID = s.Customer_ID\n" +
                    "inner join first_level_divisions as d\n" +
                    "on s.Division_ID = d.Division_ID\n" +
                    "inner join countries c\n" +
                    "on d.Country_ID = c.Country_ID\n" +
                    "where d.Country_ID = ?\n" +
                    "group by type";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String type = rs.getString("Type");
                int total = rs.getInt("Total");

                Country report = new Country(total, type);
                countryReport.add(report);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Loads all contacts from database
     * there is no contact helper, so this was added here
     */
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

    /** Returns contact object from matching ID
     * @param contactID
     * @return
     */
    public static Contact getContact(int contactID){
        loadAllContacts();
        for(Contact c: contacts){
            if (c.getContactID() == contactID){
                return c;
            }
        }
        return null;
    }

    /** Contacts getter
     * @return
     */
    public static ObservableList<Contact> getContacts(){
        return contacts;
    }

    /** Appointment getter
     * @return
     */
    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    /** Month report getter
     * @return
     */
    public static ObservableList<Country> getMonthReport(){
        return monthReport;
    }

    /** Country report getter
     * @return
     */
    public static ObservableList<Country> getCountryReport(){
        return countryReport;
    }

    /** Contact report getter
     * @return
     */
    public static ObservableList<Appointment> getContactReport(){
        return contactReport;
    }

    /** Month list getter
     * @return
     */
    public static ObservableList getMonths() {
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        return months;
    }

    /** Deletes appointment from database
     * @param appointment
     * @throws SQLException
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String sql = "Delete from appointments where Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointment.getAppointmentID());

        ps.executeUpdate();
    }

    /** Adds new appointment to database
     * @param appointment
     * @throws SQLException
     */
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

    /** Modifies appointment in the database.
     * Rewrites all parameters to catch any changes
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startTime
     * @param endTime
     * @param customerID
     * @param userID
     * @param contact
     */
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

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /** Looks through existing appointment IDs to find next available
     * Starts at 1, finds next valid int
     * @return
     */
    public static int getNextAppointmentID() {

        for(int i = 1; i <= getAppointments().size()+1; i++) {
            if (lookupAppointment(i) == null) {
                nextAppointmentID = i;
                return nextAppointmentID;
            }
        }
        return nextAppointmentID;
    }

    /** Returns appointment that matches given ID
     * returns null if ID not found
     * @param appointmentID
     * @return
     */
    public static Appointment lookupAppointment(int appointmentID){
        for(Appointment p : appointments){
            if(p.getAppointmentID() == appointmentID){
                return p;
            }
        }
        return null;
    }
}
