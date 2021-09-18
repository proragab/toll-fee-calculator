package se.evolve.service;

import java.time.LocalDateTime;

/**
 * @author Ragab Belal
 */
public interface FreeDaysService {

    /**
     * check if passed date is Toll free day or not
     *
     * @param date date and time of passed day
     * @return true if day is free day and false if not
     */
    boolean isTollFreeDate(LocalDateTime date);
}
