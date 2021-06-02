package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminChangeLockDownSeatsModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminChangeLockDownSeatsModelTest {
    private static AdminChangeLockDownSeatsModel adminChangeLockDownSeatsModel;

    @BeforeAll
    public static void initialise() {
        adminChangeLockDownSeatsModel = new AdminChangeLockDownSeatsModel();
    }

    @Test
    void getAllSeatLockedDownStatus_False_SeatTableExist() {
        assertAll(() -> assertEquals(false, adminChangeLockDownSeatsModel.getAllSeatLockedDownStatus().isEmpty(),
                "We have Seat table in database, should not be empty."));
    }

    @Test
    void updateSeatsLockDownStatus_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminChangeLockDownSeatsModel.updateSeatsLockDownStatus(2, false),
                "We can update seat 2 to false as original is true."));
    }

    @Test
    void isSelectedSeatHaveBookings_True_IfThisSeatHaveBookings() {
        assertAll(() -> assertEquals(true, adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(1),
                "We have bookings for seat 1, expected return is true"));
    }

    @Test
    void isSelectedSeatHaveBookings_False_IfThisSeatDontHaveBookings() {
        assertAll(() -> assertEquals(false, adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(5),
                "We don't have bookings for seat 5 as it is locked down, expected return is false"));
    }

    @AfterAll
    public static void updateSeat2StatusBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        int seatID = 2;
        String sqlUpdate = "update Seat set is_locked=true where id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setInt(1, seatID);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}