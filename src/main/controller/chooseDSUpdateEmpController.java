package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.model.BookingModel;
import main.model.ChooseDSUpdateEmpModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class chooseDSUpdateEmpController implements Initializable {
    private final String pattern = "yyyy-MM-dd";
    ObservableList<Integer> seatsId = FXCollections.observableArrayList(1,2,3,4,5,6);
SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();
BookingModel bookingModel = new BookingModel();
LoginController loginController = new LoginController();
ChooseDSUpdateEmpModel chooseDSUpdateEmpModel = new ChooseDSUpdateEmpModel();

    @FXML
    private DatePicker updateBookingDatePicker;
    @FXML
    private ChoiceBox choiceNewSeatBox;
    @FXML
    private Label updateErrorMessage;
    @FXML
    private BorderPane borderPaneChooseDSUpdateEmp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
// set the defalut choic box value to this user's selected booking's seatid in the list
        /**接下来拿到这个date和choic box的值做检查查看这个用户选择的新日期对应的新桌子是否已经被booking，如果是错误信息
         * 还要查是否以经被他自己booking */
        choiceNewSeatBox.setValue(selectBookingToManageEmpController.getSeatIDBookedByCurrentUserManage());
        choiceNewSeatBox.setItems(seatsId);
        updateErrorMessage.setText("");

        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        updateBookingDatePicker.setConverter(converter);

        updateBookingDatePicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore( //user only can book tomorrow date not today, every date before today will block
                                        LocalDate.now().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        updateBookingDatePicker.setDayCellFactory(dayCellFactory);
        updateBookingDatePicker.setValue(updateBookingDatePicker.getValue().plusDays(1));
    }
//isAlreadyBookedInSelectedDate from booking model check if the user already have the booking in that date user entered.
    //because if user already have booking in that day not going to let the user update this booking to that day
    //
    //select number from Booking where seat_id = 用户选择的椅子号 and date = 用选择的日期。
    // if there is result returned means seat in that date already been booked, return error message
public void updateBooking(ActionEvent event){
        int seatIdFromChoiceBox = Integer.parseInt(choiceNewSeatBox.getValue().toString());
        String updateBookingChosedDate = updateBookingDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            // first condition:if the user already booked in that date he can not change this booking to that date
            //second condition: // if user desired date and seat id already exist in that day, means already been booked, not going to let the user choose that date and seat.
            // third condition: if user can't book in that day because he booked yestday this seat
            if (bookingModel.isAlreadyBookedInSelectedDate(updateBookingChosedDate ,loginController.getEmployeeID())){
                updateErrorMessage.setText("Error! You already booked another seat in the date you chose!");
            }
            else if (chooseDSUpdateEmpModel.isSeatAlreadyBookedInThatDate(seatIdFromChoiceBox,updateBookingChosedDate)){
                updateErrorMessage.setText("Error! The seat you tend to book in that date already booked by others!");
            } else if (chooseDSUpdateEmpModel.isSeatIdBookedByUserPrevious(seatIdFromChoiceBox,loginController.getEmployeeID(),updateBookingChosedDate)) {
                updateErrorMessage.setText("Error! The seat already been booked by you previously!");
            }
            else if (chooseDSUpdateEmpModel.isSeatLockedDown(seatIdFromChoiceBox)){ // if the seat user chose is locked down by admin then not going to let him update
                updateErrorMessage.setText("Error! The seat already been Locked Down!");
            }
            /**ALSO can not booking the table is locked down add antoerh condition*/
            /**we pass the seatid user choosed in update page and check if the seat user chosed in locked down*/
            else{
                //if all godd, user can update the selected seat and update whitelist
             if (chooseDSUpdateEmpModel.updateBooking(updateBookingChosedDate,seatIdFromChoiceBox,loginController.getEmployeeID(),selectBookingToManageEmpController.getDateForManage())){
                 //if update success update whitelist again
                     if (chooseDSUpdateEmpModel.updateWhitelist(seatIdFromChoiceBox,updateBookingChosedDate,loginController.getEmployeeID(),selectBookingToManageEmpController.getDateForManage())){
                         //if update in white list success we show success message and go to main page
                         switchSceneToMain();
                         showUpdateBookingSuccessStage();
                     }
             }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
}
    public void showUpdateBookingSuccessStage(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/updateBookingEmpSuccess.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotdesking-Update Booking Success");
            stage.show();
        } catch (IOException e) {
            System.out.println("Cannot load the updateBookingEmpSuccess.fxml");
        }
    }
    public void switchSceneToMain(){
        Scene scene = borderPaneChooseDSUpdateEmp.getScene();
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
    public void goBackToempBookManageChooseOptionScene(ActionEvent event){
        Scene scene = borderPaneChooseDSUpdateEmp.getScene();
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
