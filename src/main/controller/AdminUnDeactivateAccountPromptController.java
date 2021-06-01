package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminUnDeactivateAccountPromptModel;

import java.io.IOException;
import java.sql.SQLException;

public class AdminUnDeactivateAccountPromptController {
    AdminManageAccountController adminManageAccountController = new AdminManageAccountController();
    AdminUnDeactivateAccountPromptModel adminUnDeactivateAccountPromptModel = new AdminUnDeactivateAccountPromptModel();
    @FXML
    private BorderPane borderPaneUnDeactivate;

    public void switchToAdminManageAccount(ActionEvent event) {
        Scene scene = borderPaneUnDeactivate.getScene();
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

    public void unDeactivateAccount(ActionEvent event) {
        try {
            if (adminUnDeactivateAccountPromptModel.updateDeactivateStatusToFalse(adminManageAccountController.getEmployeeIDFromAccountManageList())) {
                switchToMainAdminScene();
                showUnDeactivateSuccessStage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchToMainAdminScene() {
        Scene scene = borderPaneUnDeactivate.getScene();
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

    public void showUnDeactivateSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminUnDeactivateSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Un Deactivate Account-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminUnDeactivateSuccess.fxml");
        }
    }
}
