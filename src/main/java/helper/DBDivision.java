package helper;


import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivision {

    private static ObservableList<Division> divisions = FXCollections.observableArrayList();


    public static ObservableList<Division> loadAllDivisions(){
        divisions.removeAll(divisions);

        try {
            String sql =
                    "select d.Division_ID, d.Division, d.Country_ID, c.Country \n" +
                            "from first_level_divisions as d\n" +
                            "inner join countries c \n" +
                            "on d.Country_ID = c.Country_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Division D = new Division(divisionId, divisionName, countryID, country);
                divisions.add(D);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    public static Division getDivision(int divisionID){
        for(Division D: divisions){
            if(D.getId()==divisionID){
                return D;
            }
        }
        return null;
    }

    public static Country getCountry(int divisionID){
        for(Country C: countries){
            if(C.getId()== getDivision(divisionID).getCountryID()){
                return C;
            }
        }
        return null;
    }

    public static ObservableList<Country> countries = FXCollections.observableArrayList();

    public static ObservableList<Country> loadAllCountries(){
        countries.removeAll(countries);

        try {
            String sql = "select * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Country C = new Country(id, country);
                countries.add(C);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public static ObservableList<Division> narrowDivisions(int countryID){
        ObservableList<Division> fullDivisions = loadAllDivisions();
        ObservableList<Division> narrowedDivisions = FXCollections.observableArrayList();
        for(Division D : fullDivisions){
            if(D.getCountryID() == countryID){
                narrowedDivisions.add(D);
            }
        }
        return narrowedDivisions;
    }

    public static ObservableList<Division> getComboDivision(int divisionID){
        ObservableList<Division> fullDivisions = loadAllDivisions();
        ObservableList<Division> narrowedDivisions = FXCollections.observableArrayList();
        for(Division D : fullDivisions){
            if(D.getId() == divisionID){
                narrowedDivisions.add(D);
            }
        }
        return narrowedDivisions;
    }




}
