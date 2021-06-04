package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.ResetPassPopIDModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassPopIDController implements Initializable {
    public ResetPassPopIDModel resetPassPopIDModel = new ResetPassPopIDModel();
    protected static String SQ;//must make it static to be access by other
    protected static int employeeID;
    @FXML
    private BorderPane borderpaneResetPop;
    @FXML
    private TextField txtID;
    @FXML
    private Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
    }

    public void goToResetPassword(ActionEvent event) {
        try {
            String IdString = txtID.getText();//must do it here
            if (IdString.trim().equals("") || !IdString.matches("^[1-9]\\d*|0$")) { //id cannot be empty or not int
                errorMessage.setText("Error, Employee ID can not be empty and must be a positive whole number!");
            } else {
                int idInt = Integer.parseInt(IdString);
                employeeID = idInt;
                //the only way go to the reset password is the id exist, so don't need to check if secret question is not found or what
                SQ = resetPassPopIDModel.getSecretQuestion(idInt);//get the secret question
                if (resetPassPopIDModel.isIdExistAndNotDeactivated(idInt)) {//calling model class see if id exist, if yes go to reset password
                    Scene scene = borderpaneResetPop.getScene();
                    Window window = scene.getWindow();
                    Stage primaryStage = (Stage) window;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassword.fxml"));
                        primaryStage.setTitle("Hotdesking-Reset Password");
                        primaryStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        System.out.println("Cannot load the resetPassword scene");
                    }
                } else {
                    errorMessage.setText("Error, Employee ID not exist OR Deactivated!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) {// go back to login page
        Scene scene = borderpaneResetPop.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
            primaryStage.setTitle("Hotdesking-Login");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the resetPassword scene");
        }
    }

    public String getSQ() { //for reset passwrod page to get the secret question
        return SQ;
    }

    public int getEmployeeID() {
        return employeeID;
    }
}
