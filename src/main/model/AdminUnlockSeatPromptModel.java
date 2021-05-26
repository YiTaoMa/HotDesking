package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminUnlockSeatPromptModel {

    public boolean updateSeatIdLockedDownToUnlock(int seatID) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();

        String query = "update Seat set is_locked=false where id=?";
        PreparedStatement prst = null;
        boolean result = false;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
            prst.setInt(1, seatID);

            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
        return result;
    }
}
