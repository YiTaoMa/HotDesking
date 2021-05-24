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
import main.model.AdminSelectBookingToManageModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminSelectBookingToManageController implements Initializable {
    ObservableList<String> items = FXCollections.observableArrayList();
    protected static boolean isBookingManagementAdmin;
    protected static int seatIDBookedByCurrentUserAdminManage;
    protected static String dateForAdminManage;
    protected static boolean isCurrentBookingConfirmed;
    protected static int selectedEmpIdFromList;

    AdminSelectBookingToManageModel adminSelectBookingToManageModel = new AdminSelectBookingToManageModel();
    SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();
    @FXML
    private ListView<String> adminListView;
    @FXML
    private Label errorMessageListEmpty;
    @FXML
    private BorderPane borderPaneAdminSelectBooking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            items = adminSelectBookingToManageModel.getAllBookingDetail();
            adminListView.setItems(items);
            errorMessageListEmpty.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void goToBookingAsAdmin(ActionEvent event) {
        String selectedItem = adminListView.getSelectionModel().getSelectedItem(); // get the current select4ed item from list
        if (selectedItem == null) { // if user not choose an item
            errorMessageListEmpty.setText("Error, you must choose an item!");
        }
        else{
            String spl[] = selectedItem.split("---");
            seatIDBookedByCurrentUserAdminManage =  Integer.parseInt(spl[7]);
            //System.out.println(seatIDBookedByCurrentUserAdminManage);

            dateForAdminManage =  spl[5];

            isCurrentBookingConfirmed =Boolean.parseBoolean(spl[11]);

            selectedEmpIdFromList = Integer.parseInt(spl[3]);

            //System.out.println(isCurrentBookingConfirmed);

           // System.out.println( dateForAdminManage);
            //System.out.println(seatIDBookedByCurrentUserAdminManage);
            //selectBookingToManageEmpController.setIsBookingManagementEmp(false);// set employee booking manage to false

            // no need for this as we can create a new page for it not combine with employee
            isBookingManagementAdmin=true;
            Scene scene = borderPaneAdminSelectBooking.getScene();
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;
            try {
                // change this create a new fxml for admin booking
                Parent root = FXMLLoader.load(getClass().getResource("../ui/adminManageBooking.fxml"));
                primaryStage.setTitle("Hotdesking-Admin-Booking Management");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the adminManageBooking.fxml");
            }
        }
    }
    public void goBackToAdminMain(ActionEvent event) {
        Scene scene = borderPaneAdminSelectBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/mainAdmin.fxml"));
            primaryStage.setTitle("Hotdesking-Main-Admin");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the mainAdmin.fxml");
        }

    }
    public boolean getIsBookingManagementAdmin() {
        return isBookingManagementAdmin;
    }
    public void setIsBookingManagementAdmin(boolean adminBookingManage) {
       isBookingManagementAdmin = adminBookingManage;
    }
    public int getSeatIDBookedByCurrentUserAdminManage() {
        return seatIDBookedByCurrentUserAdminManage;
    }
    public String getDateForAdminManage(){
        return dateForAdminManage;
    }

    public boolean getIsCurrentBookingConfirmed(){
        return isCurrentBookingConfirmed;
    }

    public int getSelectedEmpIdFromList() {
        return selectedEmpIdFromList;
    }

}
