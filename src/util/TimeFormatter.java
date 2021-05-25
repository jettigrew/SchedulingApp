package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeFormatter {
    public static DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public static String getDisplayTime(String inputTime) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputTime);
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String timeString = new SimpleDateFormat("HH:mm").format(date);
        int hourInteger = Integer.parseInt(timeString.substring(0,2));
        String minutesString = timeString.substring(3);
        if (hourInteger > 12) {
            int newHourInteger = hourInteger - 12;
            String newTimeString;
            if (newHourInteger < 10) {
                newTimeString = "0" + newHourInteger + ":" + minutesString;
            }
            else {
                newTimeString = newHourInteger + ":" + minutesString;
            }
            return dateString + " " + newTimeString + " PM";
        }
        else if (hourInteger == 12) {
            return dateString + " " + timeString + " PM";
        }
        else if (hourInteger < 1) {
            return dateString + " " + "12:" + minutesString + " AM";
        }
        return dateString + " " + timeString + " AM";
    }

    public static int getCorrectHour(String hour, String amPM) {
        int intHour = Integer.parseInt(hour);
        if (amPM.equals("AM")) {
            if (intHour == 12) {
                return 0;
            }
            return intHour;
        }
        else {
            if (intHour == 12) {
                return 12;
            }
            return 12 + intHour;
        }
    }
}
