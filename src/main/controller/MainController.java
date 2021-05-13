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

public class MainController {
    @FXML
    private BorderPane borderpaneMain;

    public void booking(ActionEvent event) {
        Scene scene = borderpaneMain.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/chooseDate.fxml"));
            primaryStage.setTitle("Hotdesking-Choose Date");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the chooseDate.fxml");
        }
    }

    public void goHome(ActionEvent event) { //after register or login can not go back to login or register just go to home then can access login or register again
        Scene scene = borderpaneMain.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/home.fxml"));
            primaryStage.setTitle("Hotdesking-Home");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the login scene");
        }
    }

    public void manageBooking(ActionEvent event) {
        Scene scene = borderpaneMain.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/selectBookingToManageEmp.fxml"));
            primaryStage.setTitle("Hotdesking-Manage Booking-Employee");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the selectBookingToManageEmp.fxml");
        }
    }
}
