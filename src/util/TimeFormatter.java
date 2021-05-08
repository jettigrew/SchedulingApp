package util;

import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    public static DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
