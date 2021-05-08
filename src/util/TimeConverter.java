package util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TimeConverter {
    public static String localToDatabase(Instant localTime) {
        ZoneId targetTimeZone = ZoneId.of("UTC");
        ZonedDateTime zonedUTCTime = localTime.atZone(targetTimeZone);
        return zonedUTCTime.format(TimeFormatter.dateTimeFormatter());
    }

   public static LocalDateTime databaseToLocal(String databaseTime) {
        ZoneId sourceTimeZone = ZoneId.of("UTC");
        LocalDateTime databaseTimeLDT = LocalDateTime.parse(databaseTime, TimeFormatter.dateTimeFormatter());
        ZonedDateTime zonedDatabaseTime = databaseTimeLDT.atZone(sourceTimeZone);
        ZoneId targetTimeZone = ZoneId.of("America/Chicago");
        ZonedDateTime zonedLocalTime = zonedDatabaseTime.withZoneSameInstant(targetTimeZone);
        return zonedLocalTime.toLocalDateTime();
    }

}
