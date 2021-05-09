package test;

import main.model.LoginModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginModelTest {
//private LoginModel loginModel;
    //@BeforeAll
    //void initialise() {
    //    loginModel=new LoginModel();
    //}
    //@Test
    //void isDbConnected() {
    //   //assertEquals(true,true);
    //}

    @Test
    void isLogin() throws SQLException {
        LoginModel loginModel = new LoginModel();
        assertEquals(true, loginModel.isLogin("test","test"),
                "Login failed, username and password not match in the database");
    }

    @Test
    void getRole() throws SQLException {
        LoginModel loginModel = new LoginModel();
        assertEquals("Admin", loginModel.getRole("admin","admin"),
                "This employee's role should be Admin");
    }
}