package test.model;

import main.model.LoginModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginModelTest {
    private static LoginModel loginModel;

    @BeforeAll
    public static void initialise() {
        loginModel = new LoginModel();
    }

    @Test
    void isLogin_True_IfUsernamePasswordCorrectAndNotDeactivated() {
        assertAll(() -> assertEquals(true, loginModel.isLogin("test", "test"),
                "username and password equals test expected true"));
    }

    @Test
    void isLogin_False_IfUsernamePasswordInCorrect() {
        assertAll(() -> assertEquals(false, loginModel.isLogin("dftpoandfff", "dfpqdsgsass"),
                "username and password not match in database expected false"));
    }

    @Test
    void isLogin_False_IfUsernameRelatedAccountIsDeactivated() {
        assertAll(() -> assertEquals(false, loginModel.isLogin("testtest", "testtest"),
                "This account is deactivated expected false"));
    }

    @Test
    void getRole_Admin_IfUsernamePasswordIsRoleOfAdmin() {
        assertAll(() -> assertEquals("Admin", loginModel.getRole("admin", "admin"),
                "Admin username and password equals admin expected String Admin"));
    }

    @Test
    void getRole_Employee_IfUsernamePasswordIsRoleOfEmployee() {
        assertAll(() -> assertEquals("Employee", loginModel.getRole("test", "test"),
                "username and password equals test expected String Employee"));
    }

    @Test
    void getRole_NotFound_IfUsernamePasswordInCorrect() {
        assertAll(() -> assertEquals("not found", loginModel.getRole("dvdfbersd", "xfvgrhrthsss"),
                "username and password not match in database expected String not found"));
    }

    @Test
    void getEmployeeId_23443_IfUsernamePasswordIsTest() {
        assertAll(() -> assertEquals(23443, loginModel.getEmployeeId("test", "test"),
                "username and password is 'test' expected Employee Id 23443"));
    }

    @Test
    void getEmployeeId_0_IfUsernamePasswordInCorrect() {
        assertAll(() -> assertEquals(0, loginModel.getEmployeeId("sdfsvdfbdfb", "sdfdsfsdsd"),
                "username and password not match in database expected 0"));
    }
}