package helper;


import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class handles all functions for Divisions (States/Provinces) from the database
 *
 */
public class DBDivision {

    private static ObservableList<Division> divisions = FXCollections.observableArrayList();


    /** Loads all low level divisions
     * @return observable list of divisions
     */
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

    /** Division getter
     * @param divisionID
     * @return
     */
    public static Division getDivision(int divisionID){
        for(Division D: divisions){
            if(D.getId()==divisionID){
                return D;
            }
        }
        return null;
    }

    /** Country getter
     * @param divisionID
     * @return
     */
    public static Country getCountry(int divisionID){
        for(Country C: countries){
            if(C.getId()== getDivision(divisionID).getCountryID()){
                return C;
            }
        }
        return null;
    }

    public static ObservableList<Country> countries = FXCollections.observableArrayList();

    /** Loads all countries from the database
     * also returns as an observable list
     * @return country list
     */
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

    /** Narrows low level divisions to the country in which they reside
     * @param countryID
     * @return list of divisions
     */
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




}
