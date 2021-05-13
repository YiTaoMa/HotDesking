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

public class CancelBookingConfirmController {
    CancelBookingConfirmModel cancelBookingConfirmModel = new CancelBookingConfirmModel();
    SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();
    LoginController loginController = new LoginController();
    @FXML
    private BorderPane borderPaneCancelBookingConfirm;

    public void cancelBooking(ActionEvent event) { // if user said yes to cancel this booking
        try {
            // if delete record success
            if (cancelBookingConfirmModel.deleteBookingRecord(loginController.getEmployeeID(), selectBookingToManageEmpController.getDateForManage())) {
                // if delete whitelist corresponding record success
                if (cancelBookingConfirmModel.deleteWhitelistRecord(loginController.getEmployeeID(), selectBookingToManageEmpController.getDateForManage())) {
                    switchSceneToMain();
                    showCancelBookingSuccessStage();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBackToEmpBookManageChooseOption(ActionEvent event) {
        Scene scene = borderPaneCancelBookingConfirm.getScene();
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

    public void switchSceneToMain() {
        Scene scene = borderPaneCancelBookingConfirm.getScene();
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

    public void showCancelBookingSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/cancelBookingSuccessEmp.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Booking Cancel Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the cancelBookingSuccessEmp.fxml");
        }
    }
}
