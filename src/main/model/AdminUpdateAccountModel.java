package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminUpdateAccountModel {

    public boolean isIdUnRegistered(int inputID, int idFromList) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        ResultSet resultSet = null;

        String sqlSelect = "select * from Employee where id<>? and id=?";
        try {
            prst = connection.prepareStatement(sqlSelect);
            prst.setInt(1, idFromList);
            prst.setInt(2, inputID);
            resultSet = prst.executeQuery();

            if (resultSet.next()) {
                return false;
            } else {
                return true; // first see if id is good
            }
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    public boolean isUsernameUnRegistered(String usernameFromList, String usernameInput) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        ResultSet resultSet = null;

        String sqlSelect = "select * from Employee where username<>? and username=?";
        try {
            prst = connection.prepareStatement(sqlSelect);
            prst.setString(1, usernameFromList);
            prst.setString(2, usernameInput);

            resultSet = prst.executeQuery();

            if (resultSet.next()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    public boolean updateAccount(int inputID, int idFromList, String firstN, String lastN, String role, String userN, String passW, String SQ, String ASQ) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;

        String sqlUpdate = "update Employee set id=?,first_name=?,last_name=?,character_role=?,username=?,password=?,secret_question=?,answer_for_secret_question=? where id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setInt(1, inputID);
            prst.setString(2, firstN);
            prst.setString(3, lastN);
            prst.setString(4, role);
            prst.setString(5, userN);
            prst.setString(6, passW);
            prst.setString(7, SQ);
            prst.setString(8, ASQ);
            prst.setInt(9, idFromList);

            return prst.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    public boolean updateBookingEmpId(int empIdFromList, int empIdInput) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;

        String sqlUpdate = "update Booking set employee_id=? where employee_id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setInt(1, empIdInput);
            prst.setInt(2, empIdFromList);

            return prst.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    public boolean updateWhitelistEmpId(int empIdFromList, int empIdInput) {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;

        String sqlUpdate = "update Whitelist set employee_id=? where employee_id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setInt(1, empIdInput);
            prst.setInt(2, empIdFromList);

            return prst.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}
