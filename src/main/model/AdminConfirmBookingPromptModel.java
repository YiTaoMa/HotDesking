package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminConfirmBookingPromptModel {

    public boolean updateHasConfirmed(int empID, String date) {
        Connection connection;
        connection = SQLConnection.connect();
        String query = "update Booking set has_confirmed=true where employee_id=? and date=?";
        PreparedStatement prst = null;
        boolean result = false;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
            prst.setInt(1, empID);
            prst.setString(2, date);

            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
        return result;
    }
}
