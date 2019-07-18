package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Polovenko on 7/17/16.
 */
public class EditDialogController {

    private Stage stage;
    private Guest guest;
    private Boolean sucess=false;


    @FXML
    private TextField roomNum;
    @FXML
    private CheckBox booked;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNum;
    @FXML
    private DatePicker checkIn;
    @FXML
    private DatePicker checkOut;
    @FXML
    private TextField balance;


    public void setStage(Stage setStage) {
        this.stage = setStage;
    }


    public void setFile(Guest guest) {

        roomNum.setText(guest.getRoomNum());
        booked.setSelected(guest.isBooked());
        firstName.setText(guest.getFirstName());
        lastName.setText(guest.getLastName());
        phoneNum.setText(guest.getPhoneNum());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        checkIn.setValue(guest.getCheckIn());
        checkOut.setValue(guest.getCheckOut());
        balance.setText(guest.getBill());


    }

    @FXML
    private void cancel(){
        stage.close();
    }


    @FXML
    private Guest makeChanges() {
        String errorMessage = "";

        if (roomNum.getText() == null || roomNum.getText().trim().length() == 0) {
            errorMessage += "Please check the room number!\n";
        }
        if (firstName.getText() == null || firstName.getText().trim().length() == 0) {
            errorMessage += "Please check the first name!\n";
        }
        if (lastName.getText() == null || lastName.getText().trim().length() == 0) {
            errorMessage += "Please check the last name!\n";
        }
        if (phoneNum.getText() == null || phoneNum.getText().trim().length() == 0) {
            errorMessage += "Please check the phone number!\n";
        }
        if (checkIn.getValue().isAfter(checkOut.getValue())) {
            errorMessage += "Check in can not be after check out!\n";
        }


        if (errorMessage.length() == 0) {
            processFile();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Field");
            alert.setContentText(errorMessage);
            alert.showAndWait();

        }


        return null;

    }



    public void processFile() {
        String room = roomNum.getText().trim();
        String fName = firstName.getText().trim();
        String lName = lastName.getText().trim();
        String fphoneNum = phoneNum.getText().trim();
        boolean isBooked = booked.isSelected();
        LocalDate checkedIn = checkIn.getValue();
        LocalDate checkedOut = checkOut.getValue();
        String owns = balance.getText().trim();

        this.guest = new Guest(room, isBooked, fName, lName, fphoneNum, checkedIn, checkedOut, owns);

        sucess = true;
        stage.close();

    }

    public boolean completed() {
        return sucess;
    }


    public Guest returnGuest() {
      return guest;
    }


}


