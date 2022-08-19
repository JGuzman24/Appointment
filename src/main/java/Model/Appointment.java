package Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** creates the Object Appointment
 *
 */
public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private Contact contact;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerID;
    private int userID;


    /** Appointment constructor
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
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, Contact contact) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;

    }

    /** appointmentID getter
     * @return ID as int
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /** title getter
     * @return title as string
     */
    public String getTitle() {
        return title;
    }

    /** description getter
     * @return description as string
     */
    public String getDescription() {
        return description;
    }

    /** location getter
     * @return location as string
     */
    public String getLocation() {
        return location;
    }

    /** contact getter
     * @return contact as string
     */
    public Contact getContact() {
        return contact;
    }

    /** type getter
     * @return type as string
     */
    public String getType() {
        return type;
    }

    /** startTime getter
     * @return startTime as Timestamp
     */
    public Timestamp getStartTime() {
        return Timestamp.valueOf(startTime);
    }

    /** endTime getter
     * @return endTime as Timestamp
     */
    public Timestamp getEndTime() {
        return Timestamp.valueOf(endTime);
    }

    /** startTime getter
     * @return startTime as LocalDateTime
     */
    public LocalDateTime getStart() {
        return startTime;
    }

    /** endTime getter
     * @return endTime as LocalDateTime
     */
    public LocalDateTime getEnd() {
        return endTime;
    }

    /** customerID getter
     * @return customerID as int
     */
    public int getCustomerID() {
        return customerID;
    }

    /** userID getter
     * @return userID as int
     */
    public int getUserID() {
        return userID;
    }


    /** title setter
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** location setter
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** contact setter
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /** type setter
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

}
