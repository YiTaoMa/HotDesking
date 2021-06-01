package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminDeleteAccountPromptModel;

import java.io.IOException;

public class AdminDeleteAccountPromptController {
    AdminDeleteAccountPromptModel adminDeleteAccountPromptModel = new AdminDeleteAccountPromptModel();
    AdminManageAccountController adminManageAccountController = new AdminManageAccountController();
    @FXML
    private BorderPane borderPaneAdminDeleteAccount;

    public void switchToAdminManageAccount(ActionEvent event) {
        Scene scene = borderPaneAdminDeleteAccount.getScene();
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

    public void deleteSelectedAccount(ActionEvent event) {
        // will also delete this account related bookings and whitelist.
        if (adminDeleteAccountPromptModel.deleteSelectedEmployeeAccount(adminManageAccountController.getEmployeeIDFromAccountManageList())) {
            if (adminDeleteAccountPromptModel.isSelectedEmpHaveBookings(adminManageAccountController.getEmployeeIDFromAccountManageList())) {
                // if this employee have bookings
                if (adminDeleteAccountPromptModel.deleteSelectedEmpBookingRecords(adminManageAccountController.getEmployeeIDFromAccountManageList())) {
                    // delete their bookings
                    if (adminDeleteAccountPromptModel.deleteSelectedEmpWhitelistRecords(adminManageAccountController.getEmployeeIDFromAccountManageList())) {
                        // delete their whitelist and show success
                        switchToMainAdminScene();
                        showDeleteAccountSuccessStage();
                    }
                }
            } else { // else the employee don't have any bookings OR like admin don't have any bookings we don't delete their bookings just show success
                switchToMainAdminScene();
                showDeleteAccountSuccessStage();
            }
        }
    }

    public void switchToMainAdminScene() {
        Scene scene = borderPaneAdminDeleteAccount.getScene();
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

    public void showDeleteAccountSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminDeleteAccountSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Delete Account-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminDeleteAccountSuccess.fxml");
        }
    }
}
