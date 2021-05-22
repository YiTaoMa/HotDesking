package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminLockdownSeatPromptModel;
import main.model.AdminSelectBookingToManageModel;

import java.io.IOException;
import java.sql.SQLException;

public class AdminLockdownSeatPromptController {
    AdminSelectBookingToManageController adminSelectBookingToManageController = new AdminSelectBookingToManageController();
    AdminLockdownSeatPromptModel adminLockdownSeatPromptModel = new AdminLockdownSeatPromptModel();
    @FXML
    private BorderPane borderPaneLockDownPrompt;

    // go back to choose option
    public void switchToAdminBookManageChooseOption(ActionEvent event) {
        Scene scene = borderPaneLockDownPrompt.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminBookManageChooseOption.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Choose Option");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminBookManageChooseOption.fxml");
        }
    }

    public void lockDownSeatByAdmin(ActionEvent event) {
        // do lock down operation
        // note: this is unconfirmed seat and user chose is blue
        //1: delete all booking and whitelist if the seat is the seat we locked down
        //2: 拿到这个桌子的编号第几号桌子 list 里面拿到，然后在lockdown表里面把它变成true， 这样结束后返回到主界面。
        try {
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
