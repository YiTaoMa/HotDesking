package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.Main;

import java.io.IOException;

public class HomeController { //delete initialise here, no need to do?
    //public static Stage stgHome;
    // Check database connection
    //@Override
    //public void initialize(URL location, ResourceBundle resources) {
    //
    //    System.out.println("");
    //}
    @FXML
    private Button loginID;
    @FXML
    private Button registerID;

    public void homeLogin(ActionEvent event) {
            ////click login button on home page go to login fxml
            //Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml")); // have error here can not find the fxml
            ////create another stage
            //Stage stage = new Stage();
            ////this.stgHome = stage;
            //stage.setScene(new Scene(root));
            //stage.setTitle("Hotdesking-Login");
            //Main.stg.close(); // close the first stage which is home
            //stage.show();

            Scene scene = loginID.getScene();
            // from the scene, we try to access the primary stage
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;

            // load the second scene
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
                primaryStage.setTitle("Hotdesking-Login");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the login scene");
            }
    }
    //need to do this action set onclick in scene builder
    public void homeRegister(ActionEvent event) {

            ////click register button on home page go to register fxml
            //Parent root = FXMLLoader.load(getClass().getResource("../ui/register.fxml")); // have error here can not find the fxml
            ////create another stage
            //Stage stage = new Stage();
            ////this.stgHome = stage;
            //stage.setScene(new Scene(root));
            //stage.setTitle("Hotdesking-Register");
            //Main.stg.close(); // close the first stage which is home
            //stage.show();

            Scene scene = registerID.getScene();
            // from the scene, we try to access the primary stage
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;

            // load the second scene
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/register.fxml"));
                primaryStage.setTitle("Hotdesking-register");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the register scene");
            }
    }
}


//public class HomeController {
//    public void homeLogin(ActionEvent event) throws Exception{
//        Stage primaryStage = (Stage) LoginController.getScene().getWindow();
//        Parent newRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
//
//        primaryStage.getScene().setRoot(newRoot);
//
//    }
//}
