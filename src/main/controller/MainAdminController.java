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

public class MainAdminController {
    @FXML
    private BorderPane borderPaneMainAdmin;

    public void switchToManageBooking(ActionEvent event) {
        Scene scene = borderPaneMainAdmin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminSelectBookingToManage.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Select Booking Manage");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminSelectBookingToManage.fxml");
        }
    }
}
