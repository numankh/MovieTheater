package com.jpmc.theater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTests {

    Double testTicketPrice;
    LocalDateTime testShowStartTime;
    Customer testCustomer;
    Showing testShowing;

    @BeforeEach
    void setUp() {
        testTicketPrice = 12.5;
        testShowStartTime = LocalDateTime.of(2022, Month.APRIL, 24, 10, 0);
        testCustomer = new Customer("John Doe", UUID.randomUUID().toString());
        testShowing = new Showing(new Movie("Spider-Man: No Way Home", "Test Description",
                Duration.ofMinutes(90), testTicketPrice, 1), 1, testShowStartTime);
    }

    @Test
    void testCorrectReservationFee() {
        final int audienceCount = 3;
        double expectedMaxDiscount = 3.0;
        double expectedTotalFee = (testTicketPrice - expectedMaxDiscount) * audienceCount;
        assertEquals(new Reservation(testCustomer, testShowing, audienceCount).totalFee(), expectedTotalFee);
    }

    @Test
    void testInvalidAudienceCount() {
        assertThrows(IllegalArgumentException.class, () -> new Reservation(testCustomer, testShowing, 0));
    }

    @Test
    void testNullCustomer() {
        assertThrows(NullPointerException.class, () -> new Reservation(null, testShowing, 5));
    }

    @Test
    void testNullShowing() {
        assertThrows(NullPointerException.class, () -> new Reservation(testCustomer, null, 5));
    }
}
