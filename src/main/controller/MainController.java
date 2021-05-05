package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;

public class MainController {
    @FXML
    private BorderPane borderpaneMain;


    public void goHome(ActionEvent event) { //after register or login can not go back to login or register just go to home then can access login or register again
        //go back to home page
        try {
            Stage mainStage = (Stage) borderpaneMain.getScene().getWindow();//use the borderpane to get the stage for this register
            mainStage.close();
            Main.stg.show();//we call the home page to show

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
