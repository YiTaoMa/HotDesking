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
import main.Main;
import main.model.LoginModel;
import main.model.ResetPassPopIDModel;

import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassPopIDController implements Initializable {
    public ResetPassPopIDModel resetPassPopIDModel = new ResetPassPopIDModel();
    protected static String SQ;//must make it static to be access by other
    protected static Stage resetPassPopIdStage;
    @FXML
    private BorderPane borderpaneResetPop;
    @FXML
    private TextField txtID;
    @FXML
    private Label errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //resetPassPopIdStage = (Stage) borderpaneResetPop.getScene().getWindow();
        errorMessage.setText("");
    }

    public void goToResetPassword(ActionEvent event) {
        try {
            String IdString = txtID.getText();//must od it here
            if (IdString.trim().equals("") || !IdString.matches("^[1-9]\\d*|0$")) { //id cannot be empty or not int
                errorMessage.setText("Error, Employee ID can not be empty and must be a positive whole number!");
            } else {
                int idInt = Integer.parseInt(IdString);
                //the only way go to the resetpassword is the id exist, so don't need to check if secret question is not found or what
                SQ = resetPassPopIDModel.getSecretQuestion(idInt);//get the secret question
                if (resetPassPopIDModel.isIdExist(idInt)) {//calling model class see if id exist, if yes go to reset password
                    Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassword.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Hotdesking-Reset Password");
                    resetPassPopIdStage = (Stage) borderpaneResetPop.getScene().getWindow();//get current stage
                    resetPassPopIdStage.close();//claso current stage
                    stage.show();
                } else {
                    errorMessage.setText("Error, Employee ID not exist! Please register an new employee ID first!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) {// go back to login page
        try {
            LoginController lc = new LoginController();
            Stage loginStage = lc.getLoginStage();//get login stage
            resetPassPopIdStage = (Stage) borderpaneResetPop.getScene().getWindow();// must do this again here can not initialize at the start
            resetPassPopIdStage.close();//close current stage
            loginStage.show();//show login stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSQ() { //for reset passwrod page to get the secret question
        return SQ;
    }

    public Stage getResetPassPopStage() { //get this current stage for other to use
        return resetPassPopIdStage;
    }

}
