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
     BookingModel bookingModel = new BookingModel();
     ChooseDateController chooseDateController = new ChooseDateController();
     LoginController loginController = new LoginController();
    SeatBookedErrorController seatBookedErrorController = new SeatBookedErrorController();
    SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();

    protected static Scene bookingScene;


    LinkedList<Integer> seatsIdBookedByOther = new LinkedList<>();
    LinkedList<Integer> seatsIdLockedByAdmin = new LinkedList<>();
    LinkedList<Integer> seatsBookedByUserPrevious = new LinkedList<>();
    protected static int seatIDToBook;
    private int seatIdBookedByUserManage;

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

    @FXML
    private Label greenPrompt;
    @FXML
    private Label redPrompt;
    @FXML
    private Label darkRedOrBlackPrompt;
    @FXML
    private Label orangePrompt;

//select date date as parameter pass to model
    //select seat_id from Booking where date=? and is_booked=true;
    //select seat_id from Booking where date="2021-05-10" and is_booked=true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // at the start clean all element in the list
        seatsIdBookedByOther.clear();
        seatsIdLockedByAdmin.clear();
        seatsBookedByUserPrevious.clear();
        try { // if seat locked down by admin set to orange
            bookingScene = seat1.getScene();

            seatsIdLockedByAdmin = bookingModel.getSeatIdLockedByAdmin();
            //System.out.println(chooseDateController.getDateChosed() + "in booking controller");

            if (selectBookingToManageEmpController.getIsBookingManagementEmp()){ // if it is manage function
                greenPrompt.setText("You can only click the table you Booked!");
                redPrompt.setText("");
                darkRedOrBlackPrompt.setText("Black: Seat booked by you.");
                orangePrompt.setText("");
                seatsIdBookedByOther = bookingModel.getSeatIDBookedByOther(selectBookingToManageEmpController.getDateForManage());// we pass the date user choose in the list
            }
            else{ // else it is normal booking
                seatsIdBookedByOther = bookingModel.getSeatIDBookedByOther(chooseDateController.getDateChosed());
            }
            if (selectBookingToManageEmpController.getIsBookingManagementEmp()){ // if it is manage function
                /**update cancel完了直接到main page, 所以没必要设置这个暗红色因为用户不能在management里面进行book*/
                //do black
                seatIdBookedByUserManage = selectBookingToManageEmpController.getSeatIDBookedByCurrentUserManage();//get the seat id
                //for user seleted booking

            }
            else{
                seatsBookedByUserPrevious = bookingModel.getSeatIdBookedByUserPrevious(loginController.getEmployeeID(), chooseDateController.getDateChosed());
            }
/**if user is management then we not going to display the seat booked  by user previousely, not showing dark red color
 * we need to show the black color as show this user booked table in that date显示用户那一天booking的桌子,
 * red is the table booked by other not change, orange is lock down not change,
 * 我们可以直接从list中的stringsplit得到那一天的这个用户的seat_id 因为我们已经可以知道这个用户这天booking的座位号直接写条件manage然后在
 * 这个条件里做 判断拿到的seatid是几然后对应设置这个桌子为黑色代表这个用户这天预约的桌子。先做initialize不要管后面的点击。
 * Label最好也要改！！！！如果是management label变换 甚至不需要做用户点红色或者黄色检查因为已经检查了，但是如果用户点绿色要做判断
 * 显示错误信息，用户因为是management只能点黑色才能进行cancel或者update。*/

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

            if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if management employee
                if (seatIdBookedByUserManage == 1) {
                    seat1.setStyle("-fx-background-color: black;");
                }
                if (seatIdBookedByUserManage == 2) {
                    seat2.setStyle("-fx-background-color: black;");
                }
                if (seatIdBookedByUserManage == 3) {
                    seat3.setStyle("-fx-background-color: black;");
                }
                if (seatIdBookedByUserManage == 4) {
                    seat4.setStyle("-fx-background-color: black;");
                }
                if (seatIdBookedByUserManage == 5) {
                    seat5.setStyle("-fx-background-color: black;");
                }
                if (seatIdBookedByUserManage == 6) {
                    seat6.setStyle("-fx-background-color: black;");
                }
            }
            else{ // normal booking
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
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()) { // if it is manage by employee
            if (seat1.getStyle().equals("-fx-background-color: red;") || seat1.getStyle().equals("-fx-background-color: green;")
                    || seat1.getStyle().equals("-fx-background-color: ORANGE;")) {
                showBookManageEmpErrorStage();
            }
            else if (seat1.getStyle().equals("-fx-background-color: black;")){
                goToEmpBookManageChooseOptionStage();
            }
        }
        else{ // normal booking
            if (seat1.getStyle().equals("-fx-background-color: red;") || seat1.getStyle().equals("-fx-background-color: darkred;")
                    || seat1.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat1.getStyle().equals("-fx-background-color: green;")) {
                try {
                    // if this user booked already in that selected date like 11th but database says this user already booked a table in
                    //11 th in No 4 seat, so not going to let this user book another seat in 11 th

                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                        showBookAgainInSelecterDateErrorStage();
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
                    || seat2.getStyle().equals("-fx-background-color: ORANGE;")) {
                showBookManageEmpErrorStage();
            }
            else if (seat2.getStyle().equals("-fx-background-color: black;")){
                goToEmpBookManageChooseOptionStage();
            }
        }
        else{
            if (seat2.getStyle().equals("-fx-background-color: red;") || seat2.getStyle().equals("-fx-background-color: darkred;")
                    || seat2.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat2.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                        showBookAgainInSelecterDateErrorStage();
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
                    || seat3.getStyle().equals("-fx-background-color: ORANGE;")) {
                showBookManageEmpErrorStage();
            }
            else if (seat3.getStyle().equals("-fx-background-color: black;")){
                goToEmpBookManageChooseOptionStage();
            }
        }
        else{
            if (seat3.getStyle().equals("-fx-background-color: red;") || seat3.getStyle().equals("-fx-background-color: darkred;")
                    || seat3.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat3.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                        showBookAgainInSelecterDateErrorStage();
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
                    || seat4.getStyle().equals("-fx-background-color: ORANGE;")) {
                showBookManageEmpErrorStage();
            }
            else if (seat4.getStyle().equals("-fx-background-color: black;")){
                goToEmpBookManageChooseOptionStage();
            }
        }
        else{
            if (seat4.getStyle().equals("-fx-background-color: red;") || seat4.getStyle().equals("-fx-background-color: darkred;")
                    || seat4.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat4.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                        showBookAgainInSelecterDateErrorStage();
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
                    || seat5.getStyle().equals("-fx-background-color: ORANGE;")) {
                showBookManageEmpErrorStage();
            }
            else if (seat5.getStyle().equals("-fx-background-color: black;")){
                goToEmpBookManageChooseOptionStage();
            }
        }
        else{
            if (seat5.getStyle().equals("-fx-background-color: red;") || seat5.getStyle().equals("-fx-background-color: darkred;")
                    || seat5.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat5.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                        showBookAgainInSelecterDateErrorStage();
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
                    || seat6.getStyle().equals("-fx-background-color: ORANGE;")) {
                showBookManageEmpErrorStage();
            }
            else if (seat6.getStyle().equals("-fx-background-color: black;")){
                goToEmpBookManageChooseOptionStage();
            }
        }
        else{
            if (seat6.getStyle().equals("-fx-background-color: red;") || seat6.getStyle().equals("-fx-background-color: darkred;")
                    || seat6.getStyle().equals("-fx-background-color: ORANGE;")) {
                showSeatBookedErrorStage();
            } else if (seat6.getStyle().equals("-fx-background-color: green;")) {
                try {
                    if (bookingModel.isAlreadyBookedInSelectedDate(chooseDateController.getDateChosed(), loginController.getEmployeeID())) {
                        showBookAgainInSelecterDateErrorStage();
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

    public void showBookAgainInSelecterDateErrorStage() {
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
    public void goBackToDateSelectOrList(ActionEvent event){
        if (selectBookingToManageEmpController.getIsBookingManagementEmp()){//if now is manage by user instead of normal booking
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
        }
        else{ // normal booking
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
    public Scene getBookingScene(){
        return bookingScene;
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
