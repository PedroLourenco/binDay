package day.bin.pedro.com.binday.Util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by pedro on 31/07/16.
 */
public class DateUtils {

    public static String getWeekDay(String day) {

        if (day == null || day.isEmpty()) {
            return "";
        }

        if (day.toUpperCase().equals("MON")) {
            return "Monday";
        } else if (day.toUpperCase().equals("TUE")) {
            return "Tuesday";
        } else if (day.toUpperCase().equals("WED")) {
            return "Wednesday";
        } else if (day.toUpperCase().equals("THU")) {
            return "Thursday";
        } else if (day.toUpperCase().equals("FRI")) {
            return "Friday";
        } else {
            return day;
        }
    }

    /**
     * Convert a UTC date into the specified time zone
     *
     * @param tzName the name of the time zone for the output calendar
     * @param utc the UTC time being converted
     *
     * @return a calendar in the specified time zone with the appropriate date
     */
    public static Calendar convertTimeToLocal(String tzName, Calendar utc) {
        TimeZone zone = TimeZone.getTimeZone(tzName);
        int offset = zone.getOffset(utc.getTimeInMillis());
        GregorianCalendar local = new GregorianCalendar(zone);
        local.setTimeInMillis(utc.getTimeInMillis());
        local.add(Calendar.MILLISECOND, offset);

        return local;
    }
}
