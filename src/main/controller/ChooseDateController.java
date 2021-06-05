package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ChooseDateController implements Initializable {
    protected static String dateChose;
    private final String pattern = "yyyy-MM-dd";
    SelectBookingToManageEmpController selectBookingToManageEmpController = new SelectBookingToManageEmpController();

    @FXML
    private DatePicker bookingDatePicker;
    @FXML
    private BorderPane borderpaneChooseDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        bookingDatePicker.setConverter(converter);

        bookingDatePicker.setValue(LocalDate.now());
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
        bookingDatePicker.setDayCellFactory(dayCellFactory);
        bookingDatePicker.setValue(bookingDatePicker.getValue().plusDays(1));
    }

    public void goToBooking(ActionEvent event) {
        // if it is normal booking we first set management booking to false
        //so when you go to booking controller it will perform the initialise as booking management
        selectBookingToManageEmpController.setIsBookingManagementEmp(false);

        dateChose = bookingDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Scene scene = borderpaneChooseDate.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/booking.fxml"));
            primaryStage.setTitle("Hotdesking-Booking");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the Booking scene");
        }
    }

    public String getDateChose() {
        return dateChose;
    }

    public void goBackToMain(ActionEvent event) {
        Scene scene = borderpaneChooseDate.getScene();
        Window window = scene.getWindow();
        Stage primaryStage = (Stage) window;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/main.fxml"));
            primaryStage.setTitle("Hotdesking-Main");
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Cannot load the main.fxml");
        }
    }
}
