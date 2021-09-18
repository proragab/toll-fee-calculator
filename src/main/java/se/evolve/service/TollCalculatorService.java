package se.evolve.service;

import se.evolve.model.Vehicle;

import java.time.LocalDateTime;

/**
 * @author Ragab Belal
 */
public interface TollCalculatorService {

    /**
     * Calculate the total toll fee for one day
     *
     * @param vehicle - the vehicle
     * @param dates   - date and time of all passes on one day
     * @return - the total toll fee for that day
     */
    int getTollFee(Vehicle vehicle, LocalDateTime... dates);
}
