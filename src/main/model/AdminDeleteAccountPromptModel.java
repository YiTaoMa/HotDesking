package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDeleteAccountPromptModel {

    public boolean deleteSelectedEmployeeAccount(int empID) { // delete passed parameter employee
        Connection connection;
        connection = SQLConnection.connect();
        String query = "delete from Employee where id=?";
        PreparedStatement prst = null;
        boolean result = false;
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, empID);
            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
        return result;
    }

    public boolean deleteSelectedEmpBookingRecords(int empID) { // As employee not exist delete booking
        Connection connection;
        connection = SQLConnection.connect();
        String query = "delete from Booking where employee_id=?";
        PreparedStatement prst = null;
        boolean result = false;
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, empID);
            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
        return result;
    }

    public boolean deleteSelectedEmpWhitelistRecords(int empID) {
        Connection connection;
        connection = SQLConnection.connect();
        String query = "delete from Whitelist where employee_id=?";
        PreparedStatement prst = null;
        boolean result = false;
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, empID);
            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
        return result;
    }

    public boolean isSelectedEmpHaveBookings(int empID) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        ResultSet resultSet = null;
        String sqlregister = "select * from Booking where employee_id=?"; // see if employee have booking
        try {
            prst = connection.prepareStatement(sqlregister);
            prst.setInt(1, empID);
            resultSet = prst.executeQuery();

            if (resultSet.next()) { // if this employee have bookings
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
