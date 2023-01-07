package com.jpmc.theater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShowingTests {

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
    void testNullMovieForShowing() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.DECEMBER, 7, 12, 0);
        assertThrows(NullPointerException.class, () -> new Showing(null, 1, localDateTime));
    }

    @Test
    void testNullStartTimeForShowing() {
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 1);
        assertThrows(NullPointerException.class, () -> new Showing(spiderMan, 1, null));
    }

    @Test
    void testInvalidSequenceOfTheDay() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.DECEMBER, 7, 12, 0);
        Movie spiderMan = new Movie(testTitle, testDescription, testRunningTime, 12.0, 1);
        assertThrows(IllegalArgumentException.class, () -> new Showing(spiderMan, 0, localDateTime));
    }
}
