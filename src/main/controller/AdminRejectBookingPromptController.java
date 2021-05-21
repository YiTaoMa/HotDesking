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

public class AdminRejectBookingPromptController {
    @FXML
    private BorderPane borderPaneRejectPrompt;

    public void switchToAdminBookManageChooseOption(ActionEvent event) {
        Scene scene = borderPaneRejectPrompt.getScene();
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
    public void rejectBookingByAdmin(ActionEvent event) {
        //do reject operation
    }


}
