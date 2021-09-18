package se.evolve.service;

import se.evolve.model.Vehicle;

/**
 * @author Ragab Belal
 */
public interface TollFreeVehicleService {

    /**
     * checlk if passed vehicle must cost toll fee or not
     *
     * @param vehicle the passed vehicle
     * @return true if passed vehicle tollFree and false if not
     */
    boolean isTollFreeVehicle(Vehicle vehicle);
}
