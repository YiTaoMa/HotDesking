package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.ResetPasswordModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

class ResetPasswordModelTest {
    private static ResetPasswordModel resetPasswordModel;

    @BeforeAll
    public static void initialise() {
        resetPasswordModel = new ResetPasswordModel();
    }

    @Test
    void isAnswerForSQCorrect_True_IfSQAnswerIsCorrect() {
        assertAll(() -> assertEquals(true, resetPasswordModel.isAnswerForSQCorrect(77777, "wish"),
                "Employee ID 77777 with Answer for SQ 'wish' expected return true"));
    }

    @Test
    void isAnswerForSQCorrect_False_IfSQAnswerIsInCorrect() {
        assertAll(() -> assertEquals(false, resetPasswordModel.isAnswerForSQCorrect(77777, "vsdnfiusdnvw"),
                "Employee ID 7777 with Answer for SQ 'vsdnfiusdnvw' expected return false"));
    }

    @Test
    void generateRandomPassword_True_IfReturnTypeIsString() {
        assertEquals(true, resetPasswordModel.generateRandomPassword(10) instanceof String,
                "generate random password expected return is a type of String");
    }

    @Test
    void generateRandomPassword_10_IfReturnedAString() {
        assertEquals(10, resetPasswordModel.generateRandomPassword(10).length(),
                "generate random password length input 10 expected length return is 10");
    }

    @Test
    void updatePassword_True_IfUpdateSuccessForThisEmployee() {
        assertAll(() -> assertEquals(true, resetPasswordModel.updatePassword(77777, "model-testNewP"),
                "id 77777 exist in database expected return true"));
    }

    /**
     * As we update the id 77777 to a new password, after down the test, update back to original password
     */
    @AfterAll
    public static void updateTestIDBackToOriginal() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        int id = 77777;
        String password = "model-testP";
        String query = "update Employee set password=? where id=?";
        try {
            prst = connection.prepareStatement(query);
            prst.setString(1, password);
            prst.setInt(2, id);
            prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}