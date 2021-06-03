package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminLockdownSeatPromptModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminLockdownSeatPromptModelTest {
    private static AdminLockdownSeatPromptModel adminLockdownSeatPromptModel;

    @BeforeAll
    public static void initialise() {
        adminLockdownSeatPromptModel = new AdminLockdownSeatPromptModel();
    }

    /**
     * Note: it will delete all seat 6 if have in booking and whitelist table
     * so, if add new booking with seat 6 then if run the test will delete
     * those new added bookings.
     */
    @Test
    void deleteBookingRecordWithLockedSeat_True_IfDeleteSuccess() {
        assertAll(() -> assertEquals(true, adminLockdownSeatPromptModel.deleteBookingRecordWithLockedSeat(6),
                "We have seat 6 in booking, delete success should return true."));
    }

    @Test
    void deleteWhitelistRecordWithLockedSeat_True_IfDeleteSuccess() {
        assertAll(() -> assertEquals(true, adminLockdownSeatPromptModel.deleteWhitelistRecordWithLockedSeat(6),
                "We have seat 6 in Whitelist, delete success should return true."));
    }

    @Test
    void updateSeatIdLockedDown_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminLockdownSeatPromptModel.updateSeatIdLockedDown(6),
                "We can update seat 6 lock down status, should return true."));
    }

    @AfterAll
    public static void addDeletedBookingRecordBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String sql = "insert into Booking (number,employee_id,date,seat_id,is_booked,has_confirmed,is_checked_in) values(?,?,?,?,?,?,?)"; // SQL statement
        try {
            prst = connection.prepareStatement(sql); // PS to SQL statement
            prst.setInt(1, 25);
            prst.setInt(2, 77777);
            prst.setString(3, "2021-05-20");
            prst.setInt(4, 6);
            prst.setInt(5, 1);
            prst.setInt(6, 1);
            prst.setInt(7, 0);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    @AfterAll
    public static void addDeletedWhitelistRecordBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String sql = "insert into Whitelist (id,employee_id,seat_id,date,is_locked) values(?,?,?,?,?)"; // SQL statement
        try {
            prst = connection.prepareStatement(sql); // PS to SQL statement
            prst.setInt(1, 22);
            prst.setInt(2, 77777);
            prst.setInt(3, 6);
            prst.setString(4, "2021-05-21");
            prst.setInt(5, 1);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    @AfterAll
    public static void updateRecordLockdownStatusBack() {
        Connection connection;
        connection = SQLConnection.connect();
        String query = "update Seat set is_locked=false where id=?";
        PreparedStatement prst = null;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
            prst.setInt(1, 6);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}