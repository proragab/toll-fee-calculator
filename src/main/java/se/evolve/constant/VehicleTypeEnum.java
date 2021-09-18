package se.evolve.constant;

/**
 * @author Ragab Belal
 */
public enum VehicleTypeEnum {
    MOTORBIKE("Motorbike"),
    TRACTOR("Tractor"),
    EMERGENCY("Emergency"),
    DIPLOMAT("Diplomat"),
    FOREIGN("Foreign"),
    MILITARY("Military");
    private final String type;

    VehicleTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
