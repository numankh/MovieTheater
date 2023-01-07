package com.jpmc.theater;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * This class holds info relevant to a customer making a reservation by purchasing ticket(s) for a showing
 */
@Getter
@Setter
public class Reservation {
    private Customer customer;
    private Showing showing;
    private int audienceCount;

    /**
     * @param customer The customer making this reservation
     * @param showing The specific showing of the movie
     * @param audienceCount Amount of people included in this reservation
     */
    public Reservation(@NonNull Customer customer, @NonNull Showing showing, int audienceCount) throws IllegalArgumentException {
        if (audienceCount <= 0) {
            throw new IllegalArgumentException(
                    String.format("The audience count provided is not valid: {%s}", audienceCount));
        }
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    /**
        Using the audience count, the total fee for the reservation can be calculated
        via the getMovieFee method in the Showing class
     */
    public double totalFee() {
        return showing.getMovieFee() * audienceCount;
    }
}