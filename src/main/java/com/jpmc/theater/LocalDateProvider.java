package com.jpmc.theater;

import java.time.LocalDate;

/**
 * Singleton class to obtain the current date
 */
public class LocalDateProvider {
    private static LocalDateProvider instance;

    private LocalDateProvider() {
    }

    /**
     * @return make sure to return singleton instance
     */
    public static LocalDateProvider singleton() {
        // create object if it's not already created
        if (instance == null) {
            instance = new LocalDateProvider();
        }

        // returns the singleton object
        return instance;
    }

    public LocalDate currentDate() {
        return LocalDate.now();
    }
}
