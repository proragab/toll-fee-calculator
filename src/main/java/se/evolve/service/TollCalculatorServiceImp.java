package se.evolve.service;

import se.evolve.model.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * @author Ragab Belal
 */
public class TollCalculatorServiceImp implements TollCalculatorService {

    private static final Logger LOGGER = Logger.getLogger(TollCalculatorServiceImp.class.getName());

    private final FreeDaysService holidayCalculatorService;

    private final TollFreeVehicleService tollFreeVehicleFinderService;

    public TollCalculatorServiceImp(FreeDaysService holidayCalculatorService, TollFreeVehicleService tollFreeVehicleFinderService) {
        this.holidayCalculatorService = holidayCalculatorService;
        this.tollFreeVehicleFinderService = tollFreeVehicleFinderService;
    }


    public int getTollFee(Vehicle vehicle, LocalDateTime... dates) {

        if (!validDates(dates)) {
            return 0;
        }

        if (tollFreeVehicleFinderService.isTollFreeVehicle(vehicle)) {
            LOGGER.info(format("Passed {Vehicle:%s} is toll free", vehicle.getType()));
            return 0;
        }
        LOGGER.info(format("Calculate toll fee for {vehicle:%s} at {date(s):%s}", vehicle.getType(), Arrays.toString(dates)));
        Map<Integer, List<LocalDateTime>> categorizedDatesByRangeHour = categorizeDatesByRangeHour(dates);
        int totalFee = 0;
        for (Map.Entry<Integer, List<LocalDateTime>> datesPerHour : categorizedDatesByRangeHour.entrySet()) {
            int hourFee = 0;
            for (LocalDateTime date : datesPerHour.getValue()) {
                if (holidayCalculatorService.isTollFreeDate(date)) {
                    continue;
                }
                int currentFee = getTollFeeByDate(date);
                if (currentFee > hourFee) {
                    hourFee = currentFee;
                }
            }
            totalFee += hourFee;
        }
        if (totalFee == 0) {
            LOGGER.info(format("{Vehicle:%s} cost 0 fee because it passed at free {date(s):%s}", vehicle.getType(), Arrays.toString(dates)));
        }

        if (totalFee > 60) {
            LOGGER.info(format("Total toll fee before applying Max Limit: {%d}", totalFee));
            totalFee = 60;
            LOGGER.info(format("Total toll fee after applying Max Limit: {%d}", totalFee));
        } else {
            LOGGER.info(format("Total toll fee: {%d}", totalFee));
        }
        return totalFee;
    }

    private boolean validDates(LocalDateTime... dates) {
        if (dates == null || dates.length == 0) {
            LOGGER.warning("No Passed dates");
            return false;
        }

        boolean invalidDate = Arrays.stream(dates).anyMatch(Objects::isNull);
        if (invalidDate) {
            LOGGER.warning("on or more of Passed dates are invalid");
            return false;
        }
        return true;
    }

    private Map<Integer, List<LocalDateTime>> categorizeDatesByRangeHour(LocalDateTime... dates) {
        LOGGER.info("categorizing dates into intervals hourly");
        Map<Integer, List<LocalDateTime>> categorizedDatesByRangeHour = new HashMap<>();
        Arrays.sort(dates, Comparator.naturalOrder());
        int i;
        int j;
        int mapIndex = 0;
        for (i = 0; i < dates.length; i = j) {
            mapIndex++;
            categorizedDatesByRangeHour.putIfAbsent(mapIndex, new ArrayList<>());
            categorizedDatesByRangeHour.get(mapIndex).add(dates[i]);
            for (j = i + 1; j < dates.length; j++) {
                long minutes = dates[i].until(dates[j], ChronoUnit.MINUTES);
                if (minutes < 60) {
                    categorizedDatesByRangeHour.get(mapIndex).add(dates[j]);
                } else {
                    break;
                }
            }
        }
        return categorizedDatesByRangeHour;
    }

    private int getTollFeeByDate(LocalDateTime date) {

        int hour = date.getHour();
        int minute = date.getMinute();

        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }

}

