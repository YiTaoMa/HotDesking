package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminConfirmBookingPromptModel;

import java.io.IOException;

public class AdminConfirmBookingPromptController {
    AdminConfirmBookingPromptModel adminConfirmBookingPromptModel = new AdminConfirmBookingPromptModel();
    AdminSelectBookingToManageController adminSelectBookingToManageController = new AdminSelectBookingToManageController();
    @FXML
    private BorderPane borderPaneConfirmBookingPrompt;

    public void switchToAdminBookManageChooseOption(ActionEvent event) {
        Scene scene = borderPaneConfirmBookingPrompt.getScene();
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

    public void confirmBookingByAdmin(ActionEvent event) {
        // set the seat id to 1,2,3,4,5,6 corresponding to seat clicked button 1,2,3,4,5,6
        // then a method in admin manage booking to get the seat id so we know each button
        // clicked will set to corresponding seat id to 1,2,3,4,5,6 then when it confirm, we compare, if the seat id is
        // equal to the seat id in the list which booked by the employee, if same means this seat can be confirmed or reject
        // if not same, this seat can only be locked down.
        // Now if we confirm it can be confirmed as this booking is 100% unconfirmed, as we did the check to see if it is confirmed or not
        // in admin booking controller, must be blue represent un confirmed, dark blue represent confirmed so don't have confirm option
        if (adminConfirmBookingPromptModel.updateHasConfirmed(adminSelectBookingToManageController.getSelectedEmpIdFromList(),
                adminSelectBookingToManageController.getDateForAdminManage())) { // if update success go to main admin page
            switchBackToMainAdmin();
            showUpdateConfirmStatusSuccessStage();
        }
    }

    public void switchBackToMainAdmin() {
        Scene scene = borderPaneConfirmBookingPrompt.getScene();
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

    public void showUpdateConfirmStatusSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminConfirmBookingSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Confirm Booking-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminConfirmBookingSuccess.fxml");
        }
    }
}

