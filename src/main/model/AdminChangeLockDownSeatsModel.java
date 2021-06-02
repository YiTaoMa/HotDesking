package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.*;
import java.util.HashMap;

public class AdminChangeLockDownSeatsModel {
    HashMap<Integer, Boolean> seatsStatus = new HashMap<>();

    public HashMap<Integer, Boolean> getAllSeatLockedDownStatus() throws SQLException {
        seatsStatus.clear();
        Connection connection;
        connection = SQLConnection.connect();
        Statement statement = connection.createStatement();
        String query = "select * from Seat";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                seatsStatus.put(resultSet.getInt("id"), resultSet.getBoolean("is_locked"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
        }
        return seatsStatus;
    }

    public boolean updateSeatsLockDownStatus(int seatID, boolean is_locked) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;

        String sqlUpdate = "update Seat set is_locked=? where id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setBoolean(1, is_locked);
            prst.setInt(2, seatID);

            return prst.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    public boolean isSelectedSeatHaveBookings(int seatID) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        ResultSet resultSet = null;
        String sql = "select * from Booking where seat_id=?"; // see if employee have booking
        try {
            prst = connection.prepareStatement(sql);
            prst.setInt(1, seatID);
            resultSet = prst.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}
