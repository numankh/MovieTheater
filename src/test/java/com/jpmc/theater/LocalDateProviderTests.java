package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateProviderTests {
    @Test
    void testCurrentDate() {
        assertEquals(LocalDateProvider.singleton().currentDate(), LocalDate.now());
    }
}
