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
import main.model.BookingModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    public BookingModel bookingModel = new BookingModel();
    public ChooseDateController chooseDateController = new ChooseDateController();
    public LoginController loginController = new LoginController();
    public SeatBookedErrorController seatBookedErrorController = new SeatBookedErrorController();

    protected static Scene bookingScene;


    LinkedList<Integer> seatsIdBookedByOther = new LinkedList<>();
    LinkedList<Integer> seatsIdLockedByAdmin = new LinkedList<>();
    LinkedList<Integer> seatsBookedByUserPrevious = new LinkedList<>();
    protected static int seatIDToBook;

    final int seatID1 = 1;
    final int seatID2 = 2;
    final int seatID3 = 3;
    final int seatID4 = 4;
    final int seatID5 = 5;
    final int seatID6 = 6;
    //int seatIdLocked;
    /**
     * Button not import class awt need import class scene builder
     */
    @FXML
    private Button seat1;
    @FXML
    private Button seat2;
    @FXML
    private Button seat3;
    @FXML
    private Button seat4;
    @FXML
    private Button seat5;
    @FXML
    private Button seat6;
//select date date as parameter pass to model
    //select seat_id from Booking where date=? and is_booked=true;
    //select seat_id from Booking where date="2021-05-10" and is_booked=true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try { // if seat locked down by admin set to orange
            bookingScene = seat1.getScene();

            seatsIdLockedByAdmin = bookingModel.getSeatIdLockedByAdmin();
            //System.out.println(chooseDateController.getDateChosed() + "in booking controller");
            seatsIdBookedByOther = bookingModel.getSeatIDBookedByOther(chooseDateController.getDateChosed());
            //System.out.println(seatsIdBookedByOther);
            seatsBookedByUserPrevious = bookingModel.getSeatIdBookedByUserPrevious(loginController.getEmployeeID(), chooseDateController.getDateChosed());

            if (!seatsIdBookedByOther.isEmpty()) {
                for (int i = 0; i < seatsIdBookedByOther.size(); i++) {
                    if (seatsIdBookedByOther.get(i) == 1) {
                        seat1.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) == 2) {
                        seat2.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) == 3) {
                        seat3.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) == 4) {
                        seat4.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) == 5) {
                        seat5.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) == 6) {
                        seat6.setStyle("-fx-background-color: red;");
                    }
                }
            }
            //else{
            //    System.out.println("error in seatIdBookedByOther");
            //}
            //if this user booked previously like i booked in 9th then the whitelist record 10th is locked as true, so when
            //i come to book at 10th, the table booked by me in 9th will be dark red color and can not book again in 10th
            if (!seatsBookedByUserPrevious.isEmpty()) {
                for (int i = 0; i < seatsBookedByUserPrevious.size(); i++) {
                    if (seatsBookedByUserPrevious.get(i) == 1) {
                        seat1.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) == 2) {
                        seat2.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) == 3) {
                        seat3.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) == 4) {
                        seat4.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) == 5) {
                        seat5.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) == 6) {
                        seat6.setStyle("-fx-background-color: darkred;");
                    }
                }
            }
            //else {
            //    System.out.println("error in seatIdBookedByUserPrevious");
            //}
            //lock down always will replaced by other high pior
            if (!seatsIdLockedByAdmin.isEmpty()) {
                for (int i = 0; i < seatsIdLockedByAdmin.size(); i++) {
                    if (seatsIdLockedByAdmin.get(i) == 1) {
                        seat1.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) == 2) {
                        seat2.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) == 3) {
                        seat3.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) == 4) {
                        seat4.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) == 5) {
                        seat5.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) == 6) {
                        seat6.setStyle("-fx-background-color: ORANGE;");
                    }
                }
            }
            //else: if no for example no booking in yestayday then we don't do anything
            //else{
            //    System.out.println("error happened in seatIdLocked ");
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickedSeat1(ActionEvent event) {
        if (seat1.getStyle().equals("-fx-background-color: red;") || seat1.getStyle().equals("-fx-background-color: darkred;")
                || seat1.getStyle().equals("-fx-background-color: ORANGE;")) {
            goToSeatBookedErrorStage();
        } else if (seat1.getStyle().equals("-fx-background-color: green;")) {
            try {
                // if this user booked already in that selected date like 11th but database says this user already booked a table in
                //11 th in No 4 seat, so not going to let this user book another seat in 11 th
                /**now finish at seat 2 green */
                if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                    goToBookAgainInSelecterDateErrorStage();
                } else {
                    setPassSeatId(seatID1);
                    goToConfirmBookingStage();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickedSeat2(ActionEvent event) {
        if (seat2.getStyle().equals("-fx-background-color: red;") || seat2.getStyle().equals("-fx-background-color: darkred;")
                || seat2.getStyle().equals("-fx-background-color: ORANGE;")) {
            goToSeatBookedErrorStage();
        } else if (seat2.getStyle().equals("-fx-background-color: green;")) {
            try {
                if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                    goToBookAgainInSelecterDateErrorStage();
                } else {
                    setPassSeatId(seatID2);
                    goToConfirmBookingStage();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void clickedSeat3(ActionEvent event) {
        if (seat3.getStyle().equals("-fx-background-color: red;") || seat3.getStyle().equals("-fx-background-color: darkred;")
                || seat3.getStyle().equals("-fx-background-color: ORANGE;")) {
            goToSeatBookedErrorStage();
        } else if (seat3.getStyle().equals("-fx-background-color: green;")) {
            try {
                if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                    goToBookAgainInSelecterDateErrorStage();
                } else {
                    setPassSeatId(seatID3);
                    goToConfirmBookingStage();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickedSeat4(ActionEvent event) {
        if (seat4.getStyle().equals("-fx-background-color: red;") || seat4.getStyle().equals("-fx-background-color: darkred;")
                || seat4.getStyle().equals("-fx-background-color: ORANGE;")) {
            goToSeatBookedErrorStage();
        } else if (seat4.getStyle().equals("-fx-background-color: green;")) {
            try {
                if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                    goToBookAgainInSelecterDateErrorStage();
                } else {
                    setPassSeatId(seatID4);
                    goToConfirmBookingStage();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickedSeat5(ActionEvent event) {
        if (seat5.getStyle().equals("-fx-background-color: red;") || seat5.getStyle().equals("-fx-background-color: darkred;")
                || seat5.getStyle().equals("-fx-background-color: ORANGE;")) {
            goToSeatBookedErrorStage();
        } else if (seat5.getStyle().equals("-fx-background-color: green;")) {
            try {
                if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                    goToBookAgainInSelecterDateErrorStage();
                } else {
                    setPassSeatId(seatID5);
                    goToConfirmBookingStage();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickedSeat6(ActionEvent event) {
        if (seat6.getStyle().equals("-fx-background-color: red;") || seat6.getStyle().equals("-fx-background-color: darkred;")
                || seat6.getStyle().equals("-fx-background-color: ORANGE;")) {
            goToSeatBookedErrorStage();
        } else if (seat6.getStyle().equals("-fx-background-color: green;")) {
            try {
                if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                    goToBookAgainInSelecterDateErrorStage();
                } else {
                    setPassSeatId(seatID6);
                    goToConfirmBookingStage();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPassSeatId(int seatID) {
        seatIDToBook = seatID;
    }

    public int getSeatID() {
        return seatIDToBook;
    }

    public void goToBookAgainInSelecterDateErrorStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/bookAgainInSelectedDateError.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Error-Can not book multiple seats in a day");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the bookAgainInSelectedDateError.fxml");
        }
    }

    public void goToSeatBookedErrorStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/seatBookedError.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Booking Error Message");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the seatBookedError.fxml");
        }
    }

    public void goToConfirmBookingStage() {
        Scene scene = seat1.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/confirmBooking.fxml"));
            primaryStage.setTitle("Hotdesking-Confirm Booking");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the Confirm Booking scene");
        }
    }
    public Scene getBookingScene(){
        return bookingScene;
    }

}
