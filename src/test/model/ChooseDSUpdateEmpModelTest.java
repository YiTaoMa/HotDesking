package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.ChooseDSUpdateEmpModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ChooseDSUpdateEmpModelTest {
    private static ChooseDSUpdateEmpModel chooseDSUpdateEmpModel;

    @BeforeAll
    public static void initialise() {
        chooseDSUpdateEmpModel = new ChooseDSUpdateEmpModel();
    }

    @Test
    void isBookedAnotherSeatInSelectedDateUpdateWithNoOtherSeat() throws SQLException {
        assertEquals(false, chooseDSUpdateEmpModel.isBookedAnotherSeatInSelectedDateUpdate("2021-05-20", 77777, 6),
                "ID 77777 don't have any other seat except seat 6 in 2021-05-20, expected return false");
    }

    @Test
    void isBookedAnotherSeatInSelectedDateUpdateWithOtherSeat() throws SQLException {
        assertEquals(true, chooseDSUpdateEmpModel.isBookedAnotherSeatInSelectedDateUpdate("2021-05-25", 77777, 1),
                "ID 77777 have another seat: 4 except seat 1 in 2021-05-25, expected return true");
    }

    @Test
    void isSeatAlreadyBookedInThatDateWithBooked() throws SQLException {
        assertEquals(true, chooseDSUpdateEmpModel.isSeatAlreadyBookedInThatDate(1, "2021-05-12"),
                "seat id 1 already booked in 2021-05-12 expected return true");
    }

    @Test
    void isSeatAlreadyBookedInThatDateWithUnBooked() throws SQLException {
        assertEquals(false, chooseDSUpdateEmpModel.isSeatAlreadyBookedInThatDate(2, "2021-05-12"),
                "seat id 2 not booked by anyone in 2021-05-12 expected return false");
    }

    @Test
    void isSeatLockedDownWithLockedDown() throws SQLException {
        assertEquals(true, chooseDSUpdateEmpModel.isSeatLockedDown(2),
                "seat id 2 locked down expected return true");
    }

    @Test
    void isSeatLockedDownWithNotLockedDown() throws SQLException {
        assertEquals(false, chooseDSUpdateEmpModel.isSeatLockedDown(1),
                "seat id 1 not locked down expected return false");
    }

    @Test
    void updateBookingWithExistEmployeeIDAndDate() throws SQLException {
        assertEquals(true, chooseDSUpdateEmpModel.updateBooking("2021-05-22", 2, 77777, "2021-05-20"),
                "seat id 2 can be booked by Employee ID 77777 in 2021-05-22 expected return true");
    }

    @Test
    void updateBookingWithIncorrectEmployeeID() throws SQLException {
        assertEquals(false, chooseDSUpdateEmpModel.updateBooking("2021-05-22", 2, 00000, "2021-05-20"),
                "Employee ID 00000 not exist expected return false");
    }

    @Test
    void updateWhitelistWithCorrectEmployeeID() throws SQLException {
        assertEquals(true, chooseDSUpdateEmpModel.updateWhitelist(2, "2021-05-22", 77777, "2021-05-20"),
                "Employee ID 77777 have record in whitelist expected return true.");
    }

    @Test
    void updateWhitelistWithIncorrectEmployeeID() throws SQLException {
        assertEquals(false, chooseDSUpdateEmpModel.updateWhitelist(2, "2021-05-22", 00000, "2021-05-20"),
                "Employee ID 00000 don't have record in whitelist expected return false.");
    }

    /**
     * update id 77777 booking record back to normal after test
     */
    @AfterAll
    public static void updateTestIDBookingBackToOriginal() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String date = "2021-05-20";
        int seatID = 6;
        int id = 77777;
        String oldDate = "2021-05-22";// As 7777 already update to 2021-05-22 so we update back
        String query = "update Booking set date=?,seat_id=?,has_confirmed=false where employee_id=? and date=?";
        try {
            prst = connection.prepareStatement(query);
            prst.setString(1, date);
            prst.setInt(2, seatID);
            prst.setInt(3, id);
            prst.setString(4, oldDate);
            prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    /**
     * update ID 77777 Whitelist record back to normal after test
     */
    @AfterAll
    public static void updateTestIDWhitelistBackToOriginal() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String date = "2021-05-21";// normal date
        int seatID = 6;
        int id = 77777;
        String oldDate = "2021-05-23";// As 7777 already update to 2021-05-23 in white list 05-22 in Booking
        String query = "update Whitelist set seat_id=?,date=? where employee_id=? and date=?";
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, seatID);
            prst.setString(2, date);
            prst.setInt(3, id);
            prst.setString(4, oldDate);
            prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}