package main.model;

import main.DBUtils;
import main.SQLConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.Map.Entry;

public class HomeModel {
    HashMap<Integer, String> datesAndNumberFromBookingNotConfirmed = new HashMap<>();
    HashMap<Integer, String> bookingsOutOfRange = new HashMap<>();
    LinkedList<Integer> bookingsOutOfRangeEmpId = new LinkedList<>();
    LinkedList<String> bookingsOutOfRangeDate = new LinkedList<>();

    public void getBookingNumberAndDatesNotConfirmed() { // as we need to get unconfirmed bookings first then check date and time
        datesAndNumberFromBookingNotConfirmed.clear();
        Connection connection;
        connection = SQLConnection.connect();
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "select number,date from Booking where has_confirmed=false";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) { // for each result we put key and their value to hash map
                datesAndNumberFromBookingNotConfirmed.put(resultSet.getInt("number"), resultSet.getString("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(connection);
        }
    }

    public HashMap<Integer, String> getBookingsOutOfRanges() throws ParseException {
        // call this method directly to assign un confirmed booking to the hash map
        getBookingNumberAndDatesNotConfirmed();
        bookingsOutOfRange.clear();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date nowTime = new Date(); // today
        Date bookingTime; // target booking date
        boolean effectiveDate;
        for (Entry<Integer, String> entry : datesAndNumberFromBookingNotConfirmed.entrySet()) { // for each un confirmed booking
            bookingTime = ft.parse(entry.getValue());// get target booking date which is the value of each entry/booking
            effectiveDate = isEffectiveDate(nowTime, bookingTime);// we pass today and target date to compare,and get the result
            if (!effectiveDate) { // if the date is out of ranges
                bookingsOutOfRange.put(entry.getKey(), entry.getValue()); // we put those bookings into this hash map as the key
                // is booking number and value is date
            }
        }
        return bookingsOutOfRange;
    }

    public boolean isEffectiveDate(Date nowTime, Date bookingTime) { // check if date is valid

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(nowTime);

        Calendar bookingDate = Calendar.getInstance();
        bookingDate.setTime(bookingTime);
        // if today is before the target date/booking date in the database, which means before today's 12:00pm right before tomorrow
        // we can still confirm it. if we pass today's 12:00pm which is actually tomorrow, then today becomes tomorrow and will fail
        // which will return false
        // Also, if we have date less than today, which today is not before let's say last date, it still return false.
        if (todayCalendar.before(bookingDate)) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteOutOfRangeBookings(int bookingNumber) { // delete bookings which is out of ranges through booking number
        Connection connection;
        connection = SQLConnection.connect();
        String query = "delete from Booking where number=?";
        PreparedStatement prst = null;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
            prst.setInt(1, bookingNumber);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    public void assignOutOfRangeEmpIDDateToCollection(int bookingNumber) { // get corresponding emp ID and date for those bookings
        //out of ranges
        Connection connection;
        connection = SQLConnection.connect();
        String query = "select employee_id,date from Booking where number=?"; // we through number to get employee id and date
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingNumber);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) { // assign each result to corresponding emp ID linked list and date linked list
                bookingsOutOfRangeEmpId.add(resultSet.getInt("employee_id"));
                bookingsOutOfRangeDate.add(resultSet.getString("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closePrepareStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }

    public void deleteOutOfRangeWhitelistRecord(int empId, String date) { // delete corresponding whitelist record
        Connection connection;
        connection = SQLConnection.connect();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateConverted = LocalDate.parse(date, formatter);
        dateConverted = dateConverted.plusDays(1);//add one day if it is whitelist
        String dateString = dateConverted.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "delete from Whitelist where employee_id=? and date=?";
        PreparedStatement prst = null;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
            prst.setInt(1, empId);
            prst.setString(2, dateString);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    public void clearEmpIdDateCollection() { // clear everything in these 2 linked list
        bookingsOutOfRangeEmpId.clear();
        bookingsOutOfRangeDate.clear();
    }

    public LinkedList<Integer> getBookingsOutOfRangeEmpIdCollection() { // get this linked list
        return bookingsOutOfRangeEmpId;
    }

    public LinkedList<String> getBookingsOutOfRangeDateCollection() { // get this linked list
        return bookingsOutOfRangeDate;
    }
}
