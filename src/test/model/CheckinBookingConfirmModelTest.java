package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.CheckinBookingConfirmModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class CheckinBookingConfirmModelTest {

    private static CheckinBookingConfirmModel checkinBookingConfirmModel;

    @BeforeAll
    public static void initialise() {
        checkinBookingConfirmModel = new CheckinBookingConfirmModel();
    }

    @Test
    void updateCheckinStatus_True_IfEmployeeIDAndDateExistInBookingTable() {
        assertAll(() -> assertEquals(true, checkinBookingConfirmModel.updateCheckinStatus(77777, "2021-05-20"),
                "ID 77777 have record in database in 2021-05-20, expected return true"));
    }

    @Test
    void updateCheckinStatus_False_IfEmployeeIDAndDateNotExistInBookingTable() {
        assertAll(() -> assertEquals(false, checkinBookingConfirmModel.updateCheckinStatus(00000, "2021-05-20"),
                "ID 00000 don't have record in database in 2021-05-20, expected return false"));
    }

    @AfterAll
    public static void updateTestIDBookingBackToOriginal() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String date = "2021-05-20";
        int empID = 77777;
        String query = "update Booking set is_checked_in=false where employee_id=? and date=?";
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, empID);
            prst.setString(2, date);
            prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}