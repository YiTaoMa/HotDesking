package test.model;

import main.model.AdminSelectBookingToManageModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminSelectBookingToManageModelTest {
    private static AdminSelectBookingToManageModel adminSelectBookingToManageModel;

    @BeforeAll
    public static void initialise() {
        adminSelectBookingToManageModel = new AdminSelectBookingToManageModel();
    }

    @Test
    void getAllBookingDetail_False_IfWeHaveBookings() {
        assertAll(() -> assertEquals(false, adminSelectBookingToManageModel.getAllBookingDetail().isEmpty(),
                "We have booking records, return should not be empty, expected false."));
    }
}