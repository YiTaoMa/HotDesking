package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckinBookingConfirmModel {
    Connection connection;

    public CheckinBookingConfirmModel() {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public boolean updateCheckinStatus(int empId, String date) throws SQLException {
        String query = "update Booking set is_checked_in=true where employee_id=? and date=?";
        PreparedStatement prst = null;
        boolean result = false;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
            prst.setInt(1, empId);
            prst.setString(2, date);

            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            prst.close();
        }
        return result;
    }
}
