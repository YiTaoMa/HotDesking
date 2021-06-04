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

public class RegisterController implements Initializable {
    public RegisterModel registerModel = new RegisterModel();
    //values in 2 choice boxes
    ObservableList<String> roles = FXCollections.observableArrayList("Employee");
    ObservableList<String> secretQ = FXCollections.observableArrayList("What is your mother's maiden name?", "What was your first pet?", "In what city were you born?", "What was your childhood nickname?", "What was the model of your first car?", "What was your father's middle name?");
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
    private BorderPane borderpaneRegister;
    @FXML
    private ChoiceBox choiceRoleBox;
    @FXML
    private ChoiceBox choiceSQBox;
    @FXML
    private Label errorMessageRegister;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set choice box value at the start.
        choiceRoleBox.setValue("Employee");
        choiceRoleBox.setItems(roles);
        choiceSQBox.setValue("What was your first pet?");
        choiceSQBox.setItems(secretQ);
        errorMessageRegister.setText("");
    }

    public void register(ActionEvent event) {
        // call model
        try {
            String IdString = txtEmployeeID.getText();//must do it here
            if (IdString.trim().equals("") || !IdString.matches("^\\d{5}$")) { //id cannot be empty or not int
                errorMessageRegister.setText("Error, Employee ID can not be empty and must be a positive whole number with length 5!");
            } else if (!txtFirstName.getText().trim().matches("^[A-Za-z]+$") || !txtLastName.getText().trim().matches("^[A-Za-z]+$")) {
                errorMessageRegister.setText("Error, First name and Last name should be composed of 26 English letters！");
            } else {
                int idInt = Integer.parseInt(IdString);//pass the id from integer to int
                if (txtUserName.getText().trim().equals("") || txtPassword.getText().trim().equals("") || txtFirstName.getText().trim().equals("")
                        || txtLastName.getText().trim().equals("") || choiceRoleBox.getValue().toString().trim().equals("") || choiceSQBox.getValue().toString().trim().equals("")
                        || txtAnswerForSecretQ.getText().trim().equals("")) {
                    errorMessageRegister.setText("Fields can not be empty! (input can not be a space or spaces)");
                } else if (registerModel.isRegister(txtUserName.getText(), txtPassword.getText(), idInt, txtFirstName.getText(),
                        txtLastName.getText(), choiceRoleBox.getValue().toString(), choiceSQBox.getValue().toString(), txtAnswerForSecretQ.getText())) {
                    // If everything is good we register this employee and switch scene to the login
                    try {
                        Scene scene = borderpaneRegister.getScene();
                        Window window = scene.getWindow();
                        Stage primaryStage = (Stage) window;
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
                            primaryStage.setTitle("Hotdesking-Login");
                            primaryStage.setScene(new Scene(root));
                        } catch (IOException e) {
                            System.out.println("Cannot load the login.fxml");
                        }
                        showRegisterSuccessStage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMessageRegister.setText("Register failed, Employee ID or username already exist!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent event) { // Go back to home page
        Scene scene = borderpaneRegister.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/home.fxml"));
            primaryStage.setTitle("Hotdesking-Home");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the home scene");
        }
    }

    public void showRegisterSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/registerSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Register Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the registerSuccess.fxml");
        }
    }
}
