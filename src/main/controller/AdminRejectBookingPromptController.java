package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.CancelBookingConfirmModel;

import java.io.IOException;
import java.sql.SQLException;

public class AdminRejectBookingPromptController {
    // As admin reject booking is same as employee reject booking, just delete the record
    CancelBookingConfirmModel cancelBookingConfirmModel = new CancelBookingConfirmModel();
    AdminSelectBookingToManageController adminSelectBookingToManageController = new AdminSelectBookingToManageController();
    @FXML
    private BorderPane borderPaneRejectPrompt;

    public void switchToAdminBookManageChooseOption(ActionEvent event) {
        Scene scene = borderPaneRejectPrompt.getScene();
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

    public void rejectBookingByAdmin(ActionEvent event) {
        try {
            if (cancelBookingConfirmModel.deleteBookingRecord(adminSelectBookingToManageController.getSelectedEmpIdFromList(),
                    adminSelectBookingToManageController.getDateForAdminManage())) {
                // if delete this record successful, then we delete corresponding whitelist
                if (cancelBookingConfirmModel.deleteWhitelistRecord(adminSelectBookingToManageController.getSelectedEmpIdFromList(),
                        adminSelectBookingToManageController.getDateForAdminManage())) {
                    // if corresponding whitelist delete success go back to main and show success message.
                    switchBackToMainAdmin();
                    showRejectBookingSuccessStage();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchBackToMainAdmin() {
        Scene scene = borderPaneRejectPrompt.getScene();
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

    public void showRejectBookingSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminRejectBookingSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Reject Booking-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminRejectBookingSuccess.fxml");
        }
    }
}
