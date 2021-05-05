package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;
import main.model.LoginModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {//implements Initializable
    //public static Stage stgLogin;
    public LoginModel loginModel = new LoginModel();
    protected static Stage loginStage;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private BorderPane borderpaneLogin;


    //@FXML
    //private Button buttonLogin; //you must set id in the scene builder uner code
    //may be used for bind??
    //note: <?import javafx.scene.effect.Light?> need to be add if you see error in color


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //if (loginModel.isDbConnected()){
        //    isConnected.setText("Connected");
        //}else{
        //    isConnected.setText("Not Connected");
        //}
        /**IMPORTANT!!! can not initialise here*/
        //loginStage = (Stage) borderpaneLogin.getScene().getWindow();
        errorMessage.setText("");
    }
    /* login Action method
       check if user input is the same as database.
     */
    //This is the method link to the login button, if button clicked, it will call this method

    //It's more simple to create multiple FXML and Controller. 1 FXML = 1Controller. And you can comunicate betweeen the different controller with no problems.
    //in the scene builder left hand side, there is a controller class, you must add controller class in order to do the on click action
    public void Login(ActionEvent event) {

        try {
            if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText())) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../ui/main.fxml")); // worked now, but need to close the previous login page
                    //every time create new page you need to se the controller in scene builder first!
                    //create another stage
                    Stage stage = new Stage();
                    //this.stgLogin = stage;
                    stage.setScene(new Scene(root));
                    stage.setTitle("Hotdesking-Main");
                    loginStage = (Stage) borderpaneLogin.getScene().getWindow();//use the borderpane to get the stage for this login
                    loginStage.close(); // close the login page
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //isConnected.setText("Logged in successfully");
            } else {
                errorMessage.setText("username and password is incorrect");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) {
        try {
            Main.stg.show();//we call the home page to show
            loginStage = (Stage) borderpaneLogin.getScene().getWindow();//use the borderpane to get the stage for this login
            loginStage.close(); // close the login page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPassword(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassPopID.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Enter ID to Reset Password");
            loginStage = (Stage) borderpaneLogin.getScene().getWindow();//use the borderpane to get the stage for this login
            loginStage.close(); // close the login page
            stage.show();//we call the home page to show

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //problem is it can not be static as the borderpanelogin can not be static
    public Stage getLoginStage() {
        return loginStage;
    }
}
