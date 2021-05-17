package test.model;

import main.model.LoginModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginModelTest {
    private static LoginModel loginModel;

    @BeforeAll
    public static void initialise() {
        loginModel = new LoginModel();
    }

    @Test
    void isLoginWithCorrectUP() throws SQLException {
        assertEquals(true, loginModel.isLogin("test", "test"),
                "username and password equals test expected true");
    }

    @Test
    void isLoginWithIncorrectUP() throws SQLException {
        assertEquals(false, loginModel.isLogin("dftpoand", "dfpqdsgs"),
                "username and password not match in database expected false");
    }

    @Test
    void getRoleAdmin() throws SQLException {
        assertEquals("Admin", loginModel.getRole("admin", "admin"),
                "Admin username and password equals admin expected String Admin");
    }

    @Test
    void getRoleEmployee() throws SQLException {
        assertEquals("Employee", loginModel.getRole("test", "test"),
                "username and password equals test expected String Employee");
    }

    @Test
    void getRoleNotFound() throws SQLException {
        assertEquals("not found", loginModel.getRole("dvdfbersd", "xfvgrhrthsss"),
                "username and password not match in database expected String not found");
    }

    @Test
    void getEmployeeIdWithCorrectID() throws SQLException {
        assertEquals(23443, loginModel.getEmployeeId("test", "test"),
                "username and password is 'test' expected Employee Id 23443");
    }

    @Test
    void getEmployeeIdWithIncorrectID() throws SQLException {
        assertEquals(0, loginModel.getEmployeeId("sdfsvdfbdfb", "sdfdsfsdsd"),
                "username and password not match in database expected 0");
    }
}