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

public class empBookManageChooseOptionController {
    @FXML
    private BorderPane borderPaneEmpBMChooseOption;

    //SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();
    public void goBackToBookingManage(ActionEvent event){
        // everytime we go back to the booking page from here is go back to booking manage page so set it to true
        // but no needed
        /**every time click choose date to booking or choose from list ot manage it will set to manage to true or false, so when
         * you go back to booking page it go back to corresponding booking or booking management*/
        //selectBookingToManageEmpController.setIsBookingManagementEmp(true);
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
    /**when click update*/
    public void goToChooseDateAndSeat(ActionEvent event){
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
