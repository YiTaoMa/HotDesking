package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminUnlockSeatPromptModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminUnlockSeatPromptModelTest {
    private static AdminUnlockSeatPromptModel adminUnlockSeatPromptModel;

    @BeforeAll
    public static void initialise() {
        adminUnlockSeatPromptModel = new AdminUnlockSeatPromptModel();
    }

    @Test
    void updateSeatIdLockedDownToUnlock_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminUnlockSeatPromptModel.updateSeatIdLockedDownToUnlock(2),
                "Seat 2 is locked we can unlock it, expected true."));
    }

    @AfterAll
    public static void updateSeatLockStatusBack() {
        Connection connection;
        connection = SQLConnection.connect();
        String query = "update Seat set is_locked=true where id=?";
        PreparedStatement prst = null;
        try {
            prst = connection.prepareStatement(query); // PS to SQL statement
            prst.setInt(1, 2);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}