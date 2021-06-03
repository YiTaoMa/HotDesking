package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.AdminUnDeactivateAccountPromptModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class AdminUnDeactivateAccountPromptModelTest {
    private static AdminUnDeactivateAccountPromptModel adminUnDeactivateAccountPromptModel;

    @BeforeAll
    public static void initialise() {
        adminUnDeactivateAccountPromptModel = new AdminUnDeactivateAccountPromptModel();
    }

    /**
     * As we already prevent user to un deactivate a un deactivated account in the controller, so no need to do test here.
     * As this methods is invoked only if the account is deactivated.
     */
    @Test
    void updateDeactivateStatusToFalse_True_IfUpdateSuccess() {
        assertAll(() -> assertEquals(true, adminUnDeactivateAccountPromptModel.updateDeactivateStatusToFalse(88888),
                "This employee 88888 is deactivate so we can un deactivate it."));
    }

    @AfterAll
    public static void updateDeactivateStatusBack() {
        Connection connection;
        connection = SQLConnection.connect();
        String query = "update Employee set is_deactivated=true where id=?";
        PreparedStatement prst = null;
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, 88888);
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}