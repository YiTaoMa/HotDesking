package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SelectBookingToManageEmpModel {
    Connection connection;
    ObservableList<String> items = FXCollections.observableArrayList();
    public SelectBookingToManageEmpModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public ObservableList<String> getEmployeeBookingDetail(int employeeId) throws SQLException {
        String query = "select employee_id,date,seat_id from Booking where employee_id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,employeeId);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                items.add("Employee ID---"+resultSet.getString("employee_id")+"---Booked in---"+resultSet.getString("date")+"---At Seat Number---"+resultSet.getString("seat_id"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return items;
    }
}
