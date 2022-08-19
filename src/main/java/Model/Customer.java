package Model;

import helper.DBDivision;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;


/** class for Customer Object
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String country;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String division;
    private int divisionID;


    /** Customer constructor
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionID
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.division = DBDivision.getDivision(divisionID).getName();
        this.postalCode = postalCode;
        this.country = DBDivision.getDivision(divisionID).getCountry();
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }


    /** customerID getter
     * @return customerID as int
     */
    public int getCustomerID() {
        return customerID;
    }

    /** customerName getter
     * @return customerName as String
     */
    public String getCustomerName() {
        return customerName;
    }

    /** address getter
     * @return address as String
     */
    public String getAddress() {
        return address;
    }

    /** postalCode getter
     * @return postalCode as String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** country getter
     * @return country as String
     */
    public String getCountry() {
        return country;
    }


    /** country setter
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** phoneNumber getter
     * @return phoneNumber as String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** division getter
     * @return division as String
     */
    public String getDivision() {
        return division;
    }

    /** divisionID getter
     * @return divisionID as int
     */
    public int getDivisionID() {
        return divisionID;
    }

}