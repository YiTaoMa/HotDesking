package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.*;
import java.util.LinkedList;

public class BookingModel {

    LinkedList<Integer> seatsBookedByOther = new LinkedList<>();
    LinkedList<Integer> seatsLockedByAdmin = new LinkedList<>();
    LinkedList<Integer> seatsBookedByUserPrevious = new LinkedList<>();

    public LinkedList getSeatIdLockedByAdmin() throws SQLException {
        seatsLockedByAdmin.clear();
        Connection connection;
        connection = SQLConnection.connect();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "select id from Seat where is_locked=true";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                seatsLockedByAdmin.add(resultSet.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
        }
        return seatsLockedByAdmin;
    }

    public LinkedList getSeatIDBookedByOther(String date) throws SQLException { //the date is what user chose at the start
        seatsBookedByOther.clear();
        Connection connection;
        connection = SQLConnection.connect();
        String query = "select seat_id from Booking where date=? and is_booked=true";//that date which seat has been booked
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seatsBookedByOther.add(resultSet.getInt("seat_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
        return seatsBookedByOther;
    }

    public LinkedList getSeatIdBookedByUserPrevious(int id, String date) throws SQLException { //select the seat that user booked previous
        seatsBookedByUserPrevious.clear();
        Connection connection;
        connection = SQLConnection.connect();
        //so can not book again
        String query = "select seat_id from Whitelist where employee_id=? and is_locked=true and date=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seatsBookedByUserPrevious.add(resultSet.getInt("seat_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
        return seatsBookedByUserPrevious;
    }

    public boolean isAlreadyBookedInSelectedDate(String date, int employeeId) throws SQLException { // check if the user booked another seat in that day which is he attempt to book anotehr
        Connection connection;
        connection = SQLConnection.connect();
        //seat in that day second time, not allow
        String query = "select number from Booking where date=? and employee_id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, employeeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;// means if the user have the booking in that day not allow him to book in that day again
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
