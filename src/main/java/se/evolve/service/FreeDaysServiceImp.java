package se.evolve.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author Ragab Belal
 */
public class FreeDaysServiceImp implements FreeDaysService {


    static final int CURRENT_YEAR = 2021;


    @Override
    public boolean isTollFreeDate(LocalDateTime date) {
        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();


        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) return true;

        if (year == CURRENT_YEAR) {
            return month.equals(Month.JANUARY) && day == 1 ||
                    month == Month.MARCH && (day == 28 || day == 29) ||
                    month == Month.APRIL && (day == 1 || day == 30) ||
                    month == Month.MAY && (day == 1 || day == 8 || day == 9) ||
                    month == Month.JUNE && (day == 5 || day == 6 || day == 21) ||
                    month == Month.JULY ||
                    month == Month.NOVEMBER && day == 1 ||
                    month == Month.DECEMBER && (day == 24 || day == 25 || day == 26 || day == 31);
        }
        return false;
    }
}
