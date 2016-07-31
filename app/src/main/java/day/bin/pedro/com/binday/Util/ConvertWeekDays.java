package day.bin.pedro.com.binday.Util;

/**
 * Created by pedro on 31/07/16.
 */
public class ConvertWeekDays {

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
}
