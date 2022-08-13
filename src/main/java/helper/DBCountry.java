package helper;


import Model.Countries;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFunctions {

    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                countries.add(C);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }

    public static ObservableList<Users> getAllUsers(){
        ObservableList<Users> users = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Users U = new Users(userId, userName, userPassword);
                users.add(U);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
