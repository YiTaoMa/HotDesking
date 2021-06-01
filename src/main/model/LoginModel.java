package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    /**
     * if user nad password one of them not exist because it is and so there is no row return when password or
     */
    public Boolean isLogin(String user, String pass) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // Only get the account not deactivated by admin, if an account is deactivate by admin, no matter if username and
        //password is correct or not. we will not select it.
        String query = "select * from Employee where username=? and password=? and is_deactivated=false";
        try {
            // it means select  all from the employee table where the user name is equal to the user input or not and password
            //if there is a match we login the user, both username and password match
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);//set the ? for username is the user which is the parameter passed by controller
            //this index 1,2 means the parameter of the query username = ? and password = ?
            preparedStatement.setString(2, pass);//set the ? for password is the user which is the parameter passed by controller

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { //if result set have next means there is a match of user input and database
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }

    public String getRole(String username, String password) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select character_role from Employee where username=? and password=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("character_role");
            } else {
                return "not found";
            }
        } catch (Exception e) {
            return "not found";
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }

    public int getEmployeeId(String username, String password) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select id from Employee where username=? and password=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }
}
