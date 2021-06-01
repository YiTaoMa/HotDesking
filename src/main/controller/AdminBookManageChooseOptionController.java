package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AdminBookManageChooseOptionController {
    @FXML
    private BorderPane borderPaneConfirmOrRejectAdmin;

    public void goBackToAdminManageBooking(ActionEvent event) {
        Scene scene = borderPaneConfirmOrRejectAdmin.getScene();
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

    public void switchToAdminConfirmBookingPrompt(ActionEvent event) {
        Scene scene = borderPaneConfirmOrRejectAdmin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminConfirmBookingPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Booking Management-Confirm");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminConfirmBookingPrompt.fxml");
        }
    }

    public void switchToAdminRejectBookingPrompt(ActionEvent event) {
        Scene scene = borderPaneConfirmOrRejectAdmin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminRejectBookingPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Booking Management-Reject");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminRejectBookingPrompt.fxml");
        }
    }

    public void switchToAdminLockdownSeatPrompt(ActionEvent event) {
        Scene scene = borderPaneConfirmOrRejectAdmin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminLockdownSeatPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Booking Management-Lock down Seat");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminLockdownSeatPrompt.fxml");
        }
    }
}
