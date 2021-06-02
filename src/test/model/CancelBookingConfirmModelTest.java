package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.CancelBookingConfirmModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class CancelBookingConfirmModelTest {
    private static CancelBookingConfirmModel cancelBookingConfirmModel;

    @BeforeAll
    public static void initialise() {
        cancelBookingConfirmModel = new CancelBookingConfirmModel();
    }

    @Test
    void deleteBookingRecord_True_IfThisEmployeeAndDateExistInBookingTable() {
        assertAll(() -> assertEquals(true, cancelBookingConfirmModel.deleteBookingRecord(77777, "2021-05-20"),
                "we have employee id 77777 booked in 2021-05-20 in the database, expected true"));
    }

    @Test
    void deleteBookingRecord_False_IfThisEmployeeAndDateNotExistInBookingTable() {
        assertAll(() -> assertEquals(false, cancelBookingConfirmModel.deleteBookingRecord(00000, "2021-05-20"),
                "we don't have employee id 00000 booked in 2021-05-20 in the database, expected false"));
    }

    @Test
    void deleteWhitelistRecord_True_IfThisEmployeeAndDateExistInWhitelistTable() {
        assertAll(() -> assertEquals(true, cancelBookingConfirmModel.deleteWhitelistRecord(77777, "2021-05-20"),
                "we have employee id 77777 booked in 2021-05-21 in the database (method auto add 1 day), expected true"));
    }

    @Test
    void deleteWhitelistRecord_False_IfThisEmployeeAndDateNotExistInWhitelistTable() {
        assertAll(() -> assertEquals(false, cancelBookingConfirmModel.deleteWhitelistRecord(00000, "2021-05-20"),
                "we don't have employee id 00000 booked in 2021-05-21 in the database (method auto add 1 day), expected false"));
    }

    /**
     * Add this record back after test
     */
    @AfterAll
    public static void insertTestIDBookingBack() {
        // note: change this record confirm/checkin, etc. information
        // will impact the corresponding tests.
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        int number = 25;
        String date = "2021-05-20";
        int seatID = 6;
        int empID = 77777;
        String query = "insert into Booking (number,employee_id,date,seat_id,is_booked,has_confirmed,is_checked_in) values(?,?,?,?,true,true,false)";
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, number);
            prst.setInt(2, empID);
            prst.setString(3, date);
            prst.setInt(4, seatID);
            prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    /**
     * Add this record back after test
     */
    @AfterAll
    public static void insertTestIDWhitelistBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        int id = 22;
        String date = "2021-05-21";
        int seatID = 6;
        int empID = 77777;
        String query = "insert into Whitelist (id,employee_id,seat_id,date,is_locked) values(?,?,?,?,true)";
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, id);
            prst.setInt(2, empID);
            prst.setInt(3, seatID);
            prst.setString(4, date);
            prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}