package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminDeactivateAccountPromptModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminDeactivateAccountPromptModelTest {
    private static AdminDeactivateAccountPromptModel adminDeactivateAccountPromptModel;

    @BeforeAll
    public static void initialise() {
        adminDeactivateAccountPromptModel = new AdminDeactivateAccountPromptModel();
    }

    /**
     * As to check if this account is already deactivated is done in the controller so no need to check here as here's account
     * is always not deactivated.
     */
    @Test
    void updateDeactivateStatus_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminDeactivateAccountPromptModel.updateDeactivateStatus(77777),
                "Update successfully expected true"));
    }

    @AfterAll
    public static void updateIsDeactivatedStatusBack() {
        Connection connection;
        connection = SQLConnection.connect();
        int empId = 77777;
        String query = "update Employee set is_deactivated=false where id=?";
        PreparedStatement prst = null;
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, empId);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}