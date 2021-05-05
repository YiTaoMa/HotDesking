package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import main.model.LoginModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController { //delete initialise here, no need to do?
    //public static Stage stgHome;
    // Check database connection
    //@Override
    //public void initialize(URL location, ResourceBundle resources) {
    //
    //    System.out.println("");
    //}

    public void homeLogin(ActionEvent event) {

        try {
            //click login button on home page go to login fxml
            Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml")); // have error here can not find the fxml
            //create another stage
            Stage stage = new Stage();
            //this.stgHome = stage;
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Login");
            Main.stg.close(); // close the first stage which is home
            stage.show();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    //need to do this action set onclick in scene builder
    public void homeRegister(ActionEvent event) {
        try {
            //click register button on home page go to register fxml
            Parent root = FXMLLoader.load(getClass().getResource("../ui/register.fxml")); // have error here can not find the fxml
            //create another stage
            Stage stage = new Stage();
            //this.stgHome = stage;
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Register");
            Main.stg.close(); // close the first stage which is home
            stage.show();

        }
        catch(Exception e){
            e.printStackTrace();
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
