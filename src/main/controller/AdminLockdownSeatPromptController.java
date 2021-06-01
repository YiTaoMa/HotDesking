package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminChangeLockDownSeatsModel;
import main.model.AdminLockdownSeatPromptModel;

import java.io.IOException;
import java.sql.SQLException;

public class AdminLockdownSeatPromptController {
    AdminManageBookingController adminManageBookingController = new AdminManageBookingController();
    AdminSelectBookingToManageController adminSelectBookingToManageController = new AdminSelectBookingToManageController();
    AdminLockdownSeatPromptModel adminLockdownSeatPromptModel = new AdminLockdownSeatPromptModel();
    AdminChangeLockDownSeatsModel adminChangeLockDownSeatsModel = new AdminChangeLockDownSeatsModel();
    @FXML
    private BorderPane borderPaneLockDownPrompt;

    // go back to manage booking
    public void switchToAdminBookManage(ActionEvent event) {
        Scene scene = borderPaneLockDownPrompt.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminManageBooking.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Booking Management");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminManageBooking.fxml");
        }
    }

    public void lockDownSeatByAdmin(ActionEvent event) {
        // do lock down operation
        // note: this is unconfirmed seat and user chose is blue

        /**if it it red or green seat clicked, we will do different operation as the parameter seat passed is different,
         * this situation, the parameter seat is the corresponding seat admin clicked and we will delete/update
         * all related things about this seat that clicked, so we can not get hte seat like darkblue or blue directly from the list.
         * Process: if it is red or green in admin manage booking controller --> adminBMChooseOptionGRController --> this current controller*/
        try {
            if (adminManageBookingController.getIsSeatRedOrGreen()) { // if it is red or green seat
                if (adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(adminManageBookingController.getSeatIdCurrentClicked())) { // if we have bookings for this seat
                    if (adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(adminManageBookingController.getSeatIdCurrentClicked())) {
                        if (adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(adminManageBookingController.getSeatIdCurrentClicked())) {
                            if (adminLockdownSeatPromptModel.updateSeatIdLockedDown(adminManageBookingController.getSeatIdCurrentClicked())) {
                                switchBackToMainAdmin();
                                showLockDownSuccessStage();
                            }
                        }
                    }
                } else {
                    if (adminLockdownSeatPromptModel.updateSeatIdLockedDown(adminManageBookingController.getSeatIdCurrentClicked())) {
                        switchBackToMainAdmin();
                        showLockDownSuccessStage();
                    }
                }
            } else { // else is not red or green
                // delete all this seat number related booking in the Booking table
                if (adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(adminSelectBookingToManageController.getSeatIDBookedByCurrentUserAdminManage())) {
                    // delete all this seat number related booking in the Whitelist table
                    if (adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(adminSelectBookingToManageController.getSeatIDBookedByCurrentUserAdminManage())) {
                        // update Seat table corresponding seat ID locked just now to lock status
                        if (adminLockdownSeatPromptModel.updateSeatIdLockedDown(adminSelectBookingToManageController.getSeatIDBookedByCurrentUserAdminManage())) {
                            switchBackToMainAdmin();
                            showLockDownSuccessStage();
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchBackToMainAdmin() {
        Scene scene = borderPaneLockDownPrompt.getScene();
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

    public void showLockDownSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminLockdownSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Lock down Seat-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminLockdownSuccess.fxml");
        }
    }
}
