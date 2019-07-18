package sample;

import java.time.LocalDate;

/**
 * Created by Polovenko on 7/9/16.
 */
public class Guest {
    private String roomNum;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private boolean booked;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String bill;

    public Guest(String roomNum, boolean booked, String firstName, String lastName, String phoneNum,  LocalDate checkIn, LocalDate checkOut, String bill) {
        this.roomNum = roomNum;
        this.booked = booked;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bill = bill;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;

    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public boolean isBooked() {
        return booked;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getBill() {
        return bill;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
