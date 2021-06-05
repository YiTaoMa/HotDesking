package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.AdminManageBookingModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AdminManageBookingController implements Initializable {
    final int seatID1 = 1;
    final int seatID2 = 2;
    final int seatID3 = 3;
    final int seatID4 = 4;
    final int seatID5 = 5;
    final int seatID6 = 6;

    protected static boolean isSeatRedOrGreen;
    protected static int seatIdCurrentClicked;
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

    @FXML
    private BorderPane borderPaneAdminManageBooking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seatsIdBookedByOther.clear();
        seatsIdLockedByAdmin.clear();
        try {
            seatsIdLockedByAdmin = adminManageBookingModel.getSeatIdLockedByAdmin();
            seatsIdBookedByOther = adminManageBookingModel.getSeatIDBookedByOther(adminSelectBookingToManageController.getDateForAdminManage());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
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

        if (adminSelectBookingToManageController.getIsCurrentBookingConfirmed()) { // if this booking is confirmed
            if (seatBookedBySelectedUserToConfirm == 1) {
                seat1.setStyle("-fx-background-color: darkblue;");
            } else if (seatBookedBySelectedUserToConfirm == 2) {
                seat2.setStyle("-fx-background-color: darkblue;");
            } else if (seatBookedBySelectedUserToConfirm == 3) {
                seat3.setStyle("-fx-background-color: darkblue;");
            } else if (seatBookedBySelectedUserToConfirm == 4) {
                seat4.setStyle("-fx-background-color: darkblue;");
            } else if (seatBookedBySelectedUserToConfirm == 5) {
                seat5.setStyle("-fx-background-color: darkblue;");
            } else if (seatBookedBySelectedUserToConfirm == 6) {
                seat6.setStyle("-fx-background-color: darkblue;");
            }

        } else { // else this booking is not confirmed
// only 1 so do if else
            if (seatBookedBySelectedUserToConfirm == 1) {
                seat1.setStyle("-fx-background-color: blue;");
            } else if (seatBookedBySelectedUserToConfirm == 2) {
                seat2.setStyle("-fx-background-color: blue;");
            } else if (seatBookedBySelectedUserToConfirm == 3) {
                seat3.setStyle("-fx-background-color: blue;");
            } else if (seatBookedBySelectedUserToConfirm == 4) {
                seat4.setStyle("-fx-background-color: blue;");
            } else if (seatBookedBySelectedUserToConfirm == 5) {
                seat5.setStyle("-fx-background-color: blue;");
            } else if (seatBookedBySelectedUserToConfirm == 6) {
                seat6.setStyle("-fx-background-color: blue;");
            }
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

    public void clickSeat1(ActionEvent event) {
        setSeatIdCurrentClicked(seatID1);// every time set the current seat to current button number which represent
        //current clicked seat
        if (seat1.getStyle().equals("-fx-background-color: green;") || seat1.getStyle().equals("-fx-background-color: red;")) {
            // if it is green or red, no need to do darkred as admin not user can not booking
            // admin can lock down it only 1 operation
            isSeatRedOrGreen = true;// used to do different lock down in admin lockdown seat prompt controller
            switchToAdminLockdownSeatPrompt();

        } else if (seat1.getStyle().equals("-fx-background-color: blue;")) {
            isSeatRedOrGreen = false; // set it to false if not red or green as it will not do operation of red or green
            //if it is blue, admin can confirm it or reject it (can reject confirmed booking) or lock down it.
            switchToAdminBookManageChooseOption();
        } else if (seat1.getStyle().equals("-fx-background-color: darkblue;")) { // use same reject and lockdown operation
            isSeatRedOrGreen = false;
            // can not confirm again, no confirm option, only reject and lock down option
            switchToAdminBookManageChooseOptionWithConfirmed();//independent controller only because it do not have the confirm option.

        } else if (seat1.getStyle().equals("-fx-background-color: ORANGE;")) {
            isSeatRedOrGreen = false;
            // admin be able to unlock this table.
            // if a table previously booked by user then it will be canceled and if unlock this table, it won't come back.
            // here id is the current seat admin clicked.already set this to current seat 1 when clicked
            switchToAdminUnlockSeatPrompt();
        }
    }

    public void clickSeat2(ActionEvent event) {
        setSeatIdCurrentClicked(seatID2);
        if (seat2.getStyle().equals("-fx-background-color: green;") || seat2.getStyle().equals("-fx-background-color: red;")) {
            isSeatRedOrGreen = true;
            switchToAdminLockdownSeatPrompt();
        } else if (seat2.getStyle().equals("-fx-background-color: blue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOption();
        } else if (seat2.getStyle().equals("-fx-background-color: darkblue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOptionWithConfirmed();
        } else if (seat2.getStyle().equals("-fx-background-color: ORANGE;")) {
            isSeatRedOrGreen = false;
            switchToAdminUnlockSeatPrompt();
        }
    }

    public void clickSeat3(ActionEvent event) {
        setSeatIdCurrentClicked(seatID3);
        if (seat3.getStyle().equals("-fx-background-color: green;") || seat3.getStyle().equals("-fx-background-color: red;")) {
            isSeatRedOrGreen = true;
            switchToAdminLockdownSeatPrompt();
        } else if (seat3.getStyle().equals("-fx-background-color: blue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOption();
        } else if (seat3.getStyle().equals("-fx-background-color: darkblue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOptionWithConfirmed();
        } else if (seat3.getStyle().equals("-fx-background-color: ORANGE;")) {
            isSeatRedOrGreen = false;
            switchToAdminUnlockSeatPrompt();
        }
    }

    public void clickSeat4(ActionEvent event) {
        setSeatIdCurrentClicked(seatID4);
        if (seat4.getStyle().equals("-fx-background-color: green;") || seat4.getStyle().equals("-fx-background-color: red;")) {
            isSeatRedOrGreen = true;
            switchToAdminLockdownSeatPrompt();
        } else if (seat4.getStyle().equals("-fx-background-color: blue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOption();
        } else if (seat4.getStyle().equals("-fx-background-color: darkblue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOptionWithConfirmed();
        } else if (seat4.getStyle().equals("-fx-background-color: ORANGE;")) {
            isSeatRedOrGreen = false;
            switchToAdminUnlockSeatPrompt();
        }
    }

    public void clickSeat5(ActionEvent event) {
        setSeatIdCurrentClicked(seatID5);
        if (seat5.getStyle().equals("-fx-background-color: green;") || seat5.getStyle().equals("-fx-background-color: red;")) {
            isSeatRedOrGreen = true;
            switchToAdminLockdownSeatPrompt();
        } else if (seat5.getStyle().equals("-fx-background-color: blue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOption();
        } else if (seat5.getStyle().equals("-fx-background-color: darkblue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOptionWithConfirmed();
        } else if (seat5.getStyle().equals("-fx-background-color: ORANGE;")) {
            isSeatRedOrGreen = false;
            switchToAdminUnlockSeatPrompt();
        }
    }

    public void clickSeat6(ActionEvent event) {
        setSeatIdCurrentClicked(seatID6);
        if (seat6.getStyle().equals("-fx-background-color: green;") || seat6.getStyle().equals("-fx-background-color: red;")) {
            isSeatRedOrGreen = true;
            switchToAdminLockdownSeatPrompt();
        } else if (seat6.getStyle().equals("-fx-background-color: blue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOption();
        } else if (seat6.getStyle().equals("-fx-background-color: darkblue;")) {
            isSeatRedOrGreen = false;
            switchToAdminBookManageChooseOptionWithConfirmed();
        } else if (seat6.getStyle().equals("-fx-background-color: ORANGE;")) {
            isSeatRedOrGreen = false;
            switchToAdminUnlockSeatPrompt();
        }
    }

    public void switchToAdminBookManageChooseOption() {
        Scene scene = borderPaneAdminManageBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminBookManageChooseOption.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Choose Option");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminBookManageChooseOption.fxml");
        }
    }

    public void goBackToSelectBooking(ActionEvent event) {
        Scene scene = borderPaneAdminManageBooking.getScene();
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

    public void switchToAdminBookManageChooseOptionWithConfirmed() { // dark blue confirmed seat can be reject and lockdown only
        Scene scene = borderPaneAdminManageBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminBMChooseOptionConfirmedB.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Choose Option with confirmed seat");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminBMChooseOptionConfirmedB.fxml");
        }
    }

    public void switchToAdminLockdownSeatPrompt() {
        Scene scene = borderPaneAdminManageBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminLockdownSeatPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Booking Management-Lock down Seat");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminLockdownSeatPrompt.fxml");
        }
    }

    public void switchToAdminUnlockSeatPrompt() {
        Scene scene = borderPaneAdminManageBooking.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/adminUnlockSeatPrompt.fxml"));
            primaryStage.setTitle("Hotdesking-Admin-Booking Management-Unlock Seat");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the adminUnlockSeatPrompt.fxml");
        }
    }

    public boolean getIsSeatRedOrGreen() {
        return isSeatRedOrGreen;
    }

    public void setSeatIdCurrentClicked(int seatID) {
        seatIdCurrentClicked = seatID;
    }

    public int getSeatIdCurrentClicked() {
        return seatIdCurrentClicked;
    }
}
