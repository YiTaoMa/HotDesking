package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminGenerateReportModel;

import java.io.File;
import java.io.IOException;

/**
 * Let user to choose the location
 */
public class AdminGenerateReportController {
    AdminGenerateReportModel adminGenerateReportModel = new AdminGenerateReportModel();
    @FXML
    private BorderPane borderPaneReport;

    public void generateReportForEmployee(ActionEvent event) {
        Window stage = borderPaneReport.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("EmployeeReport");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            adminGenerateReportModel.generateEmployeeReport(file);
            switchToMainAdminScene();
            showGenerateReportSuccessStage();
        }
    }

    public void generateReportForBooking(ActionEvent event) {
        Window stage = borderPaneReport.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("BookingReport");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            adminGenerateReportModel.generateBookingReport(file);
            switchToMainAdminScene();
            showGenerateReportSuccessStage();
        }
    }

    public void goBackToMainAdmin(ActionEvent event) {
        Scene scene = borderPaneReport.getScene();
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

    public void switchToMainAdminScene() {
        Scene scene = borderPaneReport.getScene();
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

    public void showGenerateReportSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminGenerateReportSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Generate Report-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminGenerateReportSuccess.fxml");
        }
    }
}
