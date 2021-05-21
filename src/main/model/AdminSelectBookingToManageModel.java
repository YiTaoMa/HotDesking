package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.DBUtils;
import main.SQLConnection;

import java.sql.*;

public class AdminSelectBookingToManageModel {
    //Connection connection;
    ObservableList<String> itemsAdmin = FXCollections.observableArrayList();

    //public AdminSelectBookingToManageModel() {
    //    connection = SQLConnection.connect();
    //    if (connection == null)
    //        System.exit(1);
    //}

    public ObservableList<String> getAllBookingDetail() throws SQLException {
        itemsAdmin.clear();
        Connection connection;
        connection = SQLConnection.connect();
        //select * from Booking
        Statement statement = connection.createStatement();
        // include all booking both confirmed and un confirmed
        String query = "select * from Booking";//where has_confirmed=false
        //PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            //resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itemsAdmin.add("Booking number---" + resultSet.getString("number") + "---Employee ID---" + resultSet.getString("employee_id") + "---Booked in---" + resultSet.getString("date") + "---At Seat Number---" + resultSet.getString("seat_id") + "---Is booked?---" + resultSet.getBoolean("is_booked") + "---Has Confirmed?---" + resultSet.getBoolean("has_confirmed") + "---Is checked in?---" + resultSet.getBoolean("is_checked_in"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
            //statement.close();
            //resultSet.close();
        }
        return itemsAdmin;
    }
}
