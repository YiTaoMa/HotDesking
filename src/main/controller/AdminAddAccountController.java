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
import main.model.RegisterModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminAddAccountController implements Initializable {
    RegisterModel registerModel = new RegisterModel();
    ObservableList<String> roles = FXCollections.observableArrayList("Employee", "Admin");
    ObservableList<String> secretQ = FXCollections.observableArrayList("What is your mother's maiden name?", "What was your first pet?", "In what city were you born?", "What was your childhood nickname?", "What was the model of your first car?", "What was your father's middle name?");

    @FXML
    private ChoiceBox choiceRoleBox;
    @FXML
    private ChoiceBox choiceSQBox;
    @FXML
    private BorderPane borderPaneAddAccount;
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
    private Label errorMessageAddAccount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceRoleBox.setValue("Employee");
        choiceRoleBox.setItems(roles);
        choiceSQBox.setValue("What was your first pet?");
        choiceSQBox.setItems(secretQ);
        errorMessageAddAccount.setText("");
    }

    public void goBackToAdminManageAccount(ActionEvent event) {
        Scene scene = borderPaneAddAccount.getScene();
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

    public void addAccount(ActionEvent event) {
        try {
            String IdString = txtEmployeeID.getText();//must od it here
            if (IdString.trim().equals("") || !IdString.matches("^\\d{5}$")) { //id cannot be empty or not int
                errorMessageAddAccount.setText("Error, Employee ID can not be empty and must be a positive whole number with length 5!");
            } else if (!txtFirstName.getText().trim().matches("^[A-Za-z]+$") || !txtLastName.getText().trim().matches("^[A-Za-z]+$")) {
                errorMessageAddAccount.setText("Error, First name and Last name should be composed of 26 English letters！");
            } else {
                int idInt = Integer.parseInt(IdString);//pass the id from integer to int
                if (txtUserName.getText().trim().equals("") || txtPassword.getText().trim().equals("") || txtFirstName.getText().trim().equals("")
                        || txtLastName.getText().trim().equals("") || choiceRoleBox.getValue().toString().trim().equals("") || choiceSQBox.getValue().toString().trim().equals("")
                        || txtAnswerForSecretQ.getText().trim().equals("")) {
                    errorMessageAddAccount.setText("Fields can not be empty! (input can not be a space or spaces)");
                } else if (registerModel.isRegister(txtUserName.getText(), txtPassword.getText(), idInt, txtFirstName.getText(),
                        txtLastName.getText(), choiceRoleBox.getValue().toString(), choiceSQBox.getValue().toString(), txtAnswerForSecretQ.getText())) {
                    switchToMainAdminScene();
                    showAddAccountSuccessStage();
                } else {
                    errorMessageAddAccount.setText("Add account failed, Employee ID or Username already exist!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchToMainAdminScene() {
        Scene scene = borderPaneAddAccount.getScene();
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

    public void showAddAccountSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminAddAccountSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Add Account-Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the adminAddAccountSuccess.fxml");
        }
    }
}
