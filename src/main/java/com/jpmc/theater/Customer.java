package com.jpmc.theater;

import lombok.*;

/**
 * An individual intending to make a reservation for a showing at a theater
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Customer {
    @NonNull
    private String name;

    @NonNull
    private String id;
}