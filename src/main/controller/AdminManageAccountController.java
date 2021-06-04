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
    protected static int employeeIDFromAccountManageList;
    protected static String employeeFNFromAccountManageList;
    protected static String employeeLNFromAccountManageList;
    protected static String employeeRoleFromAccountManageList;
    protected static String employeeUNFromAccountManageList;
    protected static String employeePWFromAccountManageList;
    protected static String employeeSQFromAccountManageList;
    protected static String employeeASQFromAccountManageList;
    protected static boolean employeeIsDeactivatedFromAccountManageList;
    @FXML
    private ListView<String> adminManageAccountListView;
    @FXML
    private BorderPane borderPaneAdminManageAccount;
    @FXML
    private Label errorMessageList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            accounts.clear();
            accounts = adminManageAccountModel.getAllAccountsDetail();
            adminManageAccountListView.setItems(accounts);
            errorMessageList.setText("");
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
            errorMessageList.setText("Error! You have to choose an Account from the list!");
        } else {
            String spl[] = selectedItem.split("---");
            employeeRoleFromAccountManageList = spl[7];
            employeeIDFromAccountManageList = Integer.parseInt(spl[1]);
            employeeIsDeactivatedFromAccountManageList = Boolean.parseBoolean(spl[17]);

            if (employeeRoleFromAccountManageList.equals("Admin")) {
                errorMessageList.setText("Error! You can only deactivate other Employee's account not Admin!");
            } else if (employeeIsDeactivatedFromAccountManageList) {
                errorMessageList.setText("This account already been deactivated! Can not deactivated again!");
            } else {
                switchToAdminDeactivatePrompt();
            }
        }
    }

    public void switchToUnDeactivateAccountPrompt(ActionEvent event) {
        String selectedItem = adminManageAccountListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) { // if user not choose an item
            errorMessageList.setText("Error! You have to choose an Account from the list!");
        } else {
            String spl[] = selectedItem.split("---");
            employeeRoleFromAccountManageList = spl[7];
            employeeIDFromAccountManageList = Integer.parseInt(spl[1]);
            employeeIsDeactivatedFromAccountManageList = Boolean.parseBoolean(spl[17]);
            if (employeeRoleFromAccountManageList.equals("Admin")) {
                errorMessageList.setText("Error! You can only Un Deactivate other Employee's account not Admin!");
            } else if (!employeeIsDeactivatedFromAccountManageList) {
                errorMessageList.setText("This account is not deactivated! Can not Un-deactivated this account!");
            } else {
                switchToAdminUnDeactivatePrompt();
            }
        }
    }


    public void switchToDeleteAccountPrompt(ActionEvent event) {
        String selectedItem = adminManageAccountListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) { // if user not choose an item
            errorMessageList.setText("Error! You have to choose an Account from the list!");
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

    public void switchToUpdateAccountScene(ActionEvent event) {
        String selectedItem = adminManageAccountListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) { // if user not choose an item
            errorMessageList.setText("Error! You have to choose an Account from the list!");
        } else {
            String spl[] = selectedItem.split("---");
            employeeIDFromAccountManageList = Integer.parseInt(spl[1]);
            employeeFNFromAccountManageList = spl[3];
            employeeLNFromAccountManageList = spl[5];
            employeeRoleFromAccountManageList = spl[7];
            employeeUNFromAccountManageList = spl[9];
            employeePWFromAccountManageList = spl[11];
            employeeSQFromAccountManageList = spl[13];
            employeeASQFromAccountManageList = spl[15];
            switchToAdminUpdateAccount();
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

    public void switchToAdminUnDeactivatePrompt() {
        Scene scene = borderPaneAdminManageAccount.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminUnDeactivateAccountPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Manage Account-Un Deactivate Account");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminUnDeactivateAccountPrompt.fxml");
        }
    }

    public void switchToAdminDeleteAccountPrompt() {
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

    public void switchToAdminUpdateAccount() {
        Scene scene = borderPaneAdminManageAccount.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminUpdateAccount.fxml"));
            primaryStage.setTitle("Hotdesking-Manage Account-Update Account");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminUpdateAccount.fxml");
        }
    }

    public int getEmployeeIDFromAccountManageList() {
        return employeeIDFromAccountManageList;
    }

    public String getEmployeeFNFromAccountManageList() {
        return employeeFNFromAccountManageList;
    }

    public String getEmployeeLNFromAccountManageList() {
        return employeeLNFromAccountManageList;
    }

    public String getEmployeeRoleFromAccountManageList() {
        return employeeRoleFromAccountManageList;
    }

    public String getEmployeeUNFromAccountManageList() {
        return employeeUNFromAccountManageList;
    }

    public String getEmployeePWFromAccountManageList() {
        return employeePWFromAccountManageList;
    }

    public String getEmployeeSQFromAccountManageList() {
        return employeeSQFromAccountManageList;
    }

    public String getEmployeeASQFromAccountManageList() {
        return employeeASQFromAccountManageList;
    }
}
