package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassController implements Initializable {
    //public ResetPassPopIDModel resetPassPopIDModel = new ResetPassPopIDModel();
   public ResetPassPopIDController resetPassPopIDController = new ResetPassPopIDController();
   protected static Stage resetPassStage;
    @FXML
    private Label secretQuestion;
    @FXML
    private BorderPane borderpaneResetPass;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //resetPassStage = (Stage) borderpaneResetPass.getScene().getWindow(); // can not do here
        //System.out.println(resetPassPopIDController.getSQ());
        secretQuestion.setText(resetPassPopIDController.getSQ());
    }

    public void goBack(ActionEvent event) { // go back to previouse page which is resetPassPopID
        try {
            Stage resetPassPopIDStage = resetPassPopIDController.getResetPassPopStage();
            resetPassStage = (Stage) borderpaneResetPass.getScene().getWindow();
            resetPassStage.close();
            resetPassPopIDStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
