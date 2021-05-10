package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

public class ConfirmBookingModel {
    Connection connection;

    public ConfirmBookingModel() {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public boolean insertBookingRecord(int employeeId, String date, int seatID) throws SQLException {
        PreparedStatement prst = null;
        //ResultSet resultSet = null;
        boolean result = false;
        String sql = "insert into Booking (employee_id,date,seat_id,is_booked,has_confirmed,is_checked_in) values(?,?,?,?,?,?)"; // SQL statement
        try {
            prst = connection.prepareStatement(sql); // PS to SQL statement
            prst.setInt(1, employeeId);
            prst.setString(2, date);
            prst.setInt(3, seatID);
            prst.setInt(4, 1);//is booked is true
            prst.setInt(5, 0);//has confirmed is false
            prst.setInt(6, 0);//is checked in is false

            result = prst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            prst.close();
            //resultSet.close();
        }
        return result;
    }
    public boolean insertToWhitelist(int employeeId, int seatId, String date) throws SQLException {
        //in the whitelist date is plus 1 so need to convert string date to date then plus a day then convert back to String

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateConverted = LocalDate.parse(date, formatter);
        dateConverted = dateConverted.plusDays(1);//add one day
        String dateString = dateConverted.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // after added 1 day convert back to string

        PreparedStatement prst = null;
        //ResultSet resultSet = null;
        boolean result = false;
        String sql = "insert into Whitelist (employee_id,seat_id,date,is_locked) values(?,?,?,?)"; // SQL statement
        try {
            prst = connection.prepareStatement(sql); // PS to SQL statement
            prst.setInt(1, employeeId);
            prst.setInt(2, seatId);
            prst.setString(3, dateString);
            prst.setInt(4, 1);//is locked default true

            result = prst.executeUpdate() > 0;
            //prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            prst.close();
            //resultSet.close();
        }
        return result;
    }
}
