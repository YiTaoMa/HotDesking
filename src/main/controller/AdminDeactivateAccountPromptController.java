package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminDeactivateAccountPromptModel;

import java.io.IOException;
import java.sql.SQLException;

public class AdminDeactivateAccountPromptController {
    AdminDeactivateAccountPromptModel adminDeactivateAccountPromptModel = new AdminDeactivateAccountPromptModel();
    AdminManageAccountController adminManageAccountController = new AdminManageAccountController();
    @FXML
    private BorderPane borderPaneDeactivatePrompt;

    public void switchToAdminManageAccount(ActionEvent event) {
        Scene scene = borderPaneDeactivatePrompt.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminManageAccount.fxml"));
            primaryStage.setTitle("Hotdesking-Manage Account-Admin");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminManageAccount.fxml");
        }
    }

    public void deactivateAccount(ActionEvent event) {
        try {
            // parameter passed is employee id from the list
            if (adminDeactivateAccountPromptModel.updateDeactivateStatus(adminManageAccountController.getEmployeeIDFromAccountManageList())) {
                // if we update deactivate status success
                switchToMainAdminScene();
                showDeactivateSuccessStage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showDeactivateSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminDeactivateSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Deactivate Account-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminDeactivateSuccess.fxml");
        }
    }

    public void switchToMainAdminScene() {
        Scene scene = borderPaneDeactivatePrompt.getScene();
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
}
