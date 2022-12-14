package helper;

import Model.Contact;
import Model.Country;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class that handles Customer functions with the database
 *
 */
public class DBCustomer {

    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<Country> countries = FXCollections.observableArrayList();

    private static int nextCustomerID = 1;

    /** Loads all customers from the database
     *
     */
    public static void loadAllCustomers(){
        try {
            customers.removeAll(customers);

            String sql = "SELECT * from customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");;

                Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber,  divisionID);
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Loads all countries from the database
     * There is no Country helper, so this was chosen
     *
     */
    public static void loadAllCountries(){
        try{
            countries.removeAll(countries);
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country country = new Country(countryId, name);
                countries.add(country);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /** Countries getter
     * @return country list
     */
    public static ObservableList<Country> getCountries(){
        loadAllCountries();
        return countries;
    }

    /** Customers getter
     * @return customer list
     */
    public static ObservableList<Customer> getCustomers(){
        return customers;
    }

    /** Deletes a customer from the database
     * @param customer
     * @throws SQLException
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        String sql = "Delete from customers where Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customer.getCustomerID());

        ps.executeUpdate();

    }

    /** Adds new customer to the database
     * @param customer
     * @throws SQLException
     */
    public static void addCustomer(Customer customer) throws SQLException {
        try{
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By,Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customer.getCustomerID());
            ps.setString(2, customer.getCustomerName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhoneNumber());
            ps.setString(6, DBUser.getCurrentUser());
            ps.setString(7, DBUser.getCurrentUser());
            ps.setInt(8, customer.getDivisionID());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Modifies a customer in the database
     * all parameters are overwritten
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionID
     */
    public static void modCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber,  int divisionID){
        try{
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update = NOW(), Last_Updated_By=?, Division_ID=? WHERE Customer_ID =?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setString(5, DBUser.getCurrentUser());
            ps.setInt(6, divisionID);
            ps.setInt(7, customerID);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Returns next available ID for customer
     * Starts at 1, adds 1 subsequently
     * can find a previously deleted customer
     * @return
     */
    public static int getNextCustomerID() {

        for(int i = 1; i <= getCustomers().size()+1; i++) {
            if (lookupCustomer(i) == null) {
                nextCustomerID = i;
                return nextCustomerID;
            }
        }
        return nextCustomerID;
    }

    /** Looks up a customer from a given ID
     * @param customerID
     * @return customer
     */
    public static Customer lookupCustomer(int customerID){
        for(Customer p : customers){
            if(p.getCustomerID() == customerID){
                return p;
            }
        }
        return null;
    }
}
