package test.model;

import main.model.BookingModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingModelTest {
    private static BookingModel bookingModel;

    @BeforeAll
    public static void initialise() {
        bookingModel = new BookingModel();
    }

    @Test
    void isAlreadyBookedInSelectedDate_True_IfThisEmployeeHaveBookingInThatDate() {
        assertAll(() -> assertEquals(true, bookingModel.isAlreadyBookedInSelectedDate("2021-05-20", 77777),
                "Employee id 77777 with date 2021-05-20 already exist in database, expected true"));
    }

    @Test
    void isAlreadyBookedInSelectedDate_False_IfThisEmployeeDoesntHaveBookingInThatDate() {
        assertAll(() -> assertEquals(false, bookingModel.isAlreadyBookedInSelectedDate("2021-05-31", 77777),
                "Employee id 77777 with date 2021-05-31 record not in database, expected false"));
    }

    @Test
    void getSeatIdLockedByAdmin_False_IfHaveLockedDownSeatsInDatabase() {
        // note: if change lock down seat in the Seat table in database: If no lock down seat
        // this test will fail.
        assertAll(() -> assertEquals(false, bookingModel.getSeatIdLockedByAdmin().isEmpty(),
                "we have locked down seat in database so return should not be empty, expected false"));
    }

    @Test
    void getSeatIDBookedByOther_False_IfHaveBookingsInThatDate() {
        assertAll(() -> assertEquals(false, bookingModel.getSeatIDBookedByOther("2021-05-20").isEmpty(),
                "we have booking in 2021-05-20 in database so return should not be empty, expected false"));
    }

    @Test
    void getSeatIDBookedByOther_True_IfDontHaveBookingsInThatDate() {
        assertAll(() -> assertEquals(true, bookingModel.getSeatIDBookedByOther("2021-05-10").isEmpty(),
                "we don't have booking in 2021-05-10 in database so return should be empty, expected true"));
    }

    @Test
    void getSeatIdBookedByUserPrevious_False_IfThisEmployeeWithDateExistInWhitelist() {
        assertAll(() -> assertEquals(false, bookingModel.getSeatIdBookedByUserPrevious(77777, "2021-05-21").isEmpty(),
                "we have ID 77777 can not booking in 2021-05-21 in database so return should not be empty, expected false"));
    }

    @Test
    void getSeatIdBookedByUserPrevious_True_IfThisEmployeeWithDateNotExistInWhitelist() {
        assertAll(() -> assertEquals(true, bookingModel.getSeatIdBookedByUserPrevious(77777, "2021-05-10").isEmpty(),
                "we don't have ID 77777 can not booking in 2021-05-10 record in database so return should be empty, expected true"));
    }
}