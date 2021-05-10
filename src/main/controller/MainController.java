package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.Main;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane borderpaneMain;

public void booking(ActionEvent event){
    Scene scene = borderpaneMain.getScene();
    // from the scene, we try to access the primary stage
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
        //go back to home page
        //try {
        //    Stage mainStage = (Stage) borderpaneMain.getScene().getWindow();//use the borderpane to get the stage for this register
        //    mainStage.close();
        //    Main.stg.show();//we call the home page to show
        //
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        Scene scene = borderpaneMain.getScene();
        // from the scene, we try to access the primary stage
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;

        // load the second scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/home.fxml"));
            primaryStage.setTitle("Hotdesking-Home");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the login scene");
        }
    }

    public void manageBooking(ActionEvent event){

    }
}
