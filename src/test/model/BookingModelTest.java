package test.model;

import main.model.BookingModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BookingModelTest {
    private static BookingModel bookingModel;

    @BeforeAll
    public static void initialise() {
        bookingModel = new BookingModel();
    }

    @Test
    void isAlreadyBookedInSelectedDateWithBooked() throws SQLException {
        assertEquals(true, bookingModel.isAlreadyBookedInSelectedDate("2021-05-12", 23443),
                "Employee id 23443 with date 2021-05-12 already exist in database, expected true");
    }

    @Test
    void isAlreadyBookedInSelectedDateWithUnBooked() throws SQLException {
        assertEquals(false, bookingModel.isAlreadyBookedInSelectedDate("2021-05-31", 23443),
                "Employee id 23443 with date 2021-05-31 record not in database, expected false");
    }

    @Test
    void getSeatIdLockedByAdmin() throws SQLException {
        assertEquals(false, bookingModel.getSeatIdLockedByAdmin().isEmpty(),
                "we have locked down seat in database so return should not be empty, expected false");
    }

    @Test
    void getSeatIDBookedByOther() throws SQLException {
        assertEquals(false, bookingModel.getSeatIDBookedByOther("2021-05-13").isEmpty(),
                "we have booking in 2021-05-13 in database so return should not be empty, expected false");
    }

    @Test
    void getSeatIDBookedByOtherEmpty() throws SQLException {
        assertEquals(true, bookingModel.getSeatIDBookedByOther("2021-05-31").isEmpty(),
                "we don't have booking in 2021-05-31 in database so return should be empty, expected true");
    }

    @Test
    void getSeatIdBookedByUserPrevious() throws SQLException {
        assertEquals(false, bookingModel.getSeatIdBookedByUserPrevious(23443, "2021-05-13").isEmpty(),
                "we have ID 23443 can not booking in 2021-05-13 in database so return should not be empty, expected false");
    }

    @Test
    void getSeatIdBookedByUserPreviousEmpty() throws SQLException {
        assertEquals(true, bookingModel.getSeatIdBookedByUserPrevious(23443, "2021-05-31").isEmpty(),
                "we don't have ID 23443 can not booking in 2021-05-31 record in database so return should be empty, expected true");
    }


}