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
    // 任何31号的预定在30号午夜前（30号的半夜12：00前）没有确认，自动取消。

    // 如果当前日期或者30号和预约的31号相差少于等于1天 并且 当前日期的时间（30号）不在当前日期（当天）时间的半夜24：00 之前 并且
    // has_confirmed == false没有确认，那么自动删除这个预约eclipse例子用

    //也就是说如果今天是30号，预约是31号，那么现在是30号的六点，没有到今天30号的午夜12：00那么还是有时间confirmed的不自动取消，
    //但是今天30号的时间过了今天的午夜12：00则自动取消。


    //------------------- correct one below


    //！！！！！！这个已经在eclipse实现，假设今天20号目标日期19号小于今天，判断为今天不在19号之前自动删除即可
    //！！！！！！假设目标预约21号，今天20号在21号之前，那么可以confirm但是过了今天24：00也就是明天了那么不能confirm自动删除。

    //只需要一个判断不需要这么多，假设今天19号，在21号之前，那么也是可以confirm的
    //不需要判断当前日期或者30号和预约的31号相差少于等于1天

    //has_confirmed == false 需要判断

    //这个判断时间的最好在一开始，程序运行初就进行？？

    //databse: DELETE FROM table WHERE date < '2011-09-21 08:21:22';
    //可能不需要这样因为我们判断了这个预约如果小于今天或者过了时间没被confirm我们只要删除这个预约即可
    //但可能不是只删除这一个选择的booking而是选出所有数据库中的预约日期没有被confirmed，然后进行每个日期的检查，跟今天比较，
    //因为今天小于预约日期的话就可以confirm，不删除，如果今天大于预约日期的就删除

    //不太需要额外检查： 如果，预约的日期小于或者等于今天的日期并且没有被admin确认则自动删除。因为预约必须被admin在前一天确认。
    public void ClickSeat1(ActionEvent event) {
        if (seat1.getStyle().equals("-fx-background-color: green;") || seat1.getStyle().equals("-fx-background-color: red;")) {
            // if it is green or red, no need to do darkred as admin not user can not booking
            // admin can lock down it only 1 operation
            System.out.println("Do you want to lock down this seat");
        } else if (seat1.getStyle().equals("-fx-background-color: blue;")) {
            //if it is blue, admin can confirm it or reject it (can reject confirmed booking) or lock down it.
            switchToAdminBookManageChooseOption();
        }
        else if (seat1.getStyle().equals("-fx-background-color: darkblue;")){
            // can not confirm again, no confirm option, only reject and lock down option
        }
        else if (seat1.getStyle().equals("-fx-background-color: ORANGE;")) {
            // admin be able to unlock this table.
            // if a table previously booked by user then it will be canceled and if unlock this table, it won't come back.
            System.out.println("do you want to un lock this table?");
        }

    }

    public void ClickSeat2(ActionEvent event) {
        if (seat2.getStyle().equals("-fx-background-color: green;") || seat2.getStyle().equals("-fx-background-color: red;")) {
            System.out.println("Do you want to lock down this seat");
        } else if (seat2.getStyle().equals("-fx-background-color: blue;")) {
            switchToAdminBookManageChooseOption();
        }
        else if (seat2.getStyle().equals("-fx-background-color: darkblue;")){

        }
        else if (seat2.getStyle().equals("-fx-background-color: ORANGE;")) {
            System.out.println("do you want to un lock this table?");
        }

    }

    public void ClickSeat3(ActionEvent event) {
        if (seat3.getStyle().equals("-fx-background-color: green;") || seat3.getStyle().equals("-fx-background-color: red;")) {
            System.out.println("Do you want to lock down this seat");
        } else if (seat3.getStyle().equals("-fx-background-color: blue;")) {
            switchToAdminBookManageChooseOption();
        }
        else if (seat3.getStyle().equals("-fx-background-color: darkblue;")){

        }
        else if (seat3.getStyle().equals("-fx-background-color: ORANGE;")) {
            System.out.println("do you want to un lock this table?");
        }

    }

    public void ClickSeat4(ActionEvent event) {
        if (seat4.getStyle().equals("-fx-background-color: green;") || seat4.getStyle().equals("-fx-background-color: red;")) {
            System.out.println("Do you want to lock down this seat");
        } else if (seat4.getStyle().equals("-fx-background-color: blue;")) {
            switchToAdminBookManageChooseOption();
        }
        else if (seat4.getStyle().equals("-fx-background-color: darkblue;")){

        }
        else if (seat4.getStyle().equals("-fx-background-color: ORANGE;")) {
            System.out.println("do you want to un lock this table?");
        }

    }

    public void ClickSeat5(ActionEvent event) {
        if (seat5.getStyle().equals("-fx-background-color: green;") || seat5.getStyle().equals("-fx-background-color: red;")) {
            System.out.println("Do you want to lock down this seat");
        } else if (seat5.getStyle().equals("-fx-background-color: blue;")) {
            switchToAdminBookManageChooseOption();
        }
        else if (seat5.getStyle().equals("-fx-background-color: darkblue;")){

        }
        else if (seat5.getStyle().equals("-fx-background-color: ORANGE;")) {
            System.out.println("do you want to un lock this table?");
        }

    }

    public void ClickSeat6(ActionEvent event) {
        if (seat6.getStyle().equals("-fx-background-color: green;") || seat6.getStyle().equals("-fx-background-color: red;")) {
            System.out.println("Do you want to lock down this seat");
        } else if (seat6.getStyle().equals("-fx-background-color: blue;")) {
            switchToAdminBookManageChooseOption();
        }
        else if (seat6.getStyle().equals("-fx-background-color: darkblue;")){

        }
        else if (seat6.getStyle().equals("-fx-background-color: ORANGE;")) {
            System.out.println("do you want to un lock this table?");
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

}
