package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.ConfirmBookingModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmBookingModelTest {
    private static ConfirmBookingModel confirmBookingModel;

    @BeforeAll
    public static void initialise() {
        confirmBookingModel = new ConfirmBookingModel();
    }

    @Test
    void insertBookingRecord_True_IfEmployeeNotExistInDatabase() {
        assertAll(() -> assertEquals(true, confirmBookingModel.insertBookingRecord(77778, "2021-05-20", 4),
                "new record can be insert into database, expected true"));
    }


    @Test
    void insertToWhitelist_True_AutoInsertAsBookingRecordInsert() {
        assertAll(() -> assertEquals(true, confirmBookingModel.insertToWhitelist(77778, 4, "2021-05-20"),
                "new record can be insert into database, expected true"));
    }

    @AfterAll
    public static void deleteTestIDBooking() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String date = "2021-05-20";
        int empID = 77778;
        String query = "delete from Booking where employee_id=? and date=?";
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

    @AfterAll
    public static void deleteTestIDWhitelist() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String date = "2021-05-21";
        int empID = 77778;
        String query = "delete from Whitelist where employee_id=? and date=?";
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