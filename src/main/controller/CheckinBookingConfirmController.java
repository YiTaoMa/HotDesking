package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.CheckinBookingConfirmModel;

import java.io.IOException;
import java.sql.SQLException;

public class CheckinBookingConfirmController {
    CheckinBookingConfirmModel checkinBookingConfirmModel = new CheckinBookingConfirmModel();
    SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();
    LoginController loginController = new LoginController();
    @FXML
    private BorderPane borderPaneConfirmCheckin;

    public void checkinBooking(ActionEvent event) {
        try {
            if (checkinBookingConfirmModel.updateCheckinStatus(loginController.getEmployeeID(), selectBookingToManageEmpController.getDateForManage())) {
                switchToMainScene();
                showCheckinBookingSuccessStage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBackToEmpBookManageChooseOption(ActionEvent event) {
        Scene scene = borderPaneConfirmCheckin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/empBookManageChooseOption.fxml"));
            primaryStage.setTitle("Hotdesking-Booking Management-Choose Options");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the empBookManageChooseOption.fxml");
        }
    }

    public void switchToMainScene() {
        Scene scene = borderPaneConfirmCheckin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/main.fxml"));
            primaryStage.setTitle("Hotdesking-Main");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the Main scene");
        }
    }

    public void showCheckinBookingSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/checkinBookingSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Booking Checked in Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the checkinBookingSuccess.fxml");
        }
    }
}
