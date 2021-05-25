package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.CancelBookingConfirmModel;
import main.model.MainAdminModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

public class MainAdminController implements Initializable {
    MainAdminModel mainAdminModel = new MainAdminModel();
    //HashMap<Integer, String> datesAndNumberFromBookingNotConfirmed = new HashMap<>();
    HashMap<Integer, String> bookingsOutOfRange = new HashMap<>();

    LinkedList<Integer> bookingsOutOfRangeEmpId = new LinkedList<>();
    LinkedList<String> bookingsOutOfRangeDate = new LinkedList<>();
    @FXML
    private BorderPane borderPaneMainAdmin;

    /**
     * Every time this scene is shown, which right before admin select bookings to manage, time validation framework here
     * will automate delete bookings and their whitelist if any tomorrow booking has not been confirmed by midnight before tomorrow
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // clear everything at the start
        bookingsOutOfRange.clear();
        bookingsOutOfRangeEmpId.clear();
        bookingsOutOfRangeDate.clear();

        try {
            bookingsOutOfRange = mainAdminModel.getBookingsOutOfRanges(); // get all booking out of ranges (need to delete)
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Entry<Integer, String> entry : bookingsOutOfRange.entrySet()) { // for each out of ranges booking
            mainAdminModel.assignOutOfRangeEmpIDDateToCollection(entry.getKey()); //assign their emp ID and date to the model
            //2 linked list used to delete their corresponding whitelist
        }
        // after loop, after all assigned, we assign the 2 linked list in model to the controller to used it
        bookingsOutOfRangeEmpId = mainAdminModel.getBookingsOutOfRangeEmpIdCollection();
        bookingsOutOfRangeDate = mainAdminModel.getBookingsOutOfRangeDateCollection();

        for (Entry<Integer, String> entry : bookingsOutOfRange.entrySet()) { // for each out of ranges booking
            //System.out.println(entry.getKey());
            mainAdminModel.deleteOutOfRangeBookings(entry.getKey());// we delete them through their unique booking number
        }

        for (int i = 0; i < bookingsOutOfRangeEmpId.size(); i++) { // as these 2 collection size should be same, so use 1 of them will be fine
            //System.out.println(bookingsOutOfRangeEmpId.get(i) + "---"+bookingsOutOfRangeDate.get(i));
            mainAdminModel.deleteOutOfRangeWhitelistRecord(bookingsOutOfRangeEmpId.get(i), bookingsOutOfRangeDate.get(i));
            // delete their whitelist record by pass their emp ID and date, date plus 1 day in the model
        }
        mainAdminModel.clearEmpIdDateCollection();// clear 2 linked list in the model method after we done the job.

    }

    public void switchToManageBooking(ActionEvent event) {
        Scene scene = borderPaneMainAdmin.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminSelectBookingToManage.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Select Booking Manage");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminSelectBookingToManage.fxml");
        }
    }

    //public void testHashMap(){
    //    System.out.println("Booking not confirmed");
    //    System.out.println(mainAdminModel.getBookingEmpIDAndDatesNotConfirmed());
    //    System.out.println("Bookings need to delete");
    //    try {
    //        System.out.println(mainAdminModel.getBookingsOutOfRanges());
    //    }
    //    catch (ParseException e){
    //        e.printStackTrace();
    //    }
    //}
}
