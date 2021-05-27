package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminManageAccountModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminManageAccountController implements Initializable {
    ObservableList<String> accounts = FXCollections.observableArrayList();
    AdminManageAccountModel adminManageAccountModel = new AdminManageAccountModel();
    private String role;
    protected static int employeeIDFromAccountManageList;
    @FXML
    private ListView<String> adminManageAccountListView;
    @FXML
    private BorderPane borderPaneAdminManageAccount;
    @FXML
    private Label errorMessageListEmpty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            accounts.clear();
            accounts = adminManageAccountModel.getAllAccountsDetail();
            adminManageAccountListView.setItems(accounts);
            errorMessageListEmpty.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBackToMainAdmin(ActionEvent event) {
        Scene scene = borderPaneAdminManageAccount.getScene();
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

    public void switchToDeactivateAccountPrompt(ActionEvent event) {
        String selectedItem = adminManageAccountListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) { // if user not choose an item
            errorMessageListEmpty.setText("Error! You have to choose an Account from the list!");
        } else {
            String spl[] = selectedItem.split("---");
            role = spl[7];
            employeeIDFromAccountManageList = Integer.parseInt(spl[1]);
            if (role.equals("Admin")) {
                errorMessageListEmpty.setText("Error! You can only deactivate other Employee's account not Admin!");
            } else { // if is employee account
                switchToAdminDeactivatePrompt();
            }
        }
    }
    public void switchToDeleteAccountPrompt(ActionEvent event) {
        String selectedItem = adminManageAccountListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) { // if user not choose an item
            errorMessageListEmpty.setText("Error! You have to choose an Account from the list!");
        } else {
            String spl[] = selectedItem.split("---");
            employeeIDFromAccountManageList = Integer.parseInt(spl[1]);
            switchToAdminDeleteAccountPrompt();
        }
    }

    public void switchToAddAccountScene(ActionEvent event) {
        Scene scene = borderPaneAdminManageAccount.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminAddAccount.fxml"));
            primaryStage.setTitle("Hotdesking-Manage Account-Add Account");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminAddAccount.fxml");
        }
    }


    public void switchToAdminDeactivatePrompt() {
        Scene scene = borderPaneAdminManageAccount.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminDeactivateAccountPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Manage Account-Deactivate Account");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminDeactivateAccountPrompt.fxml");
        }
    }

    public void switchToAdminDeleteAccountPrompt(){
        Scene scene = borderPaneAdminManageAccount.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminDeleteAccountPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Manage Account-Delete Account");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminDeleteAccountPrompt.fxml");
        }
    }

    public int getEmployeeIDFromAccountManageList(){
        return employeeIDFromAccountManageList;
    }

}
