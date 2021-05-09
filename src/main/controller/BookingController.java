package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BackgroundFill;
import main.model.BookingModel;

import java.awt.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    public BookingModel bookingModel = new BookingModel();
    public ChooseDateController chooseDateController =new ChooseDateController();
    public LoginController loginController = new LoginController();

    LinkedList<Integer>  seatsIdBookedByOther = new LinkedList<>();
    LinkedList<Integer>  seatsIdLockedByAdmin = new LinkedList<>();
    LinkedList<Integer>  seatsBookedByUserPrevious = new LinkedList<>();
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
            seatsIdLockedByAdmin= bookingModel.getSeatIdLockedByAdmin();
            //System.out.println(chooseDateController.getDateChosed() + "in booking controller");
            seatsIdBookedByOther = bookingModel.getSeatIDBookedByOther(chooseDateController.getDateChosed());
            //System.out.println(seatsIdBookedByOther);
            seatsBookedByUserPrevious = bookingModel.getSeatIdBookedByUserPrevious(loginController.getEmployeeID(),chooseDateController.getDateChosed());

            if (!seatsIdBookedByOther.isEmpty()){
                for (int i=0;i<seatsIdBookedByOther.size();i++){
                    if (seatsIdBookedByOther.get(i) ==1){
                        seat1.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) ==2){
                        seat2.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) ==3){
                        seat3.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) ==4){
                        seat4.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) ==5){
                        seat5.setStyle("-fx-background-color: red;");
                    }
                    if (seatsIdBookedByOther.get(i) ==6){
                        seat6.setStyle("-fx-background-color: red;");
                    }
                }
            }
            //else{
            //    System.out.println("error in seatIdBookedByOther");
            //}
            //if this user booked previously like i booked in 9th then the whitelist record 10th is locked as true, so when
            //i come to book at 10th, the table booked by me in 9th will be dark red color and can not book again in 10th
            if (!seatsBookedByUserPrevious.isEmpty()){
                for (int i=0;i<seatsBookedByUserPrevious.size();i++){
                    if (seatsBookedByUserPrevious.get(i) ==1){
                        seat1.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) ==2){
                        seat2.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) ==3){
                        seat3.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) ==4){
                        seat4.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) ==5){
                        seat5.setStyle("-fx-background-color: darkred;");
                    }
                    if (seatsBookedByUserPrevious.get(i) ==6){
                        seat6.setStyle("-fx-background-color: darkred;");
                    }
                }
            }
            //else {
            //    System.out.println("error in seatIdBookedByUserPrevious");
            //}
            //lock down always will replaced by other high pior
            if (!seatsIdLockedByAdmin.isEmpty()){
                for (int i=0;i<seatsIdLockedByAdmin.size();i++){
                    if (seatsIdLockedByAdmin.get(i) ==1){
                        seat1.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) ==2){
                        seat2.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) ==3){
                        seat3.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) ==4){
                        seat4.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) ==5){
                        seat5.setStyle("-fx-background-color: ORANGE;");
                    }
                    if (seatsIdLockedByAdmin.get(i) ==6){
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

}
