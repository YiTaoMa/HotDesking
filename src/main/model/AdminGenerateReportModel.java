package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminGenerateReportModel {

    public void generateEmployeeReport(File file) {
        Connection connection;
        connection = SQLConnection.connect();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from Employee";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("id,first_name,last_name,character_role,username,password,secret_question,answer_for_secret_question,is_deactivated");
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String character_role = resultSet.getString("character_role");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String secret_question = resultSet.getString("secret_question");
                String answer_for_secret_question = resultSet.getString("answer_for_secret_question");
                boolean is_deactivated = resultSet.getBoolean("is_deactivated");

                String line = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%b", id, first_name, last_name, character_role, username, password, secret_question, answer_for_secret_question, is_deactivated);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            fileWriter.close(); // make sure to close it
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
        }
    }

    public void generateBookingReport(File file) {
        Connection connection;
        connection = SQLConnection.connect();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from Booking";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write("number,employee_id,date,seat_id,is_booked,has_confirmed,is_checked_in");
            while (resultSet.next()) {
                Integer number = resultSet.getInt("number");
                Integer employee_id = resultSet.getInt("employee_id");
                String date = resultSet.getString("date");
                Integer seat_id = resultSet.getInt("seat_id");
                boolean is_booked = resultSet.getBoolean("is_booked");
                boolean has_confirmed = resultSet.getBoolean("has_confirmed");
                boolean is_checked_in = resultSet.getBoolean("is_checked_in");

                String line = String.format("%d,%d,%s,%d,%b,%b,%b", number, employee_id, date, seat_id, is_booked, has_confirmed, is_checked_in);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            fileWriter.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
        }
    }
}
