package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.HomeModel;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    HomeModel homeModel = new HomeModel();
    HashMap<Integer, String> bookingsOutOfRange = new HashMap<>();
    LinkedList<Integer> bookingsOutOfRangeEmpId = new LinkedList<>();
    LinkedList<String> bookingsOutOfRangeDate = new LinkedList<>();
    @FXML
    private Button loginID;
    @FXML
    private Button registerID;

    /**
     * Every time this scene is shown, which at very beginning, time validation framework here
     * will automate delete bookings and their whitelist if any tomorrow booking has not been confirmed by midnight before tomorrow
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // clear everything at the start
        bookingsOutOfRange.clear();
        bookingsOutOfRangeEmpId.clear();
        bookingsOutOfRangeDate.clear();
        try {
            bookingsOutOfRange = homeModel.getBookingsOutOfRanges(); // get all booking out of ranges (need to delete)
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!bookingsOutOfRange.isEmpty()) { // if we have bookings out of range
            for (Map.Entry<Integer, String> entry : bookingsOutOfRange.entrySet()) { // for each out of ranges booking
                homeModel.assignOutOfRangeEmpIDDateToCollection(entry.getKey()); //assign their emp ID and date to the model
                //2 linked list used to delete their corresponding whitelist
            }
            // after loop, after all assigned, we assign the 2 linked list in model to the controller to used it
            bookingsOutOfRangeEmpId = homeModel.getBookingsOutOfRangeEmpIdCollection();
            bookingsOutOfRangeDate = homeModel.getBookingsOutOfRangeDateCollection();

            for (Map.Entry<Integer, String> entry : bookingsOutOfRange.entrySet()) { // for each out of ranges booking
                homeModel.deleteOutOfRangeBookings(entry.getKey());// we delete them through their unique booking number
            }
            for (int i = 0; i < bookingsOutOfRangeEmpId.size(); i++) { // as these 2 collection size should be same, so use 1 of them will be fine
                homeModel.deleteOutOfRangeWhitelistRecord(bookingsOutOfRangeEmpId.get(i), bookingsOutOfRangeDate.get(i));
                // delete their whitelist record by pass their emp ID and date, date plus 1 day in the model
            }
            homeModel.clearEmpIdDateCollection();// clear 2 linked list in the model method after we done the job.
        }
    }

    public void homeLogin(ActionEvent event) {
        Scene scene = loginID.getScene();
        // from the scene, we try to access the primary stage
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        // load another scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
            primaryStage.setTitle("Hotdesking-Login");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the login scene");
        }
    }

    //Need to do this action set onclick in scene builder
    public void homeRegister(ActionEvent event) {
        Scene scene = registerID.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/register.fxml"));
            primaryStage.setTitle("Hotdesking-register");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the register scene");
        }
    }
}
