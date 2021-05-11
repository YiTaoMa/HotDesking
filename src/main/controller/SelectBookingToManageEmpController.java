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
import main.model.SelectBookingToManageEmpModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SelectBookingToManageEmpController implements Initializable {
    @FXML
    private ListView<String> list;

    @FXML
    private BorderPane borderPaneSelectBooking;
    @FXML
    private Label errorMessageListEmpty;

    protected static boolean isBookingManagementEmp;
    protected static String dateForManage;
    protected static int seatIDBookedByCurrentUserManage;
    ObservableList<String> items = FXCollections.observableArrayList();
    SelectBookingToManageEmpModel selectBookingToManageEmpModel = new SelectBookingToManageEmpModel();
    LoginController loginController = new LoginController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            items = selectBookingToManageEmpModel.getEmployeeBookingDetail(loginController.getEmployeeID());
            list.setItems(items);
            errorMessageListEmpty.setText("");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        //select employee_id,date,seat_id from Booking where employee_id=23443
    }
    public void goToBookingManageEmp(ActionEvent event){
        String selectedItem = list.getSelectionModel().getSelectedItem(); // get the current select4ed item from list
        //System.out.println(selectedItem);
        if (selectedItem==null){ // if user not choose an item
            errorMessageListEmpty.setText("Error, you must choose an item!");
        }
        else{
            String spl[]=selectedItem.split("---");
            dateForManage=spl[3];
            seatIDBookedByCurrentUserManage=Integer.parseInt(spl[5]);

            // System.out.println(dateForManage);

//this action is go to the booking page as to management so set it to true
            isBookingManagementEmp =true;
            Scene scene =  borderPaneSelectBooking.getScene();
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/booking.fxml"));
                primaryStage.setTitle("Hotdesking-Booking Management");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the booking.fxml");
            }
        }
    }
    public void goBackToMain(ActionEvent event){
        Scene scene =  borderPaneSelectBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/main.fxml"));
            primaryStage.setTitle("Hotdesking-Main");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the main.fxml");
        }
    }

    public boolean getIsBookingManagementEmp(){
        return isBookingManagementEmp;
    }
    public String getDateForManage(){
        return dateForManage;
    }
    public int getSeatIDBookedByCurrentUserManage(){
        return seatIDBookedByCurrentUserManage;
    }
    public void setIsBookingManagementEmp(boolean normalBooking){ // if user is normal booking set it to false
        isBookingManagementEmp = normalBooking;
    }
}