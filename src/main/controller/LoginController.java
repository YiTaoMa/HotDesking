package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {//implements Initialize
    public LoginModel loginModel = new LoginModel();
    protected static int employeeID;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private BorderPane borderpaneLogin;

    //note: <?import javafx.scene.effect.Light?> need to be add if you see error in color

    /**
     * Java FX : initials method called automate by javafx right before the fxml file is displayed to the user
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
    }

    /* login Action method
       check if user input is the same as database.
     */
    //This is the method link to the login button, if button clicked, it will call this method
    public void login(ActionEvent event) {
        try {
            if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText())) { // if login success
                employeeID = loginModel.getEmployeeId(txtUsername.getText(), txtPassword.getText());
                if (loginModel.getRole(txtUsername.getText(), txtPassword.getText()).equals("Employee")) {
                    Scene scene = borderpaneLogin.getScene();
                    Window window = scene.getWindow();
                    Stage primaryStage = (Stage) window;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../ui/main.fxml"));
                        primaryStage.setTitle("Hotdesking-Main");
                        primaryStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        System.out.println("Cannot load the main scene");
                    }
                } else if (loginModel.getRole(txtUsername.getText(), txtPassword.getText()).equals("Admin")) {
                    Scene scene = borderpaneLogin.getScene();
                    Window window = scene.getWindow();
                    Stage primaryStage = (Stage) window;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../ui/mainAdmin.fxml"));
                        primaryStage.setTitle("Hotdesking-Main-Admin");
                        primaryStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        System.out.println("Cannot load the mainAdmin scene");
                    }
                }
            } else {
                errorMessage.setText("Username and Password is incorrect OR This account is Deactivated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) { //go back to home.fxml
        //get current scene which is login
        Scene scene = borderpaneLogin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        // load the home.fxml
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/home.fxml"));
            primaryStage.setTitle("Hotdesking-Home");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the home scene");
        }
    }

    public void forgotPassword(ActionEvent event) {
        //get login scene which is this login
        Scene scene = borderpaneLogin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        // load the resetPassPopID.fxml
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassPopID.fxml"));
            primaryStage.setTitle("Hotdesking-Reset Password-Enter ID");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the resetPassPopID scene");
        }
    }

    /**
     * This can be done using singleton just call set id from singleton class and get id from other controller then you don't
     * need to create controller reference everytime needed
     */
    public int getEmployeeID() {
        return employeeID;
    }
}
