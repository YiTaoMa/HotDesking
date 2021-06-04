package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminChangeLockDownSeatsModel;
import main.model.AdminLockdownSeatPromptModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

public class AdminChangeLockDownSeatsController implements Initializable {
    final int SEAT_NO1 = 1;
    final int SEAT_NO2 = 2;
    final int SEAT_NO3 = 3;
    final int SEAT_NO4 = 4;
    final int SEAT_NO5 = 5;
    final int SEAT_NO6 = 6;
    HashMap<Integer, Boolean> seatsStatus = new HashMap<>();
    HashMap<Integer, Boolean> seatsStatusFinal = new HashMap<>();
    ObservableList<String> isLockDown = FXCollections.observableArrayList("true", "false");
    AdminChangeLockDownSeatsModel adminChangeLockDownSeatsModel = new AdminChangeLockDownSeatsModel();
    AdminLockdownSeatPromptModel adminLockdownSeatPromptModel = new AdminLockdownSeatPromptModel();
    private String value1FromDataBase;
    private String value2FromDataBase;
    private String value3FromDataBase;
    private String value4FromDataBase;
    private String value5FromDataBase;
    private String value6FromDataBase;
    @FXML
    private BorderPane borderPaneChangeLockDownSeats;
    @FXML
    private Label errorMessageChangeLockedDownSeats;
    @FXML
    private ChoiceBox choiceBoxSeat1;
    @FXML
    private ChoiceBox choiceBoxSeat2;
    @FXML
    private ChoiceBox choiceBoxSeat3;
    @FXML
    private ChoiceBox choiceBoxSeat4;
    @FXML
    private ChoiceBox choiceBoxSeat5;
    @FXML
    private ChoiceBox choiceBoxSeat6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get all data from the database at the start to set as the default.
        errorMessageChangeLockedDownSeats.setText("");
        seatsStatus.clear();
        try {
            seatsStatus = adminChangeLockDownSeatsModel.getAllSeatLockedDownStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Entry<Integer, Boolean> entry : seatsStatus.entrySet()) {
            if (entry.getKey() == SEAT_NO1) {
                value1FromDataBase = String.valueOf(entry.getValue());
                choiceBoxSeat1.setValue(String.valueOf(entry.getValue()));
                choiceBoxSeat1.setItems(isLockDown);
            } else if (entry.getKey() == SEAT_NO2) {
                value2FromDataBase = String.valueOf(entry.getValue());
                choiceBoxSeat2.setValue(String.valueOf(entry.getValue()));
                choiceBoxSeat2.setItems(isLockDown);
            } else if (entry.getKey() == SEAT_NO3) {
                value3FromDataBase = String.valueOf(entry.getValue());
                choiceBoxSeat3.setValue(String.valueOf(entry.getValue()));
                choiceBoxSeat3.setItems(isLockDown);
            } else if (entry.getKey() == SEAT_NO4) {
                value4FromDataBase = String.valueOf(entry.getValue());
                choiceBoxSeat4.setValue(String.valueOf(entry.getValue()));
                choiceBoxSeat4.setItems(isLockDown);
            } else if (entry.getKey() == SEAT_NO5) {
                value5FromDataBase = String.valueOf(entry.getValue());
                choiceBoxSeat5.setValue(String.valueOf(entry.getValue()));
                choiceBoxSeat5.setItems(isLockDown);
            } else if (entry.getKey() == SEAT_NO6) {
                value6FromDataBase = String.valueOf(entry.getValue());
                choiceBoxSeat6.setValue(String.valueOf(entry.getValue()));
                choiceBoxSeat6.setItems(isLockDown);
            }
        }
    }

    public void goBackToAdminMain(ActionEvent event) {
        Scene scene = borderPaneChangeLockDownSeats.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/mainAdmin.fxml"));
            primaryStage.setTitle("Hotdesking-Main-Admin");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the mainAdmin.fxml");
        }
    }

    public void updateChangedLockedDownSeats(ActionEvent event) {
        seatsStatusFinal.clear();
        // if admin not change anything
        if (choiceBoxSeat1.getValue().toString().equals(value1FromDataBase) && choiceBoxSeat2.getValue().toString().equals(value2FromDataBase)
                && choiceBoxSeat3.getValue().toString().equals(value3FromDataBase) && choiceBoxSeat4.getValue().toString().equals(value4FromDataBase)
                && choiceBoxSeat5.getValue().toString().equals(value5FromDataBase) && choiceBoxSeat6.getValue().toString().equals(value6FromDataBase)) {
            errorMessageChangeLockedDownSeats.setText("Error! You haven't change anything, Can not update!");
        } else {
            // assign current value to each element
            seatsStatusFinal.put(SEAT_NO1, Boolean.parseBoolean(choiceBoxSeat1.getValue().toString()));
            seatsStatusFinal.put(SEAT_NO2, Boolean.parseBoolean(choiceBoxSeat2.getValue().toString()));
            seatsStatusFinal.put(SEAT_NO3, Boolean.parseBoolean(choiceBoxSeat3.getValue().toString()));
            seatsStatusFinal.put(SEAT_NO4, Boolean.parseBoolean(choiceBoxSeat4.getValue().toString()));
            seatsStatusFinal.put(SEAT_NO5, Boolean.parseBoolean(choiceBoxSeat5.getValue().toString()));
            seatsStatusFinal.put(SEAT_NO6, Boolean.parseBoolean(choiceBoxSeat6.getValue().toString()));
            for (Entry<Integer, Boolean> entry : seatsStatusFinal.entrySet()) { // for each element update their status if changed
                adminChangeLockDownSeatsModel.updateSeatsLockDownStatus(entry.getKey(), entry.getValue());
            }
            // if the database value original value is not equal to current value then it is changed and
            // if it is change from false to true if the value is true then we delete their corresponding
            // bookings and whitelist, if it changed from true to false we don't do anything, like unlock
            // if it is same, so not change at all we also not delete bookings and whitelist
            try {
                if (!choiceBoxSeat1.getValue().toString().equals(value1FromDataBase) && choiceBoxSeat1.getValue().toString().equals("true")) {
                    if (adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(SEAT_NO1)) { // if this seat have bookings
                        adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(SEAT_NO1);
                        adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(SEAT_NO1);
                    }
                }
                if (!choiceBoxSeat2.getValue().toString().equals(value2FromDataBase) && choiceBoxSeat2.getValue().toString().equals("true")) {
                    if (adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(SEAT_NO2)) {
                        adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(SEAT_NO2);
                        adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(SEAT_NO2);
                    }
                }
                if (!choiceBoxSeat3.getValue().toString().equals(value3FromDataBase) && choiceBoxSeat3.getValue().toString().equals("true")) {
                    if (adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(SEAT_NO3)) {
                        adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(SEAT_NO3);
                        adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(SEAT_NO3);
                    }
                }
                if (!choiceBoxSeat4.getValue().toString().equals(value4FromDataBase) && choiceBoxSeat4.getValue().toString().equals("true")) {
                    if (adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(SEAT_NO4)) {
                        adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(SEAT_NO4);
                        adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(SEAT_NO4);
                    }
                }
                if (!choiceBoxSeat5.getValue().toString().equals(value5FromDataBase) && choiceBoxSeat5.getValue().toString().equals("true")) {
                    if (adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(SEAT_NO5)) {
                        adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(SEAT_NO5);
                        adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(SEAT_NO5);
                    }
                }
                if (!choiceBoxSeat6.getValue().toString().equals(value6FromDataBase) && choiceBoxSeat6.getValue().toString().equals("true")) {
                    if (adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(SEAT_NO6)) {
                        adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(SEAT_NO6);
                        adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(SEAT_NO6);
                    }
                }
                switchToMainAdminScene();
                showChangeLockDownSeatsSuccessStage();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchToMainAdminScene() {
        Scene scene = borderPaneChangeLockDownSeats.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/mainAdmin.fxml"));
            primaryStage.setTitle("Hotdesking-Main-Admin");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the mainAdmin scene");
        }
    }

    public void showChangeLockDownSeatsSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminChangeLockDownSeatsSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Change Seat table-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminChangeLockDownSeatsSuccess.fxml");
        }
    }
}
