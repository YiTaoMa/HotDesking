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
import main.model.ResetPasswordModel;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {
    //public ResetPassPopIDModel resetPassPopIDModel = new ResetPassPopIDModel();
    public ResetPassPopIDController resetPassPopIDController = new ResetPassPopIDController();
    public ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    protected static String newPasswrod;
    protected static Stage resetPassStage;
    @FXML
    private Label secretQuestion;
    @FXML
    private BorderPane borderpaneResetPass;
    @FXML
    private TextField txtAnswerForSQ;
    @FXML
    private Label answerSQWrongError;
    //@FXML
    //private Label newPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //resetPassStage = (Stage) borderpaneResetPass.getScene().getWindow(); // can not do here
        //System.out.println(resetPassPopIDController.getSQ());
        //when you load a fxml so set scene it will first do the initialise, so sometimes failed because something in initialize wrong
        secretQuestion.setText(resetPassPopIDController.getSQ());
        answerSQWrongError.setText("");
    }

    public void resetPassword(ActionEvent event) {
        //String SQ = resetPassPopIDController.getSQ();
        //String answerForSQ = resetPassPopIDController.getAnswerForSQ();//get the answer for SQ from poop controller
        int employeeID = resetPassPopIDController.getEmployeeID();

        try {
            if (resetPasswordModel.isAnswerForSQCorrect(employeeID, txtAnswerForSQ.getText())) { // note: answer is not get from the reset Pass pop
                //it is get from the one that user inputed again which is reset password this fxml
                newPasswrod = resetPasswordModel.generateRandomPassword(10);//generate new password
                resetPasswordModel.updatePassword(employeeID, newPasswrod);//update new password to the database
                    Scene scene = borderpaneResetPass.getScene();
                    Window window = scene.getWindow();
                    Stage primaryStage = (Stage) window;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassSuccess.fxml"));
                        primaryStage.setTitle("Hotdesking-Reset Password Success");
                        primaryStage.setScene(new Scene(root));
                        //Parent root2 = FXMLLoader.load(getClass().getResource("../ui/generateReportEmployee.fxml"));
                        //Stage stage = new Stage();
                        ////this.stgLogin = stage;
                        //stage.setScene(new Scene(root2));
                        //stage.show();
                    } catch (IOException e) {
                        System.out.println("Cannot load the reset Password Success scene");
                    }
            }
            else{
                answerSQWrongError.setText("Your answer is incorrect! Can not reset the password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) { // go back to previouse page which is resetPassPopID
        //try {
        //    Stage resetPassPopIDStage = resetPassPopIDController.getResetPassPopStage();
        //    resetPassStage = (Stage) borderpaneResetPass.getScene().getWindow();
        //    resetPassStage.close();
        //    resetPassPopIDStage.show();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        Scene scene = borderpaneResetPass.getScene();
        // from the scene, we try to access the primary stage
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;

        // load the second scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassPopID.fxml"));
            primaryStage.setTitle("Hotdesking-Reset Password-Enter ID");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the resetPassword scene");
        }
    }

    public String getNewPassword(){
        return newPasswrod;
    }
}
