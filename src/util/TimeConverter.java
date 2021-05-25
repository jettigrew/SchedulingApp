package util;

import DAO.AppointmentDAO;
import model.Appointment;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TimeConverter {
    public static String localToDatabase(LocalDateTime localTime) {
        ZoneId sourceTimeZone = ZoneId.systemDefault();
        ZonedDateTime zonedSourceTime = localTime.atZone(sourceTimeZone);
        ZoneId targetTimeZone = ZoneId.of("UTC");
        ZonedDateTime zonedDatabaseTime = zonedSourceTime.withZoneSameInstant(targetTimeZone);
        return zonedDatabaseTime.format(TimeFormatter.dateTimeFormatter());
    }

   public static LocalDateTime databaseToLocal(String databaseTime) {
        ZoneId sourceTimeZone = ZoneId.of("UTC");
        LocalDateTime databaseTimeLDT = LocalDateTime.parse(databaseTime, TimeFormatter.dateTimeFormatter());
        ZonedDateTime zonedDatabaseTime = databaseTimeLDT.atZone(sourceTimeZone);
        ZoneId targetTimeZone = ZoneId.systemDefault();
        ZonedDateTime zonedLocalTime = zonedDatabaseTime.withZoneSameInstant(targetTimeZone);
        return zonedLocalTime.toLocalDateTime();
    }

    public static String databaseToLocalString(String databaseTime) {
        ZoneId sourceTimeZone = ZoneId.of("UTC");
        LocalDateTime databaseTimeLDT = LocalDateTime.parse(databaseTime, TimeFormatter.dateTimeFormatter());
        ZonedDateTime zonedDatabaseTime = databaseTimeLDT.atZone(sourceTimeZone);
        ZoneId targetTimeZone = ZoneId.systemDefault();
        ZonedDateTime zonedLocalTime = zonedDatabaseTime.withZoneSameInstant(targetTimeZone);
        return zonedLocalTime.format(TimeFormatter.dateTimeFormatter());
    }

    public static boolean isWithinBusinessHours(LocalDateTime startTime, LocalDateTime endTime) {
        ZoneId sourceTimeZone = ZoneId.systemDefault();
        ZonedDateTime zonedSourceStartTime = startTime.atZone(sourceTimeZone);
        ZonedDateTime zonedSourceEndTime = endTime.atZone(sourceTimeZone);
        ZoneId targetTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime zonedEasternStartTime = zonedSourceStartTime.withZoneSameInstant(targetTimeZone);
        ZonedDateTime zonedEasternEndTime = zonedSourceEndTime.withZoneSameInstant(targetTimeZone);
        LocalTime easternStart = zonedEasternStartTime.toLocalTime();
        LocalTime easternEnd = zonedEasternEndTime.toLocalTime();

        LocalTime businessStart = LocalTime.parse("08:00:00");
        LocalTime businessEnd = LocalTime.parse("22:00:00");
        if ((((easternStart.compareTo(businessStart)) > -1 && (easternStart.compareTo(businessEnd) < 0))) &&
            (((easternEnd.compareTo(businessStart)) > 0 && (easternEnd.compareTo(businessEnd) < 1))) &&
            (!(zonedEasternStartTime.getDayOfWeek() == DayOfWeek.SATURDAY)) && (!(zonedEasternStartTime.getDayOfWeek() == DayOfWeek.SUNDAY)) &&
            (!(zonedEasternEndTime.getDayOfWeek() == DayOfWeek.SATURDAY)) && (!(zonedEasternEndTime.getDayOfWeek() == DayOfWeek.SUNDAY))) {
            return true;
        }
        return false;
    }

    public static void checkIfAppointmentSoon(int userID) throws ParseException {
        LocalDateTime nowPlusFifteenMinutes = LocalDateTime.now().plusMinutes(15);
        Appointment nextAppointment = AppointmentDAO.retrieveUsersNextAppointment(userID);
        if (AppointmentDAO.retrieveUsersNextAppointment(userID) == null) {
            AlertGenerator noMeetingSoonAlert = new AlertGenerator();
            noMeetingSoonAlert.createAlert("INFORMATION", "No appointments in the next 15 minutes!");
            return;
        } else {
            assert nextAppointment != null;
            LocalDateTime appointmentStart = LocalDateTime.parse(nextAppointment.getAppointmentStart(), TimeFormatter.dateTimeFormatter());
            if ((appointmentStart.compareTo(nowPlusFifteenMinutes) < 1) && (appointmentStart.compareTo(LocalDateTime.now()) > -1)) {
                String meetingAlertText = "You have an appointment soon! " + "Appointment " + nextAppointment.getAppointmentID() + " " +
                        nextAppointment.getAppointmentTitle() + " at " + TimeFormatter.getDisplayTime(nextAppointment.getAppointmentStart());
                AlertGenerator meetingSoonAlert = new AlertGenerator();
                meetingSoonAlert.createAlert("WARNING", meetingAlertText);
            } else {
                AlertGenerator noMeetingSoonAlert = new AlertGenerator();
                noMeetingSoonAlert.createAlert("INFORMATION", "No appointments in the next 15 minutes!");
            }
        }
    }
}
