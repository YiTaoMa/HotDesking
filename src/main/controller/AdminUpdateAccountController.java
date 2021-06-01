package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminDeleteAccountPromptModel;
import main.model.AdminUpdateAccountModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminUpdateAccountController implements Initializable {
    AdminDeleteAccountPromptModel adminDeleteAccountPromptModel = new AdminDeleteAccountPromptModel();
    AdminUpdateAccountModel adminUpdateAccountModel = new AdminUpdateAccountModel();
    AdminManageAccountController adminManageAccountController = new AdminManageAccountController();
    ObservableList<String> roles = FXCollections.observableArrayList("Employee", "Admin");
    ObservableList<String> secretQ = FXCollections.observableArrayList("What is your mother's maiden name?", "What was your first pet?", "In what city were you born?", "What was your childhood nickname?", "What was the model of your first car?", "What was your father's middle name?");
    @FXML
    private ChoiceBox choiceRoleBox;
    @FXML
    private ChoiceBox choiceSQBox;
    @FXML
    private BorderPane borderPaneUpdateAccount;
    @FXML
    private TextField txtEmployeeID;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtAnswerForSecretQ;
    @FXML
    private Label errorMessageUpdateAccount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // here we need to set value to what we have that user chose from the list.
        txtEmployeeID.setText(String.valueOf(adminManageAccountController.getEmployeeIDFromAccountManageList()));// set text to admin chose from the list
        txtFirstName.setText(adminManageAccountController.getEmployeeFNFromAccountManageList());
        txtLastName.setText(adminManageAccountController.getEmployeeLNFromAccountManageList());
        choiceRoleBox.setValue(adminManageAccountController.getEmployeeRoleFromAccountManageList());
        choiceRoleBox.setItems(roles);
        txtUserName.setText(adminManageAccountController.getEmployeeUNFromAccountManageList());
        txtPassword.setText(adminManageAccountController.getEmployeePWFromAccountManageList());
        choiceSQBox.setValue(adminManageAccountController.getEmployeeSQFromAccountManageList());
        choiceSQBox.setItems(secretQ);
        txtAnswerForSecretQ.setText(adminManageAccountController.getEmployeeASQFromAccountManageList());
        errorMessageUpdateAccount.setText("");
    }

    public void switchToAdminManageAccount(ActionEvent event) {
        Scene scene = borderPaneUpdateAccount.getScene();
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

    public void updateAccount(ActionEvent event) {

        String IdString = txtEmployeeID.getText();//must do it here
        if (IdString.trim().equals("") || !IdString.matches("^\\d{5}$")) { //id cannot be empty or not int
            errorMessageUpdateAccount.setText("Error, Employee ID can not be empty and must be a positive whole number with length 5!");
        } else if (!txtFirstName.getText().trim().matches("^[A-Za-z]+$") || !txtLastName.getText().trim().matches("^[A-Za-z]+$")) {
            errorMessageUpdateAccount.setText("Error, First name and Last name should be composed of 26 English letters！");
        } else {
            int idInt = Integer.parseInt(IdString);//pass the id from integer to int
            if (txtUserName.getText().trim().equals("") || txtPassword.getText().trim().equals("") || txtFirstName.getText().trim().equals("")
                    || txtLastName.getText().trim().equals("") || choiceRoleBox.getValue().toString().trim().equals("") || choiceSQBox.getValue().toString().trim().equals("")
                    || txtAnswerForSecretQ.getText().trim().equals("")) {
                errorMessageUpdateAccount.setText("Fields can not be empty! (input can not be a space or spaces)");
            } else if (idInt == adminManageAccountController.getEmployeeIDFromAccountManageList() && txtFirstName.getText().equals(adminManageAccountController.getEmployeeFNFromAccountManageList())
                    && txtLastName.getText().equals(adminManageAccountController.getEmployeeLNFromAccountManageList()) && choiceRoleBox.getValue().toString().equals(adminManageAccountController.getEmployeeRoleFromAccountManageList())
                    && txtUserName.getText().equals(adminManageAccountController.getEmployeeUNFromAccountManageList()) && txtPassword.getText().equals(adminManageAccountController.getEmployeePWFromAccountManageList())
                    && choiceSQBox.getValue().toString().equals(adminManageAccountController.getEmployeeSQFromAccountManageList()) && txtAnswerForSecretQ.getText().equals(adminManageAccountController.getEmployeeASQFromAccountManageList())) {
                // if admin not change anything
                errorMessageUpdateAccount.setText("You haven't change anything, Can not update!");
            }
            // if the id input is already registered (exclude current selected account emp id!). We can not let the admin to update id to that one
            // if it is fine then we check user name
            else if (adminUpdateAccountModel.isIdUnRegistered(idInt, adminManageAccountController.getEmployeeIDFromAccountManageList())) {
                // if user name is already registered (exclude current selected account username). We can not let the admin to update this
                // if username exclude this current account is not registered in the database, update the account
                if (adminUpdateAccountModel.isUsernameUnRegistered(adminManageAccountController.getEmployeeUNFromAccountManageList(), txtUserName.getText())) {
                    if (adminUpdateAccountModel.updateAccount(idInt, adminManageAccountController.getEmployeeIDFromAccountManageList(),
                            txtFirstName.getText(), txtLastName.getText(), choiceRoleBox.getValue().toString(), txtUserName.getText(),
                            txtPassword.getText(), choiceSQBox.getValue().toString(), txtAnswerForSecretQ.getText())) {
                        // if this employee old id from the list have bookings we need update employee id in the booking table as well,
                        // also whitelist
                        if (adminDeleteAccountPromptModel.isSelectedEmpHaveBookings(adminManageAccountController.getEmployeeIDFromAccountManageList())
                                && idInt != adminManageAccountController.getEmployeeIDFromAccountManageList()) {
                            // if this employee have bookings and admin changed this employee's emp id then
                            // we need to update booking's emp id and whitelist emp id. If the emp id not changed
                            // we don't need to change bookings and whitelist emp id
                            // update employee id in booking table
                            if (adminUpdateAccountModel.updateBookingEmpId(adminManageAccountController.getEmployeeIDFromAccountManageList(), idInt)) {
                                // update employee id in the whitelist
                                if (adminUpdateAccountModel.updateWhitelistEmpId(adminManageAccountController.getEmployeeIDFromAccountManageList(), idInt)) {
                                    switchToMainAdminScene();
                                    showUpdateAccountSuccessStage();
                                }
                            }
                        } else { // else this employee don't have any bookings then just delete account only
                            switchToMainAdminScene();
                            showUpdateAccountSuccessStage();
                        }
                    }
                } else {
                    errorMessageUpdateAccount.setText("Update failed, Username already exist!");
                }
            } else {
                errorMessageUpdateAccount.setText("Update failed, Employee ID already exist!");
            }
        }
    }

    public void switchToMainAdminScene() {
        Scene scene = borderPaneUpdateAccount.getScene();
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

    public void showUpdateAccountSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminUpdateAccountSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Update Account-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminUpdateAccountSuccess.fxml");
        }
    }
}
