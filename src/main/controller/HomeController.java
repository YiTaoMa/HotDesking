package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button loginID;
    @FXML
    private Button registerID;

    public void homeLogin(ActionEvent event) {
        Scene scene = loginID.getScene();
        // from the scene, we try to access the primary stage
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        // load another scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
            primaryStage.setTitle("Hotdesking-Login");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the login scene");
        }
    }

    //Need to do this action set onclick in scene builder
    public void homeRegister(ActionEvent event) {
        Scene scene = registerID.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/register.fxml"));
            primaryStage.setTitle("Hotdesking-register");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the register scene");
        }
    }
}
