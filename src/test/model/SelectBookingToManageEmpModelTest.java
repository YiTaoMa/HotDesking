package test.model;

import main.model.SelectBookingToManageEmpModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SelectBookingToManageEmpModelTest {
    private static SelectBookingToManageEmpModel selectBookingToManageEmpModel;

    @BeforeAll
    public static void initialise() {
        selectBookingToManageEmpModel = new SelectBookingToManageEmpModel();
    }

    @Test
    void getEmployeeBookingDetail() throws SQLException {
        assertEquals(false, selectBookingToManageEmpModel.getEmployeeBookingDetail(23443).isEmpty(),
                "ID 23443 have booking record in database, not empty, so expected return false.");
    }

    @Test
    void getEmployeeBookingDetailWithIncorrectID() throws SQLException {
        assertEquals(true, selectBookingToManageEmpModel.getEmployeeBookingDetail(00000).isEmpty(),
                "ID 00000 don't have booking record in database, empty, so expected return true.");
    }
}