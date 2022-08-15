package helper;

import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomer {

    private static ObservableList<Customer> customers = FXCollections.observableArrayList();

    private static int nextCustomerID = 1;

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
                String country = "United States";
                String division = "Ohio";
                int divisionID = rs.getInt("Division_ID");;

                Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber,  divisionID);
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Customer> getCustomers(){
        return customers;
    }

    public static void deleteCustomer(Customer customer){
        customers.remove(customer);
    }

    public static void addCustomer(Customer customer) throws SQLException {
        try{
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, DBUser.getCurrentUser());
            ps.setString(6, DBUser.getCurrentUser());
            ps.setInt(7, customer.getDivisionID());
            //customers.add(customer);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Customer getCustomer(int id){
        for(Customer c : customers){
            if (c.getCustomerID() == id){
                return c;
            }
        }
        return null;
    }

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


            //customers.add(customer);
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
        getCustomer(customerID).setDivisionID(divisionID);*/

    }

    public static int getNextCustomerID() {

        for(int i = 1; i <= getCustomers().size()+1; i++) {
            if (lookupCustomer(i) == null) {
                nextCustomerID = i;
                return nextCustomerID;
            }
        }
        return nextCustomerID;
    }

    public static Customer lookupCustomer(int customerID){
        for(Customer p : customers){
            if(p.getCustomerID() == customerID){
                return p;
            }
        }
        return null;
    }
}
