package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPassPopIDModel {

    Connection connection;

    public ResetPassPopIDModel() {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public String getSecretQuestion(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query); // PS to SQL statement
            preparedStatement.setInt(1, id);//represnet "?" place holder
            resultSet = preparedStatement.executeQuery(); // execute SQL statement and return results
            if (resultSet.next()) { //if there is an id same as the user input
                return resultSet.getString("secret_question");
            } else {
                return "not found";
            }
        } catch (Exception e) {
            return "not found";
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public Boolean isIdExist(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query); // PS to SQL statement
            preparedStatement.setInt(1, id);//represnet "?" place holder
            resultSet = preparedStatement.executeQuery(); // execute SQL statement and return results
            if (resultSet.next()) { //if there is an id same as the user input
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
