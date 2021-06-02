package test.model;

import main.model.ResetPassPopIDModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResetPassPopIDModelTest {
    private static ResetPassPopIDModel resetPassPopIDModel;

    @BeforeAll
    public static void initialise() {
        resetPassPopIDModel = new ResetPassPopIDModel();
    }

    @Test
    void getSecretQuestion_CorrectString_IfEmpIDCorrect() {
        assertAll(() -> assertEquals("What was your first pet?", resetPassPopIDModel.getSecretQuestion(77777),
                "Employee ID 77777 expected secret question 'What was your first pet?'"));
    }

    @Test
    void getSecretQuestion_CorrectString_IfEmpIDInCorrect() {
        assertAll(() -> assertEquals("not found", resetPassPopIDModel.getSecretQuestion(00000),
                "Employee ID 0000 expected string 'not found'"));
    }

    @Test
    void isIdExistAndNotDeactivated_True_IfEmpIDExist() {
        assertAll(() -> assertEquals(true, resetPassPopIDModel.isIdExistAndNotDeactivated(77777),
                "Employee ID 77777 exist, expected return true"));
    }

    @Test
    void isIdExist_False_IfEmpIDNotExist() {
        assertAll(() -> assertEquals(false, resetPassPopIDModel.isIdExistAndNotDeactivated(00000),
                "Employee ID 00000 expected return false"));
    }

    @Test
    void isIdExistAndNotDeactivated_false_IfEmpIDExistButDeactivated() {
        assertAll(() -> assertEquals(false, resetPassPopIDModel.isIdExistAndNotDeactivated(88888),
                "Employee ID 88888 exist but deactivated, expected return false"));
    }
}