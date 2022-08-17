package Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Contact getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public Timestamp getStartTime() {
        return Timestamp.valueOf(startTime);
    }

    public Timestamp getEndTime() {
        return Timestamp.valueOf(endTime);
    }

    public LocalDateTime getStart() {
        return startTime;
    }

    public LocalDateTime getEnd() {
        return endTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
