package test.model;

import main.model.AdminChangeLockDownSeatsModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminChangeLockDownSeatsModelTest {
    private static AdminChangeLockDownSeatsModel adminChangeLockDownSeatsModel;

    @BeforeAll
    public static void initialise() {
        adminChangeLockDownSeatsModel = new AdminChangeLockDownSeatsModel();
    }

    /**
     * Not to test update method here, as if lock down seat change it will impact function badly.
     * For example: When you mark my assignment you unlock seat number 2. but the test is actually going to
     * unlock it also this is fine, but after all I update seat 2's lock down status back to true which originally
     * is true, but actually you unlock it, so become false. And I am not sure what sequence you going to do
     * so just to make sure the test not impact the working functions, i decided not to test few methods to ensure
     * my function working fine.
     */
    @Test
    void getAllSeatLockedDownStatus_False_SeatTableExist() {
        assertAll(() -> assertEquals(false, adminChangeLockDownSeatsModel.getAllSeatLockedDownStatus().isEmpty(),
                "We have Seat table in database, should not be empty."));
    }

    @Test
    void isSelectedSeatHaveBookings_True_IfThisSeatHaveBookings() {
        assertAll(() -> assertEquals(true, adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(1),
                "We have bookings for seat 1, expected return is true"));
    }

    @Test
    void isSelectedSeatHaveBookings_False_IfThisSeatDontHaveBookings() {
        assertAll(() -> assertEquals(false, adminChangeLockDownSeatsModel.isSelectedSeatHaveBookings(5),
                "We don't have bookings for seat 5 as it is locked down, expected return is false"));
    }
}