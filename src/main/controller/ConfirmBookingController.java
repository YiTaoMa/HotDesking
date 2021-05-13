package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.ConfirmBookingModel;

import java.io.IOException;
import java.sql.SQLException;

public class ConfirmBookingController {
    ConfirmBookingModel confirmBookingModel = new ConfirmBookingModel();
    LoginController loginController = new LoginController();
    ChooseDateController chooseDateController = new ChooseDateController();
    BookingController bookingController = new BookingController();
    @FXML
    private BorderPane borderPaneConfirmBooking;

    public void confirmBooking(ActionEvent event) {
        //if confirmed, we insert into Booking table don't need to consider booking number auto increment
        //so just insert into Booking with employee id, date, seat_id,is_booked (default true),has_confirmed(default false),
        // is_checked in(default false)
        try {
            // one booking only can book 1 seat!!!!!
            //as long as this employee appear in the booking table in that day, it means he already booked a
            //table in that date, so not going to let he book another.
            if (confirmBookingModel.insertBookingRecord(loginController.getEmployeeID(), chooseDateController.getDateChose(), bookingController.getSeatID())) {
                // if insert into Booking table success insert into whitelist also
                if (confirmBookingModel.insertToWhitelist(loginController.getEmployeeID(), bookingController.getSeatID(), chooseDateController.getDateChose())) {
                    //if insert into whitelist is true which is success then go to page
                    switchSceneToMain();
                    showBookingSuccessStage();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goBackToBookingScene(ActionEvent event) {
        switchSceneToBookingScene();
    }

    public void showBookingSuccessStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/bookingSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Booking Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the bookingSuccess.fxml");
        }
    }

    public void switchSceneToMain() {
        Scene scene = borderPaneConfirmBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/main.fxml"));
            primaryStage.setTitle("Hotdesking-Main");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the Main scene");
        }
    }

    public void switchSceneToBookingScene() {
        Scene scene = borderPaneConfirmBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/booking.fxml"));
            primaryStage.setTitle("Hotdesking-Booking");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the booking.fxml");
        }
    }
}
