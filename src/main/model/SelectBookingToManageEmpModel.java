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
   // DBUtils dbUtils = new DBUtils();
    //Connection connection;
    ObservableList<String> items = FXCollections.observableArrayList();

    //public SelectBookingToManageEmpModel() {
    //    connection = SQLConnection.connect();
    //    if (connection == null)
    //        System.exit(1);
    //}

    public ObservableList<String> getEmployeeBookingDetail(int employeeId) throws SQLException {
        Connection connection;
        connection = SQLConnection.connect();
        String query = "select employee_id,date,seat_id,has_confirmed from Booking where employee_id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items.add("Employee ID---" + resultSet.getString("employee_id") + "---Booked in---" + resultSet.getString("date") + "---At Seat Number---" + resultSet.getString("seat_id") + "---Confirmed?---" + resultSet.getBoolean("has_confirmed"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
            //preparedStatement.close();
            //resultSet.close();
        }
        return items;
    }
}
