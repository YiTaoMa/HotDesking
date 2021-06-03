package test.model;

import main.model.AdminManageBookingModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminManageBookingModelTest {
    private static AdminManageBookingModel adminManageBookingModel;

    @BeforeAll
    public static void initialise() {
        adminManageBookingModel = new AdminManageBookingModel();
    }

    @Test
    void getSeatIdLockedByAdmin_False_IfWeHaveSeatLockedDown() {
        assertAll(() -> assertEquals(false, adminManageBookingModel.getSeatIdLockedByAdmin().isEmpty(),
                "We have seat locked, return should not be empty."));
    }

    @Test
    void getSeatIDBookedByOther_False_IfWeHaveSeatBookedInThatDate() {
        assertAll(() -> assertEquals(false, adminManageBookingModel.getSeatIDBookedByOther("2021-05-25").isEmpty(),
                "We have seat booked in 2021-05-25, return should not be empty."));
    }
}