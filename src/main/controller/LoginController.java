package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.model.LoginModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }

    }
    /* login Action method
       check if user input is the same as database.
     */
    //This is the method link to the login button, if button clicked, it will call this method

    //It's more simple to create multiple FXML and Controller. 1 FXML = 1Controller. And you can comunicate betweeen the different controller with no problems.
    //in the scene builder left hand side, there is a controller class, you must add controller class in order to do the on click action
    public void Login(ActionEvent event){

        try {
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){

                isConnected.setText("Logged in successfully");
            }else{
                isConnected.setText("username and password is incorrect");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //11.2.3 big sur



}
