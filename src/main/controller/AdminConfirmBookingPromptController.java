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

public class AdminConfirmBookingPromptController {
    @FXML
    private BorderPane borderPaneConfirmBookingPrompt;
    // go back to choose option
    public void switchToAdminBookManageChooseOption(ActionEvent event) {
        Scene scene = borderPaneConfirmBookingPrompt.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminBookManageChooseOption.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Choose Option");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminBookManageChooseOption.fxml");
        }
    }
    public void confirmBookingByAdmin(ActionEvent event) {
        // set the seat id to 1,2,3,4,5,6 corresponding to seat clicked button 1,2,3,4,5,6
        // then a method in admin manage booking to get the setted seat id so we know each button
        //clicked will set to corresponding seat id to 1,2,3,4,5,6 then when it confirm, we compare, if the seat id is
        //equal to the seat id in the list which booked by the employee, if same means this seat can be confirmed or reject
        //if not same, this seat can only be locked down.
        //
    }


}
