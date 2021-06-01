package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBUtils;
import main.SQLConnection;

import java.sql.*;

public class AdminSelectBookingToManageModel {

    ObservableList<String> itemsAdmin = FXCollections.observableArrayList();

    public ObservableList<String> getAllBookingDetail() throws SQLException {
        itemsAdmin.clear();
        Connection connection;
        connection = SQLConnection.connect();
        Statement statement = connection.createStatement();
        // include all booking both confirmed and un confirmed
        String query = "select * from Booking";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                itemsAdmin.add("Booking number---" + resultSet.getString("number") + "---Employee ID---" + resultSet.getString("employee_id") + "---Booked in---" + resultSet.getString("date") + "---At Seat Number---" + resultSet.getString("seat_id") + "---Is booked?---" + resultSet.getBoolean("is_booked") + "---Has Confirmed?---" + resultSet.getBoolean("has_confirmed") + "---Is checked in?---" + resultSet.getBoolean("is_checked_in"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
        }
        return itemsAdmin;
    }
}
