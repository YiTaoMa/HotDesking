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

public class AdminBMChooseOptionGRController {
    @FXML
    private BorderPane borderPaneAdminChooseOptionGR;

    public void  goBackToAdminManageBooking(ActionEvent event) {
        Scene scene = borderPaneAdminChooseOptionGR.getScene();
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

    public void switchToAdminLockdownSeatPrompt(ActionEvent event) {
        Scene scene = borderPaneAdminChooseOptionGR.getScene();
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
