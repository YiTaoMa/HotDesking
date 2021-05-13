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
import java.util.ResourceBundle;

public class ResetPassSuccessController implements Initializable {
    public ResetPasswordController resetPasswordController = new ResetPasswordController();
    @FXML
    private Label newPassword;
    @FXML
    private BorderPane borderpaneResetPassSuccess;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newPassword.setText(resetPasswordController.getNewPassword()); // set random password
    }

    public void goBackToLogin(ActionEvent event) {
        Scene scene = borderpaneResetPassSuccess.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
            primaryStage.setTitle("Hotdesking-Login");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the Login.fxml");
        }
    }
}
