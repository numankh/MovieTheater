package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class Initializes a list of showings pertaining to a single theater and
 * allows a customer to select the showing that they want to make a reservation for
 */
public class Theater {

    LocalDateProvider provider;
    private List<Showing> schedule;

    /**
     * @param provider Singleton class we use to obtain the current date
     */
    public Theater(LocalDateProvider provider) {
        this.provider = provider;

        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spider-Man's identity has been revealed",
                Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", "Thirteen-year-old girl can turn into giant red panda",
                Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", "Batman investigates the mayor's death",
                Duration.ofMinutes(95), 9, 0);

        schedule = List.of(
            new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
            new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
            new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
            new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
            new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
            new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
            new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
            new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
            new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            throw new IllegalStateException("Not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getShowStartTime() + " | " + s.getMovie().getTitle() +
                        " | " + s.getMovie().getDescription() + " | " + humanReadableFormat(s.getMovie().getRunningTime()) + " | $" + s.getMovie().getTicketPrice())
        );
        System.out.println("===================================================");
    }

    public void printScheduleJsonFormat() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        System.out.println("[");
        for (Showing showing : schedule) {
            System.out.println("\t{");
            System.out.println(String.format("\t\t\"sequence\":%s", showing.getSequenceOfTheDay()));
            System.out.println(String.format("\t\t\"startTime\":\"%s\"", showing.getShowStartTime()));
            System.out.println(String.format("\t\t\"title\":\"%s\"", showing.getMovie().getTitle()));
            System.out.println(String.format("\t\t\"description\":\"%s\"", showing.getMovie().getDescription()));
            System.out.println(String.format("\t\t\"runningTime\":\"%s\"", humanReadableFormat(showing.getMovie().getRunningTime())));
            System.out.println(String.format("\t\t\"ticketPrice\":%s", showing.getMovie().getTicketPrice()));
            System.out.println("\t},");
        }
        System.out.println("]");
        System.out.println("===================================================");
    }

    public String humanReadableFormat(final Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    /**
        (s) postfix should be added to handle plural correctly
     */
    private String handlePlural(final long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }
}
