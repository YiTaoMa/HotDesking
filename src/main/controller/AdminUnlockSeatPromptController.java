package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminUnlockSeatPromptModel;

import java.io.IOException;
import java.sql.SQLException;

public class AdminUnlockSeatPromptController {
    AdminUnlockSeatPromptModel adminUnlockSeatPromptModel = new AdminUnlockSeatPromptModel();
    AdminManageBookingController adminManageBookingController = new AdminManageBookingController();
    @FXML
    private BorderPane borderPaneUnlockSeat;

    // go back to manage booking
    public void switchToAdminBookManage(ActionEvent event) {
        Scene scene = borderPaneUnlockSeat.getScene();
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

    public void unlockClickedSeat(ActionEvent event) {
        try {
            if (adminUnlockSeatPromptModel.updateSeatIdLockedDownToUnlock(adminManageBookingController.getSeatIdCurrentClicked())) {
                switchBackToMainAdmin();
                showUnlockSuccessStage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchBackToMainAdmin() {
        Scene scene = borderPaneUnlockSeat.getScene();
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

    public void showUnlockSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminUnlockSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Unlock Seat-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminUnlockSuccess.fxml");
        }
    }
}
