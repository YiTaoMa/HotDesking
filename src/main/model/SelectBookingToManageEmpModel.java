package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBUtils;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectBookingToManageEmpModel {

    ObservableList<String> items = FXCollections.observableArrayList();

    public ObservableList<String> getEmployeeBookingDetail(int employeeId) throws SQLException {
        items.clear();
        Connection connection;
        connection = SQLConnection.connect();
        String query = "select number,employee_id,date,seat_id,is_booked,has_confirmed,is_checked_in from Booking where employee_id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items.add("Booking Number---" + resultSet.getInt("number") + "---Employee ID---" + resultSet.getString("employee_id") + "---Booked in---" + resultSet.getString("date") + "---At Seat Number---" + resultSet.getString("seat_id") + "---Is Booked?---" + resultSet.getBoolean("is_booked") + "---Confirmed?---" + resultSet.getBoolean("has_confirmed") + "---Is Checked In?---" + resultSet.getBoolean("is_checked_in"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
        return items;
    }
}
