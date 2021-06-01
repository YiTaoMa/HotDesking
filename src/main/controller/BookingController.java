package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.BookingModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    final int seatID1 = 1;
    final int seatID2 = 2;
    final int seatID3 = 3;
    final int seatID4 = 4;
    final int seatID5 = 5;
    final int seatID6 = 6;

    BookingModel bookingModel = new BookingModel();
    ChooseDateController chooseDateController = new ChooseDateController();
    LoginController loginController = new LoginController();
    SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();

    protected static Scene bookingScene;
    protected static int seatIDToBook;

    LinkedList<Integer> seatsIdBookedByOther = new LinkedList<>();
    LinkedList<Integer> seatsIdLockedByAdmin = new LinkedList<>();
    LinkedList<Integer> seatsBookedByUserPrevious = new LinkedList<>();
    LinkedList<Integer> seatsBookedByUserPreviousAdmin = new LinkedList<>();

    private int seatIdBookedByUserManage;

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

    @FXML
    private Label greenPrompt;
    @FXML
    private Label redPrompt;
    @FXML
    private Label darkRedOrBlackPrompt;
    @FXML
    private Label orangePrompt;
    @FXML
    private Label welcomeMessage;
    @FXML
    private Label promptClickMessage;
    @FXML
    private Label blackPrompt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // At the start clean all element in the list
        seatsIdBookedByOther.clear();
        seatsIdLockedByAdmin.clear();
        seatsBookedByUserPrevious.clear();
        seatsBookedByUserPreviousAdmin.clear();
        try { // if seat locked down by admin set to orange
            bookingScene = seat1.getScene();
            seatsIdLockedByAdmin = bookingModel.getSeatIdLockedByAdmin();

            if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage function
                welcomeMessage.setText("Welcome to Booking Management Page");
                promptClickMessage.setText("Click the Seat you booked to start your Management!");
                greenPrompt.setText("You can only click the table you Booked!");
                greenPrompt.setStyle("-fx-text-fill: black;");
                seatsIdBookedByOther = bookingModel.getSeatIDBookedByOther(selectBookingToManageEmpController.getDateForManage());// we pass the date user choose in the list
            } else { // else it is normal booking
                redPrompt.setText("Red: Already been booked by You OR others");
                blackPrompt.setText("");
                darkRedOrBlackPrompt.setStyle("-fx-text-fill: darkred;");
                seatsIdBookedByOther = bookingModel.getSeatIDBookedByOther(chooseDateController.getDateChose());
            }

            if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage function
                seatIdBookedByUserManage = selectBookingToManageEmpController.getSeatIDBookedByCurrentUserManage();//get the seat id
                seatsBookedByUserPreviousAdmin = bookingModel.getSeatIdBookedByUserPrevious(loginController.getEmployeeID(), selectBookingToManageEmpController.getDateForManage());
            } else { // normal booking
                seatsBookedByUserPrevious = bookingModel.getSeatIdBookedByUserPrevious(loginController.getEmployeeID(), chooseDateController.getDateChose());
            }

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

            if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if management employee
                if (seatIdBookedByUserManage == 1) {
                    seat1.setStyle("-fx-background-color: black;");
                } else if (seatIdBookedByUserManage == 2) {
                    seat2.setStyle("-fx-background-color: black;");
                } else if (seatIdBookedByUserManage == 3) {
                    seat3.setStyle("-fx-background-color: black;");
                } else if (seatIdBookedByUserManage == 4) {
                    seat4.setStyle("-fx-background-color: black;");
                } else if (seatIdBookedByUserManage == 5) {
                    seat5.setStyle("-fx-background-color: black;");
                } else if (seatIdBookedByUserManage == 6) {
                    seat6.setStyle("-fx-background-color: black;");
                }

                if (!seatsBookedByUserPreviousAdmin.isEmpty()) {
                    for (int i = 0; i < seatsBookedByUserPreviousAdmin.size(); i++) {
                        if (seatsBookedByUserPreviousAdmin.get(i) == 1) {
                            seat1.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPreviousAdmin.get(i) == 2) {
                            seat2.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPreviousAdmin.get(i) == 3) {
                            seat3.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPreviousAdmin.get(i) == 4) {
                            seat4.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPreviousAdmin.get(i) == 5) {
                            seat5.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPreviousAdmin.get(i) == 6) {
                            seat6.setStyle("-fx-background-color: darkred;");
                        }
                    }
                }
            } else { // normal booking
                //if this user booked previously like i booked in 9th then the whitelist record 10th is locked as true, so when
                //i come to book at 10th, the table booked by me in 9th will be dark red color and can not book again in 10th

                if (!seatsBookedByUserPrevious.isEmpty()) {
                    for (int i = 0; i < seatsBookedByUserPrevious.size(); i++) {
                        if (seatsBookedByUserPrevious.get(i) == 1) {
                            seat1.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPrevious.get(i) == 2) {
                            seat2.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPrevious.get(i) == 3) {
                            seat3.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPrevious.get(i) == 4) {
                            seat4.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPrevious.get(i) == 5) {
                            seat5.setStyle("-fx-background-color: darkred;");
                        } else if (seatsBookedByUserPrevious.get(i) == 6) {
                            seat6.setStyle("-fx-background-color: darkred;");
                        }
                    }
                }
            }

            //lock down always will replaced by other, high priority
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickedSeat1(ActionEvent event) { // when user clicked seat 1
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage by employee
            if (seat1.getStyle().equals("-fx-background-color: red;") || seat1.getStyle().equals("-fx-background-color: green;")
                    || seat1.getStyle().equals("-fx-background-color: ORANGE;") || seat1.getStyle().equals("-fx-background-color: darkred;")) {
                showBookManageEmpErrorStage();
            } else if (seat1.getStyle().equals("-fx-background-color: black;")) {
                goToEmpBookManageChooseOptionStage();
            }
        } else { // normal booking
            if (seat1.getStyle().equals("-fx-background-color: red;") || seat1.getStyle().equals("-fx-background-color: darkred;")
                    || seat1.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat1.getStyle().equals("-fx-background-color: green;")) {
                try {
                    // if this user booked already in that selected date like 11th but database says this user already booked a table in
                    //11 th in No 4 seat, so not going to let this user book another seat in 11 th
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChose(), loginController.getEmployeeID())) {
                        showBookAgainInSelectedDateErrorStage();
                    } else {
                        setPassSeatId(seatID1);
                        goToConfirmBookingStage();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clickedSeat2(ActionEvent event) {
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage by employee
            if (seat2.getStyle().equals("-fx-background-color: red;") || seat2.getStyle().equals("-fx-background-color: green;")
                    || seat2.getStyle().equals("-fx-background-color: ORANGE;") || seat2.getStyle().equals("-fx-background-color: darkred;")) {
                showBookManageEmpErrorStage();
            } else if (seat2.getStyle().equals("-fx-background-color: black;")) {
                goToEmpBookManageChooseOptionStage();
            }
        } else {
            if (seat2.getStyle().equals("-fx-background-color: red;") || seat2.getStyle().equals("-fx-background-color: darkred;")
                    || seat2.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat2.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChose(), loginController.getEmployeeID())) {
                        showBookAgainInSelectedDateErrorStage();
                    } else {
                        setPassSeatId(seatID2);
                        goToConfirmBookingStage();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clickedSeat3(ActionEvent event) {
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage by employee
            if (seat3.getStyle().equals("-fx-background-color: red;") || seat3.getStyle().equals("-fx-background-color: green;")
                    || seat3.getStyle().equals("-fx-background-color: ORANGE;") || seat3.getStyle().equals("-fx-background-color: darkred;")) {
                showBookManageEmpErrorStage();
            } else if (seat3.getStyle().equals("-fx-background-color: black;")) {
                goToEmpBookManageChooseOptionStage();
            }
        } else {
            if (seat3.getStyle().equals("-fx-background-color: red;") || seat3.getStyle().equals("-fx-background-color: darkred;")
                    || seat3.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat3.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChose(), loginController.getEmployeeID())) {
                        showBookAgainInSelectedDateErrorStage();
                    } else {
                        setPassSeatId(seatID3);
                        goToConfirmBookingStage();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clickedSeat4(ActionEvent event) {
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage by employee
            if (seat4.getStyle().equals("-fx-background-color: red;") || seat4.getStyle().equals("-fx-background-color: green;")
                    || seat4.getStyle().equals("-fx-background-color: ORANGE;") || seat4.getStyle().equals("-fx-background-color: darkred;")) {
                showBookManageEmpErrorStage();
            } else if (seat4.getStyle().equals("-fx-background-color: black;")) {
                goToEmpBookManageChooseOptionStage();
            }
        } else {
            if (seat4.getStyle().equals("-fx-background-color: red;") || seat4.getStyle().equals("-fx-background-color: darkred;")
                    || seat4.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat4.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChose(), loginController.getEmployeeID())) {
                        showBookAgainInSelectedDateErrorStage();
                    } else {
                        setPassSeatId(seatID4);
                        goToConfirmBookingStage();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clickedSeat5(ActionEvent event) {
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage by employee
            if (seat5.getStyle().equals("-fx-background-color: red;") || seat5.getStyle().equals("-fx-background-color: green;")
                    || seat5.getStyle().equals("-fx-background-color: ORANGE;") || seat5.getStyle().equals("-fx-background-color: darkred;")) {
                showBookManageEmpErrorStage();
            } else if (seat5.getStyle().equals("-fx-background-color: black;")) {
                goToEmpBookManageChooseOptionStage();
            }
        } else {
            if (seat5.getStyle().equals("-fx-background-color: red;") || seat5.getStyle().equals("-fx-background-color: darkred;")
                    || seat5.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat5.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChose(), loginController.getEmployeeID())) {
                        showBookAgainInSelectedDateErrorStage();
                    } else {
                        setPassSeatId(seatID5);
                        goToConfirmBookingStage();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clickedSeat6(ActionEvent event) {
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage by employee
            if (seat6.getStyle().equals("-fx-background-color: red;") || seat6.getStyle().equals("-fx-background-color: green;")
                    || seat6.getStyle().equals("-fx-background-color: ORANGE;") || seat6.getStyle().equals("-fx-background-color: darkred;")) {
                showBookManageEmpErrorStage();
            } else if (seat6.getStyle().equals("-fx-background-color: black;")) {
                goToEmpBookManageChooseOptionStage();
            }
        } else {
            if (seat6.getStyle().equals("-fx-background-color: red;") || seat6.getStyle().equals("-fx-background-color: darkred;")
                    || seat6.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat6.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChose(), loginController.getEmployeeID())) {
                        showBookAgainInSelectedDateErrorStage();
                    } else {
                        setPassSeatId(seatID6);
                        goToConfirmBookingStage();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setPassSeatId(int seatID) {
        seatIDToBook = seatID;
    }

    public int getSeatID() {
        return seatIDToBook;
    }

    public void showBookAgainInSelectedDateErrorStage() {
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

    public void showSeatBookedErrorStage() {
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

    public void goBackToDateSelectOrList(ActionEvent event) {
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) {//if now is manage by user instead of normal booking
            Scene scene = seat1.getScene();
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/selectBookingToManageEmp.fxml"));
                primaryStage.setTitle("Hotdesking-Manage Booking-Employee");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the selectBookingToManageEmp.fxml");
            }
        } else { // normal booking
            Scene scene = seat1.getScene();
            Window window = scene.getWindow();
            Stage primaryStage = (Stage) window;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/chooseDate.fxml"));
                primaryStage.setTitle("Hotdesking-Choose Date");
                primaryStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println("Cannot load the chooseDate.fxml");
            }
        }
    }

    public void showBookManageEmpErrorStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/bookManageEmpError.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Booking Management Error Message");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the bookManageEmpError.fxml");
        }
    }

    public void goToEmpBookManageChooseOptionStage() {
        Scene scene = seat1.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/empBookManageChooseOption.fxml"));
            primaryStage.setTitle("Hotdesking-Booking Management-Choose Options");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the empBookManageChooseOption.fxml");
        }
    }
}
