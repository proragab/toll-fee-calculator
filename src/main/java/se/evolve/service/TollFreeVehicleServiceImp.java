package se.evolve.service;

import se.evolve.constant.VehicleTypeEnum;
import se.evolve.model.Vehicle;

/**
 * @author Ragab Belal
 */
public class TollFreeVehicleServiceImp implements TollFreeVehicleService {


    @Override
    public boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getType();
        return vehicleType.equals(VehicleTypeEnum.MOTORBIKE.getType()) ||
                vehicleType.equals(VehicleTypeEnum.TRACTOR.getType()) ||
                vehicleType.equals(VehicleTypeEnum.EMERGENCY.getType()) ||
                vehicleType.equals(VehicleTypeEnum.DIPLOMAT.getType()) ||
                vehicleType.equals(VehicleTypeEnum.FOREIGN.getType()) ||
                vehicleType.equals(VehicleTypeEnum.MILITARY.getType());
    }
}
