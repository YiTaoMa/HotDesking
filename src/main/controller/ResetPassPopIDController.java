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
import main.Main;
import main.model.LoginModel;
import main.model.ResetPassPopIDModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassPopIDController implements Initializable {
    public ResetPassPopIDModel resetPassPopIDModel = new ResetPassPopIDModel();
    protected static String SQ;//must make it static to be access by other
    //protected static String answerForSQ;
    protected static int employeeID;
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
                employeeID = idInt;
                //System.out.println(employeeID);
                //the only way go to the resetpassword is the id exist, so don't need to check if secret question is not found or what
                SQ = resetPassPopIDModel.getSecretQuestion(idInt);//get the secret question
                //answerForSQ = resetPassPopIDModel.getAnswerForSecretQuestion(idInt);//get answer for SQ
                if (resetPassPopIDModel.isIdExist(idInt)) {//calling model class see if id exist, if yes go to reset password

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
                    errorMessage.setText("Error, Employee ID not exist! Please register an new employee ID first!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) {// go back to login page
        //try {
        //    LoginController lc = new LoginController();
        //    Stage loginStage = lc.getLoginStage();//get login stage
        //    resetPassPopIdStage = (Stage) borderpaneResetPop.getScene().getWindow();// must do this again here can not initialize at the start
        //    resetPassPopIdStage.close();//close current stage
        //    loginStage.show();//show login stage
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        Scene scene = borderpaneResetPop.getScene();
        // from the scene, we try to access the primary stage
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;

        // load the second scene
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

    //public String getAnswerForSQ(){
    //    return answerForSQ;
    //}
    public int getEmployeeID(){
        return employeeID;
    }
    //public Stage getResetPassPopStage() { //get this current stage for other to use
    //    return resetPassPopIdStage;
    //}

}
