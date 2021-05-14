package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.model.AdminManageBookingModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AdminManageBookingController implements Initializable {
    LinkedList<Integer> seatsIdBookedByOther = new LinkedList<>();
    LinkedList<Integer> seatsIdLockedByAdmin = new LinkedList<>();
    private int seatBookedBySelectedUserToConfirm;
    AdminManageBookingModel adminManageBookingModel = new AdminManageBookingModel();
    AdminSelectBookingToManageController adminSelectBookingToManageController = new AdminSelectBookingToManageController();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seatsIdBookedByOther.clear();
        seatsIdLockedByAdmin.clear();
        try {
            seatsIdLockedByAdmin = adminManageBookingModel.getSeatIdLockedByAdmin();
            seatsIdBookedByOther =adminManageBookingModel.getSeatIDBookedByOther(adminSelectBookingToManageController.getDateForAdminManage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        seatBookedBySelectedUserToConfirm = adminSelectBookingToManageController.getSeatIDBookedByCurrentUserAdminManage();

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
// only 1 so do if else
        //seat that admin selected user booked not confirmed
        if (seatBookedBySelectedUserToConfirm == 1) {
            seat1.setStyle("-fx-background-color: blue;");
        }
        else if (seatBookedBySelectedUserToConfirm == 2) {
            seat2.setStyle("-fx-background-color: blue;");
        }
        else if (seatBookedBySelectedUserToConfirm == 3) {
            seat3.setStyle("-fx-background-color: blue;");
        }
        else if (seatBookedBySelectedUserToConfirm == 4) {
            seat4.setStyle("-fx-background-color: blue;");
        }
        else if (seatBookedBySelectedUserToConfirm == 5) {
            seat5.setStyle("-fx-background-color: blue;");
        }
        else if (seatBookedBySelectedUserToConfirm == 6) {
            seat6.setStyle("-fx-background-color: blue;");
        }


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
    }
    public void ClickSeat1(ActionEvent event) {
        if (seat1.getStyle().equals("-fx-background-color: green;") || seat1.getStyle().equals("-fx-background-color: red;")){
            System.out.println("can not click green or red");
        }
        else if (seat1.getStyle().equals("-fx-background-color: blue;")){
            System.out.println("do you want to confirm or reject this booking");
        }
        else if(seat1.getStyle().equals("-fx-background-color: ORANGE;")){
            System.out.println("do you want to lock down");
        }

    }
    public void ClickSeat2(ActionEvent event) {

    }
    public void ClickSeat3(ActionEvent event) {

    }
    public void ClickSeat4(ActionEvent event) {

    }
    public void ClickSeat5(ActionEvent event) {

    }
    public void ClickSeat6(ActionEvent event) {

    }
}
