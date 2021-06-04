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
    public ResetPassPopIDController resetPassPopIDController = new ResetPassPopIDController();
    public ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    protected static String newPassword;
    @FXML
    private Label secretQuestion;
    @FXML
    private BorderPane borderpaneResetPass;
    @FXML
    private TextField txtAnswerForSQ;
    @FXML
    private Label answerSQWrongError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get secret question for this user.
        secretQuestion.setText(resetPassPopIDController.getSQ());
        answerSQWrongError.setText("");
    }

    public void resetPassword(ActionEvent event) {
        int employeeID = resetPassPopIDController.getEmployeeID();
        try {
            if (resetPasswordModel.isAnswerForSQCorrect(employeeID, txtAnswerForSQ.getText())) { // note: answer is not get from the reset Pass pop
                //it is get from the one that user inputted again which is reset password this fxml
                newPassword = resetPasswordModel.generateRandomPassword(10);//generate new password
                if (resetPasswordModel.updatePassword(employeeID, newPassword)) { //update new password to the database
                    Scene scene = borderpaneResetPass.getScene();
                    Window window = scene.getWindow();
                    Stage primaryStage = (Stage) window;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassSuccess.fxml"));
                        primaryStage.setTitle("Hotdesking-Reset Password Success");
                        primaryStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        System.out.println("Cannot load the reset Password Success scene");
                    }
                }
            } else {
                answerSQWrongError.setText("Your answer is incorrect! Can not reset the password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) { // go back to previous page which is resetPassPopID
        Scene scene = borderpaneResetPass.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/resetPassPopID.fxml"));
            primaryStage.setTitle("Hotdesking-Reset Password-Enter ID");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the resetPassword scene");
        }
    }

    public String getNewPassword() {
        return newPassword;
    }
}
