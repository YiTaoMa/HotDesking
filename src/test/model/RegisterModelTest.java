package test.model;

import main.DBUtils;
import main.SQLConnection;
import main.model.RegisterModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class RegisterModelTest {
    private static RegisterModel registerModel;

    @BeforeAll
    public static void initialise() {
        registerModel = new RegisterModel();
    }

    @Test
    void isRegister_False_IfEmpIDOrUsernameExist() {
        assertAll(() -> assertEquals(false, registerModel.isRegister("test", "test", 23444, "sdf", "sdf", "Employee", "What was your childhood nickname?", "wer"),
                "username and ID already exist in database expected false"));
    }

    @Test
    void isRegister_True_IfEmpIDOrUsernameNotExist() {
        assertAll(() -> assertEquals(true, registerModel.isRegister("Model-Test", "model-test", 11111, "Model-TestF", "Model-TestL", "Employee", "What was your childhood nickname?", "Model-test"),
                "username and ID not exist in database expected true"));
    }

    /**
     * After we insert the new employee into the database in order to do the test, we delete this record after the test
     */
    @AfterAll
    public static void deleteRegisteredUP() {
        Connection connection;
        connection = SQLConnection.connect();
        PreparedStatement prst = null;
        int id = 11111;
        String username = "Model-Test";
        String password = "model-test";
        String query = "delete from Employee where id=? and username=? and password=?";
        try {
            prst = connection.prepareStatement(query);
            prst.setInt(1, id);
            prst.setString(2, username);
            prst.setString(3, password);
            prst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closePrepareStatement(prst);
            DBUtils.closeConnection(connection);
        }
    }
}