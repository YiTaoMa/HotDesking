package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminConfirmBookingPromptModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminConfirmBookingPromptModelTest {
    private static AdminConfirmBookingPromptModel adminConfirmBookingPromptModel;

    @BeforeAll
    public static void initialise() {
        adminConfirmBookingPromptModel = new AdminConfirmBookingPromptModel();
    }

    @BeforeAll
    public static void updateHasConfirmedStatusFirst() {
        Connection connection;
        connection = SQLConnection.connect();
        int empID = 77777;
        String date = "2056-06-13";
        String query = "update Booking set has_confirmed=false where employee_id=? and date=?";
        PreparedStatement prst = null;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
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

    /**
     * There is no need to test if employee id is wrong as this booking have to exist and done the check in the controller
     */
    @Test
    void updateHasConfirmed_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminConfirmBookingPromptModel.updateHasConfirmed(77777, "2056-06-13"),
                "As employee id and date exist in booking, update success expected true."));
    }
}