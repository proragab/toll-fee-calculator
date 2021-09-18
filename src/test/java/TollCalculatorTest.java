import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import se.evolve.model.*;
import se.evolve.service.FreeDaysServiceImp;
import se.evolve.service.TollCalculatorService;
import se.evolve.service.TollCalculatorServiceImp;
import se.evolve.service.TollFreeVehicleServiceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ragab Belal
 */
class TollCalculatorTest {

    private static final int CURRENT_YEAR = 2021;
    private static TollCalculatorService tollCalculatorService;

    @BeforeAll
    static void init() {
        tollCalculatorService = new TollCalculatorServiceImp(new FreeDaysServiceImp(), new TollFreeVehicleServiceImp());
    }

    @Test
    void motorbikePassed_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.JANUARY, 1), LocalTime.of(8, 29));
        assertEquals(0, tollCalculatorService.getTollFee(new Motorbike(), date));
    }

    @Test
    void emergencyPassed_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.JANUARY, 1), LocalTime.of(8, 29));
        assertEquals(0, tollCalculatorService.getTollFee(new Emergency(), date));
    }

    @Test
    void foreignPassed_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.JANUARY, 1), LocalTime.of(8, 29));
        assertEquals(0, tollCalculatorService.getTollFee(new Foreign(), date));
    }

    @Test
    void militaryPassed_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.JANUARY, 1), LocalTime.of(8, 29));
        assertEquals(0, tollCalculatorService.getTollFee(new Military(), date));
    }

    @Test
    void tractorPassed_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.JANUARY, 1), LocalTime.of(8, 29));
        assertEquals(0, tollCalculatorService.getTollFee(new Tractor(), date));
    }

    @Test
    void vehiclePassedAtSaturday_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.APRIL, 10), LocalTime.of(8, 29));
        assertEquals(0, tollCalculatorService.getTollFee(new Car(), date));
    }

    @Test
    void vehiclePassedAtSunday_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.APRIL, 11), LocalTime.of(8, 29));
        assertEquals(0, tollCalculatorService.getTollFee(new Car(), date));
    }

    @Test
    void vehiclePassedAt30April_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.APRIL, 30), LocalTime.of(7, 30));
        assertEquals(0, tollCalculatorService.getTollFee(new Car(), date));
    }

    @Test
    void vehiclePassedAtJuly_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.JULY, 16), LocalTime.of(7, 30));
        assertEquals(0, tollCalculatorService.getTollFee(new Car(), date));
    }

    @Test
    void vehiclePassedAtFirstHalfOf9clock_then_Free() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.MAY, 11), LocalTime.of(9, 12));
        assertEquals(0, tollCalculatorService.getTollFee(new Car(), date));
    }


    @Test
    void vehiclePassedTwoTimesAt8clock_then_HigherValueOfBoth() {

        LocalDateTime date1 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(8, 10));
        LocalDateTime date2 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(8, 45));

        assertEquals(13, tollCalculatorService.getTollFee(new Car(), date1, date2));
    }

    @Test
    void vehiclePassedAtMultiTimesInOneHour_then_HigherValueOfThem() {

        LocalDateTime date1 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(6, 24));
        LocalDateTime date2 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(6, 45));
        LocalDateTime date3 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(7, 20));


        assertEquals(18, tollCalculatorService.getTollFee(new Car(), date1, date2, date3));
    }

    @Test
    void vehiclePassedInMultiSpans_then_highestCostInEachSpan_Added_To_totalCost() {
        LocalDateTime date1 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(6, 24));
        LocalDateTime date2 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(6, 45));
        LocalDateTime date3 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(7, 20));
        LocalDateTime date4 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(8, 24));
        LocalDateTime date5 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(9, 10));
        LocalDateTime date6 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(10, 20));

        assertEquals(31, tollCalculatorService.getTollFee(new Car(), date1, date2, date3, date4, date5, date6));
    }

    @Test
    void vehiclePassedMultipleTimes_then_Max60() {

        LocalDateTime date1 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(6, 24));
        LocalDateTime date2 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(6, 45));
        LocalDateTime date3 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(7, 20));
        LocalDateTime date4 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(8, 24));
        LocalDateTime date5 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(9, 45));
        LocalDateTime date6 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(10, 20));
        LocalDateTime date7 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(11, 24));
        LocalDateTime date8 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(12, 45));
        LocalDateTime date9 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(13, 20));
        LocalDateTime date10 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(14, 45));
        LocalDateTime date11 = LocalDateTime.of(LocalDate.of(CURRENT_YEAR, Month.FEBRUARY, 10), LocalTime.of(15, 20));
        LocalDateTime[] dates = {date1, date2, date3, date4, date5, date6, date7, date8, date9, date10, date11};
        assertEquals(60, tollCalculatorService.getTollFee(new Car(), dates));
    }


}
