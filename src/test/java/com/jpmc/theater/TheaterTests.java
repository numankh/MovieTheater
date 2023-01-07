package com.jpmc.theater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheaterTests {

    Theater theater;
    Customer testCustomer;

    @BeforeEach
    void setUp() {
        theater = new Theater(LocalDateProvider.singleton());
        testCustomer = new Customer("John Doe", UUID.randomUUID().toString());
    }

    @Test
    void testCorrectTotalFeeForCustomer() {
        final int numberOfTickets = 4;
        Reservation reservation = theater.reserve(testCustomer, 1, numberOfTickets);

        double expectedDiscount = 3;
        double expectedFee = (11.0 - expectedDiscount) * numberOfTickets;
        assertEquals(expectedFee, reservation.totalFee());
    }

    @Test
    void testInvalidReservationSequence(){
        final int numberOfTickets = 4;
        final int incorrectSequence = 0;

        assertThrows(IllegalStateException.class, () ->
                theater.reserve(testCustomer, incorrectSequence, numberOfTickets));
    }

    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }

    @Test
    void printMovieScheduleJsonFormat() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printScheduleJsonFormat();
    }
}
