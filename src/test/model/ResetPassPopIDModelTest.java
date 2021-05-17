package test.model;

import main.model.ResetPassPopIDModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ResetPassPopIDModelTest {
    private static ResetPassPopIDModel resetPassPopIDModel;

    @BeforeAll
    public static void initialise() {
        resetPassPopIDModel = new ResetPassPopIDModel();
    }

    @Test
    void getSecretQuestionWithCorrectID() throws SQLException {
        assertEquals("What was your childhood nickname?", resetPassPopIDModel.getSecretQuestion(23443),
                "Employee ID 23443 expected secret question 'What was your childhood nickname?'");
    }

    @Test
    void getSecretQuestionWithIncorrectID() throws SQLException {
        assertEquals("not found", resetPassPopIDModel.getSecretQuestion(00000),
                "Employee ID 0000 expected string 'not found'");
    }

    @Test
    void isIdExistWithExistID() throws SQLException {
        assertEquals(true, resetPassPopIDModel.isIdExist(23443),
                "Employee ID 23443 expected return true");
    }

    @Test
    void isIdExistWithIncorrectID() throws SQLException {
        assertEquals(false, resetPassPopIDModel.isIdExist(00000),
                "Employee ID 00000 expected return false");
    }
}