package sample;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

/**
 * Created by Polovenko on 7/9/16.
 */
public class DialogController {


    @FXML
    private TextField roomNum;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private CheckBox booked;


    @FXML
    private DatePicker checkIn;


    @FXML
    private DatePicker checkOut;


    @FXML
    private TextField balance;



    @FXML
    private TextField fphoneNum;


    public Guest processFile() {
        String room = roomNum.getText().trim();
        String fName = firstName.getText().trim();
        String lName = lastName.getText().trim();
        String phoneNum = fphoneNum.getText().trim();
        boolean isBooked = booked.isSelected();
        LocalDate checkedIn = checkIn.getValue();
        LocalDate checkedOut = checkOut.getValue();
        String owns = balance.getText().trim();

        Guest newGuest = new Guest(room, isBooked, fName, lName, phoneNum,  checkedIn, checkedOut, owns);
        LoadData.getInstance().addGuest(newGuest);
        return newGuest;


    }



}
