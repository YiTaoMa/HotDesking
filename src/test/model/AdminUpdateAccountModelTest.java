package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminUpdateAccountModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminUpdateAccountModelTest {
    private static AdminUpdateAccountModel adminUpdateAccountModel;

    @BeforeAll
    public static void initialise() {
        adminUpdateAccountModel = new AdminUpdateAccountModel();
    }

    @Test
    void isIdUnRegistered_False_IfEmpIdAlreadyRegistered() {
        assertAll(() -> assertEquals(false, adminUpdateAccountModel.isIdUnRegistered(88888, 77777),
                "Current we are 77777 and want to update id to 88888, but already exist in the database, expected false"));
    }

    @Test
    void isIdUnRegistered_True_IfEmpIdNotRegistered() {
        assertAll(() -> assertEquals(true, adminUpdateAccountModel.isIdUnRegistered(45454, 77777),
                "Current we are 77777 and want to update id to 45454, not exist in the database, expected true, we can update it to 45454."));
    }

    @Test
    void isUsernameUnRegistered_False_IfUsernameAlreadyRegistered() {
        assertAll(() -> assertEquals(false, adminUpdateAccountModel.isUsernameUnRegistered("MODEL-TESTU", "test"),
                "Current we are 77777 with username MODEL-TESTU, we update it to 'test'  but this username already exist in the database, expected false"));
    }

    @Test
    void isUsernameUnRegistered_True_IfUsernameNotRegistered() {
        assertAll(() -> assertEquals(true, adminUpdateAccountModel.isUsernameUnRegistered("MODEL-TESTU", "MODEL-TESTUTwoooo"),
                "Current we are 77777 with username MODEL-TESTU, we update it to 'MODEL-TESTUTwoooo' this username not exist in the database, expected true"));
    }

    @Test
    void updateAccount_True_IfUpdateSuccess() {
        // note we changed id to 12345 from 77777 and username to MODEL-TESTUTwo
        assertAll(() -> assertEquals(true, adminUpdateAccountModel.updateAccount(12345, 77777, "Model-TestFN", "Model-TestLN", "Employee", "MODEL-TESTUTwo", "model-testP", "What was your first pet?", "wish"),
                "We can update it, update success expected true"));
    }

    @Test
    void updateBookingEmpId_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminUpdateAccountModel.updateBookingEmpId(77777, 12345),
                "We can update it, update success expected true"));
    }

    @Test
    void updateWhitelistEmpId_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminUpdateAccountModel.updateWhitelistEmpId(77777, 12345),
                "We can update it, update success expected true"));
    }

    @AfterAll
    public static void updateAccountBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String sqlUpdate = "update Employee set id=?,username=? where id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setInt(1, 77777);
            prst.setString(2, "MODEL-TESTU");
            prst.setInt(3, 12345);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    @AfterAll
    public static void updateBookingRecordBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String sqlUpdate = "update Booking set employee_id=? where employee_id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setInt(1, 77777);
            prst.setInt(2, 12345);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }

    @AfterAll
    public static void updateWhitelistRecordBack() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        String sqlUpdate = "update Whitelist set employee_id=? where employee_id=?";
        try {
            prst = connection.prepareStatement(sqlUpdate);
            prst.setInt(1, 77777);
            prst.setInt(2, 12345);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}