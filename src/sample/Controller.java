package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;


public class Controller {


    @FXML
    private ListView<Guest> rooms;


    @FXML
    private TableView<Guest> rightTable;
    @FXML
    private TableColumn<Guest, String> roomColumn;
    @FXML
    private TableColumn<Guest, String> bookedColumn;
    @FXML
    private TableColumn<Guest, String> checkInColumn;
    @FXML
    private TableColumn<Guest, String> checkOutColumn;
    @FXML
    private TableColumn<Guest, String> balanceColumn;
    @FXML
    private GridPane gridPane;




    @FXML
    private Label roomNumber;
    @FXML
    private Label booked;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label phoneNum;
    @FXML
    private Label checkIn;
    @FXML
    private Label checkOut;
    @FXML
    private Label bill;



    @FXML
    private BorderPane mainBorderPane;





    public void initialize() {


            roomNumber.setText("");
            booked.setText("");
            firstName.setText("");
            lastName.setText("");
            phoneNum.setText("");
            checkIn.setText("");
            checkOut.setText("");
            bill.setText("");
            gridPane.setDisable(true);


        rooms.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Guest>() {
            @Override
            public void changed(ObservableValue<? extends Guest> observable, Guest oldValue, Guest newValue) {
                if (newValue != null) {
                    gridPane.setDisable(false);
                    Guest record = rooms.getSelectionModel().getSelectedItem();
                   roomNumber.setText(record.getRoomNum());
                    if(record.isBooked()){
                        booked.setText("Yes");
                    }else{
                        booked.setText("No");
                    }
                    firstName.setText(record.getFirstName());
                    lastName.setText(record.getLastName());
                    phoneNum.setText(record.getPhoneNum());
                   DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    checkIn.setText(df.format(record.getCheckIn()));
                    checkOut.setText(df.format(record.getCheckOut()));
                    bill.setText("$"+record.getBill());


                } else{
                    roomNumber.setText("");
                    booked.setText("");
                    firstName.setText("");
                    lastName.setText("");
                    phoneNum.setText("");
                    checkIn.setText("");
                    checkOut.setText("");
                    bill.setText("");
                    gridPane.setDisable(true);
                }
            }
        });


        SortedList<Guest> sortedList = new SortedList<Guest>(LoadData.getInstance().getFiles(),
                new Comparator<Guest>() {
                    @Override
                    public int compare(Guest o1, Guest o2) {
                        return o1.getRoomNum().compareTo(o2.getRoomNum());
                    }
                });


        rooms.setItems(sortedList);
        rooms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rooms.getSelectionModel().selectFirst();

        rooms.setCellFactory(new Callback<ListView<Guest>, ListCell<Guest>>() {
            @Override
            public ListCell<Guest> call(ListView<Guest> param) {
                ListCell<Guest> cell = new ListCell<Guest>() {
                    @Override
                    protected void updateItem(Guest item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText("Room "+ item.getRoomNum());
                        }
                    }

                };

                return cell;

            }
        });



    }

    @FXML
    private void deleteSelectedFile() {
        Guest onFile = rooms.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete guests file");
        alert.setHeaderText("Delete room " + onFile.getRoomNum()+"'s file");
        alert.setContentText("Press ok to delete this guests information");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            LoadData.getInstance().deleteGuest(onFile);
        }

    }


    public void showEditDialog(ActionEvent event) throws Exception {
        Guest onFile = rooms.getSelectionModel().getSelectedItem();

        if (onFile != null) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("editDialog.fxml"));

                GridPane dialog = (GridPane) loader.load();


                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit guests file");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(mainBorderPane.getScene().getWindow());
                Scene scene = new Scene(dialog);
                dialogStage.setScene(scene);


                EditDialogController controller = loader.getController();
                controller.setStage(dialogStage);

                controller.setFile(onFile);
                dialogStage.showAndWait();

                if(controller != null && controller.completed() ==  true) {

                    LoadData.getInstance().addGuest(controller.returnGuest());
                    LoadData.getInstance().deleteGuest(onFile);
                    rooms.getSelectionModel().select(controller.returnGuest());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No file to edit");
            alert.setHeaderText("There is no file to edit");
            alert.setContentText("Please make sure that you select a valid file");
            alert.showAndWait();
        }



    }



    @FXML
    public void showNewGuestDialog(ActionEvent event) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add a new guest");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newFile.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.APPLY) {
            DialogController controller = fxmlLoader.getController();
            Guest newItem = controller.processFile();
            rooms.getSelectionModel().select(newItem);


        }

    }

}

