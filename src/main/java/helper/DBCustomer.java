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

    public static void loadAllCustomers(){
        try {
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

                Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber, country, division, divisionID);
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
}
