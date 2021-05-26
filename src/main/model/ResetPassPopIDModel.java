package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPassPopIDModel {

    public String getSecretQuestion(int id) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query); // PS to SQL statement
            preparedStatement.setInt(1, id);//represent "?" place holder
            resultSet = preparedStatement.executeQuery(); // execute SQL statement and return results
            if (resultSet.next()) { //if there is an id same as the user input
                return resultSet.getString("secret_question");
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

    public Boolean isIdExistAndNotDeactivated(int id) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where id=? and is_deactivated=false";
        try {
            preparedStatement = connection.prepareStatement(query); // PS to SQL statement
            preparedStatement.setInt(1, id);//represent "?" place holder
            resultSet = preparedStatement.executeQuery(); // execute SQL statement and return results
            if (resultSet.next()) { //if there is an id same as the user input
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
}
