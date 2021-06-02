package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminDeleteAccountPromptModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminDeleteAccountPromptModelTest {
    private static AdminDeleteAccountPromptModel adminDeleteAccountPromptModel;

    @BeforeAll
    public static void initialise() {
        adminDeleteAccountPromptModel = new AdminDeleteAccountPromptModel();
    }

    @Test
    void deleteSelectedEmployeeAccount_True_IfDeleteSuccess() {
        assertAll(() -> assertEquals(true, adminDeleteAccountPromptModel.deleteSelectedEmployeeAccount(88888),
                "Delete success expected return true."));
    }

    @Test
    void deleteSelectedEmpBookingRecords_True_IfDeleteSuccess() {
        assertAll(() -> assertEquals(true, adminDeleteAccountPromptModel.deleteSelectedEmpBookingRecords(88888),
                "Delete success expected return true."));
    }

    @Test
    void deleteSelectedEmpWhitelistRecords_True_IfDeleteSuccess() {
        assertAll(() -> assertEquals(true, adminDeleteAccountPromptModel.deleteSelectedEmpWhitelistRecords(88888),
                "Delete success expected return true."));
    }

    @Test
    void isSelectedEmpHaveBookings_True_EmpId77777HaveBookings() {
        assertAll(() -> assertEquals(true, adminDeleteAccountPromptModel.isSelectedEmpHaveBookings(77777),
                "Employee id 77777 have bookings expected return true."));
    }

    @Test
    void isSelectedEmpHaveBookings_False_EmpId25683DontHaveBookings() {
        assertAll(() -> assertEquals(false, adminDeleteAccountPromptModel.isSelectedEmpHaveBookings(25683),
                "Employee id 25683 have bookings expected return false."));
    }

    @AfterAll
    public static void addDeletedAccountBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        try {
            int id = 88888;
            String firstname = "sdfsdfsd";
            String lastname = "fsafsdfs";
            String role = "Employee";
            String username = "testtest";
            String password = "testtest";
            String secretQ = "What was your first pet?";
            String answerForSecretQ = "setsetestst";

            String sql = "insert into Employee (id,first_name,last_name,character_role,username,password,secret_question,answer_for_secret_question,is_deactivated) values(?,?,?,?,?,?,?,?,?)"; // SQL statement

            prst = connection.prepareStatement(sql); // PS to SQL statement
            prst.setInt(1, id);
            prst.setString(2, firstname);
            prst.setString(3, lastname);
            prst.setString(4, role);
            prst.setString(5, username);
            prst.setString(6, password);
            prst.setString(7, secretQ);
            prst.setString(8, answerForSecretQ);
            prst.setInt(9, 1);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    @AfterAll
    public static void addDeletedBookingRecordBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String sql = "insert into Booking (number,employee_id,date,seat_id,is_booked,has_confirmed,is_checked_in) values(?,?,?,?,?,?,?)"; // SQL statement
        try {
            prst = connection.prepareStatement(sql); // PS to SQL statement
            prst.setInt(1, 52);
            prst.setInt(2, 88888);
            prst.setString(3, "2056-06-17");
            prst.setInt(4, 1);
            prst.setInt(5, 1);
            prst.setInt(6, 1);
            prst.setInt(7, 1);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    @AfterAll
    public static void addDeletedWhitelistRecordBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String sql = "insert into Whitelist (id,employee_id,seat_id,date,is_locked) values(?,?,?,?,?)"; // SQL statement
        try {
            prst = connection.prepareStatement(sql); // PS to SQL statement
            prst.setInt(1, 49);
            prst.setInt(2, 88888);
            prst.setInt(3, 1);
            prst.setString(4, "2056-06-18");
            prst.setInt(5, 1);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}