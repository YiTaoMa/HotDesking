package test.model;

import main.model.HomeModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HomeModelTest {
    private static HomeModel homeModel;
    private static Date today;
    private static SimpleDateFormat ft;
    private static Date bookingTime;
    private static Date featureBookingTime;

    @BeforeAll
    public static void initialise() {
        // This class only can test 1 method as this is time validation framework, it will change through the time
        // and most is void method
        homeModel = new HomeModel();
        today = new Date();
        ft = new SimpleDateFormat("yyyy-MM-dd");
        assertAll(() -> bookingTime = ft.parse("2021-05-28"));
        assertAll(() -> featureBookingTime = ft.parse("2052-05-28"));
    }

    @Test
    void isEffectiveDate_False_IfTodayNotBeforeBookingDate() {
        assertAll(() -> assertEquals(false, homeModel.isEffectiveDate(today, bookingTime),
                "Today is not before \"2021-05-28\" expect false"));
    }

    @Test
    void isEffectiveDate_True_IfTodayBeforeBookingDate() {
        assertAll(() -> assertEquals(true, homeModel.isEffectiveDate(today, featureBookingTime),
                "Today is before \"2052-05-28\" expect true"));
    }
}