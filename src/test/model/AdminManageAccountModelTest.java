package test.model;

import main.model.AdminManageAccountModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminManageAccountModelTest {
    private static AdminManageAccountModel adminManageAccountModel;

    @BeforeAll
    public static void initialise() {
        adminManageAccountModel = new AdminManageAccountModel();
    }

    @Test
    void getAllAccountsDetail_False_IfWeHaveAccount() {
        assertAll(() -> assertEquals(false, adminManageAccountModel.getAllAccountsDetail().isEmpty(),
                "We have account in the Account table in the database, returned list should not be empty."));
    }
}