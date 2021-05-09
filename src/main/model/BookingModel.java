package main.model;

import main.SQLConnection;

import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;

public class BookingModel {
    Connection connection;
LinkedList<Integer> seatsBookedByOther = new LinkedList<>();
    LinkedList<Integer> seatsLockedByAdmin = new LinkedList<>();
    LinkedList<Integer> seatsBookedByUserPrevious = new LinkedList<>();
    public BookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }
    public LinkedList getSeatIdLockedByAdmin() throws SQLException {
        //PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet=null;
        String query = "select id from Seat where is_locked=true";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                seatsLockedByAdmin.add(resultSet.getInt("id"));
            }
        }
        catch (Exception e)
        {
         e.printStackTrace();
        }finally {
            statement.close();
            resultSet.close();
        }
        return seatsLockedByAdmin;
    }
    public LinkedList getSeatIDBookedByOther(String date) throws SQLException { //the date is what user chosed at the start
        String query = "select seat_id from Booking where date=? and is_booked=true";//that date which seat has been booked
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,date);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                //System.out.println(resultSet.getInt("seat_id"));
                seatsBookedByOther.add(resultSet.getInt("seat_id"));
            }
            //else{
            //    return 0;
            //}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return seatsBookedByOther;
    }
     public LinkedList getSeatIdBookedByUserPrevious(int id, String date) throws SQLException {//select hte seat that user booked previous
        //so can not book again
         String query = "select seat_id from Whitelist where employee_id=? and is_locked=true and date=?";
         PreparedStatement preparedStatement = null;
         ResultSet resultSet=null;
         try {
             preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1,id);
             preparedStatement.setString(2,date);
             resultSet = preparedStatement.executeQuery();
             while(resultSet.next()) {
                 //System.out.println(resultSet.getInt("seat_id"));
                seatsBookedByUserPrevious.add(resultSet.getInt("seat_id"));
             }

         }
         catch (Exception e)
         {
             e.printStackTrace();
         }finally {
             preparedStatement.close();
             resultSet.close();
         }
         return seatsBookedByUserPrevious;
     }
}
