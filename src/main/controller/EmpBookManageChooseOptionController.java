package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmpBookManageChooseOptionController implements Initializable {
    SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();

    @FXML
    private BorderPane borderPaneEmpBMChooseOption;
    @FXML
    private Label chooseOptionErrorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseOptionErrorMessage.setText("");
    }

    public void goBackToBookingManage(ActionEvent event) {
        // everytime we go back to the booking page from here is go back to booking manage page so set it to true
        // but no needed
        /**every time click choose date to booking or choose from list ot manage it will set to manage to true or false, so when
         * you go back to booking page it go back to corresponding booking or booking management*/
        Scene scene = borderPaneEmpBMChooseOption.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/booking.fxml"));
            primaryStage.setTitle("Hotdesking-Booking Management");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the booking.fxml");
        }
    }

    /**
     * when click update
     */
    public void goToChooseDateAndSeat(ActionEvent event) {

        LocalDate dateConvertedFromList = convertDateFromListToLocalDate();
        // time validation 48 hours
        if (!checkDateLimit(dateConvertedFromList)) {
            chooseOptionErrorMessage.setText("Error! This seat can't be updated. You should update it 48 hours before this booking!");
        } else {
            Scene scene = borderPaneEmpBMChooseOption.getScene();
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/chooseDateAndSeatUpdateEmp.fxml"));
                primaryStage.setTitle("Hotdesking-New date and seat for booking");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the chooseDateAndSeatUpdateEmp.fxml");
            }
        }
    }

    public void switchToCancelBookingScene(ActionEvent event) {
        LocalDate dateConvertedFromListCancel = convertDateFromListToLocalDate();
        // time validation 48 hours
        if (!checkDateLimit(dateConvertedFromListCancel)) {
            chooseOptionErrorMessage.setText("Error! This seat can't be canceled. You should cancel it 48 hours before this booking!");
        } else {
            Scene scene = borderPaneEmpBMChooseOption.getScene();
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/cancelBookingConfirm.fxml"));
                primaryStage.setTitle("Hotdesking-Cancel Booking Confirm");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the cancelBookingConfirm.fxml");
            }
        }
    }

    public void switchToConfirmCheckinScene(ActionEvent event) {
        if (selectBookingToManageEmpController.getIsCheckedIn()) {
            chooseOptionErrorMessage.setText("This booking/seat is already checked in, Can not check in again!");
        } else if (!selectBookingToManageEmpController.getHasConfirmedFromList()) {
            chooseOptionErrorMessage.setText("This booking not confirmed by the Admin, Can not check in!");
        } else {
            Scene scene = borderPaneEmpBMChooseOption.getScene();
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/checkinBookingConfirm.fxml"));
                primaryStage.setTitle("Hotdesking-Confirm Check in");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the checkinBookingConfirm.fxml");
            }
        }
    }

    public boolean checkDateLimit(LocalDate date) {
        // convert Date into Java 8 LocalDate
        LocalDate today = LocalDate.now();
        // count number of days between the given date and today
        long days = ChronoUnit.DAYS.between(today, date); //11-14
        return days > 2;
    }

    public LocalDate convertDateFromListToLocalDate() {
        String dateEmpFromList = selectBookingToManageEmpController.getDateForManage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateConverted = LocalDate.parse(dateEmpFromList, formatter);
        return dateConverted;
    }
}
