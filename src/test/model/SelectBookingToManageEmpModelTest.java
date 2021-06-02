package test.model;

import main.model.SelectBookingToManageEmpModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectBookingToManageEmpModelTest {
    private static SelectBookingToManageEmpModel selectBookingToManageEmpModel;

    @BeforeAll
    public static void initialise() {
        selectBookingToManageEmpModel = new SelectBookingToManageEmpModel();
    }

    @Test
    void getEmployeeBookingDetail_False_IfEmployeeHaveBookings() {
        assertAll(() -> assertEquals(false, selectBookingToManageEmpModel.getEmployeeBookingDetail(77777).isEmpty(),
                "ID 77777 have booking record in database, not empty, so expected return false."));
    }
}