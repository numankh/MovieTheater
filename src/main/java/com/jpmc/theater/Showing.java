package com.jpmc.theater;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * For each movie, a showing keeps track of its sequence in the day and start time
 */
@Getter
@Setter
public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    /**
     * @param movie The specific movie for this showing
     * @param sequenceOfTheDay Sequentially which showing from the start of the day is this
     * @param showStartTime The date and time for the showing
     */
    public Showing(@NonNull Movie movie, int sequenceOfTheDay, @NonNull LocalDateTime showStartTime) throws IllegalArgumentException {
        if (sequenceOfTheDay < 1)
            throw new IllegalArgumentException("Sequence of the day must be a positive number");

        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.calculateFinalTicketPrice(this);
    }
}
