package com.jpmc.theater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTests {

    String testTitle;
    String testDescription;
    Duration testRunningTime;
    LocalDateTime testNoDiscountLocalDatTime;


    @BeforeEach
    void setUp() {
        testTitle = "Spider-Man: No Way Home";
        testDescription = "Test description";
        testRunningTime = Duration.ofMinutes(90);
        testNoDiscountLocalDatTime = LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 0);
    }


    @Test
    void testNormalTicketPrice() {
        // Movie is not a special movie
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 0);

        // The movie showing is not 1st or 2nd
        Showing showing = new Showing(spiderMan, 5, testNoDiscountLocalDatTime);

        assertTrue(showing.isSequence(5));
        assertEquals(12.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testSpecialMovieTicketPrice() {
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime,12.5, 1);
        Showing showing = new Showing(spiderMan, 5, testNoDiscountLocalDatTime);
        assertEquals(10, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testFirstShowingTicketPrice() {
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime,12.0, 0);
        Showing showing = new Showing(spiderMan, 1, testNoDiscountLocalDatTime);
        assertEquals(9.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testSpecialMovieFirstShowingTicketPrice() {
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime,12.5, 1);
        Showing showing = new Showing(spiderMan, 1, testNoDiscountLocalDatTime);
        assertEquals(9.5, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testSecondShowingTicketPrice() {
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 0);
        Showing showing = new Showing(spiderMan, 2, testNoDiscountLocalDatTime);
        assertEquals(10.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testSpecialMovieSecondShowingTicketPrice() {
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.5, 1);
        Showing showing = new Showing(spiderMan, 2, testNoDiscountLocalDatTime);
        assertEquals(10.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testTimeDiscountedTicketPrice() {
        LocalDateTime discountedLocalDateTime = LocalDateTime.of(2022, Month.DECEMBER, 24, 12, 0);
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 0);
        Showing showing = new Showing(spiderMan, 5, discountedLocalDateTime);
        assertEquals(9.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testBelowTimeDiscountLowerBound() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.DECEMBER, 24, 10, 59);
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 0);
        Showing showing = new Showing(spiderMan, 5, localDateTime);
        assertEquals(12.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testAboveTimeDiscountLowerBound() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.DECEMBER, 24, 16, 01);
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 0);
        Showing showing = new Showing(spiderMan, 5, localDateTime);
        assertEquals(12.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testDateDiscountedTicketPrice() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.DECEMBER, 7, 10, 0);
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 0);
        Showing showing = new Showing(spiderMan, 5, localDateTime);
        assertEquals(11.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testAllDiscountsAppliedTicketPrice() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.DECEMBER, 7, 12, 0);
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 1);
        Showing showing = new Showing(spiderMan, 1, localDateTime);
        assertEquals(9.0, spiderMan.calculateFinalTicketPrice(showing));
    }

    @Test
    void testNullMovieTitle() {
        assertThrows(NullPointerException.class, () -> new Movie(null, testDescription, testRunningTime, 12.0, 0));
    }

    @Test
    void testEmptyMovieTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Movie(" ", testDescription, testRunningTime, 12.0, 0));
    }

    @Test
    void testNullMovieDescription() {
        assertThrows(NullPointerException.class, () -> new Movie(testTitle, null, testRunningTime, 12.0, 0));
    }

    @Test
    void testEmptyMovieDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Movie(testTitle, " ", testRunningTime, 12.0, 0));
    }

    @Test
    void testNullMovieRunningTime() {
        assertThrows(NullPointerException.class, () -> new Movie(testTitle, testDescription, null, 12.0, 0));
    }

    @Test
    void testNoMovieRunningTime() {
        assertThrows(IllegalArgumentException.class, () -> new Movie(testTitle, testDescription, Duration.ofMinutes(0), 12.0, 0));
    }

    @Test
    void testIncorrectTicketPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Movie(testTitle, testDescription, testRunningTime, -10.0, 0));
    }
}
