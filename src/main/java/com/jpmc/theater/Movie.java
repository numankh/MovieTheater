package com.jpmc.theater;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.DoubleStream;

/**
 * This class holds relevant movie info and logic to determine ticket price after eligible discounts
 */
@Getter
@Setter
@EqualsAndHashCode
public class Movie {
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Duration runningTime;
    @NonNull
    private double ticketPrice;
    @NonNull
    private int specialCode;

    private static int MOVIE_CODE_SPECIAL = 1;
    private static final LocalTime LOWER_BOUND_TIME_DISCOUNT = LocalTime.parse("11:00:00.00");
    private static final LocalTime UPPER_BOUND_TIME_DISCOUNT = LocalTime.parse("16:00:00.00");

    /**
     * @param title Movie title
     * @param description Movie description
     * @param runningTime Movie running time
     * @param ticketPrice Movie ticket price
     * @param specialCode Determine whether movie is eligible for the special discount
     */
    public Movie(String title, String description, Duration runningTime, double ticketPrice, int specialCode)
            throws IllegalArgumentException {
        if (title.trim().isEmpty())
            throw new IllegalArgumentException("Invalid movie title");
        if (description.trim().isEmpty())
            throw new IllegalArgumentException("Invalid movie description");
        if (runningTime.compareTo(Duration.ofMinutes(0)) == 0)
            throw new IllegalArgumentException("Invalid movie running time");
        if (ticketPrice <= 0)
            throw new IllegalArgumentException(String.format("Incorrect ticket price provided {%d}", ticketPrice));

        this.title = title;
        this.description = description;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public double calculateFinalTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing.getShowStartTime(), showing.getSequenceOfTheDay());
    }

    /**
        Given the show start time and the sequence in the day, we determine
        if the movie is applicable for any discounts. If its applicable for
        more than one discount, we only return the maximum discount.
     */
    private double getDiscount(LocalDateTime showStartTime, int showSequence) {
        // 20% discount for special movie
        double specialMovieDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
            specialMovieDiscount = ticketPrice * 0.2;
        }

        // $3 discount for 1st show and $2 discount for 2nd show
        double sequenceDiscount = 0;
        if (showSequence == 1) {
            sequenceDiscount = 3.0;
        } else if (showSequence == 2) {
            sequenceDiscount = 2.0;
        }

        // 25% discount for movies between 11AM and 4PM
        double timeDiscount = 0;
        if (showStartTime.toLocalTime().isAfter(LOWER_BOUND_TIME_DISCOUNT) &&
                showStartTime.toLocalTime().isBefore(UPPER_BOUND_TIME_DISCOUNT)) {
            timeDiscount = ticketPrice * 0.25;
        }

        // $1 discount for movies on the 7th day of the month
        double dateDiscount = 0;
        if (showStartTime.getDayOfMonth() == 7) {
            dateDiscount = 1;
        }

        // Obtain the maximum discount
        double maximumDiscount = DoubleStream.of(specialMovieDiscount, sequenceDiscount, timeDiscount, dateDiscount)
                .max()
                .getAsDouble();

        return maximumDiscount;
    }
}