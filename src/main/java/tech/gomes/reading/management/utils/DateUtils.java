package tech.gomes.reading.management.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {


    private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");

    public static String formatInstantToDate(Instant instant) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZONE)
                .format(instant);
    }

    public static String formatInstantToDateTime(Instant instant) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZONE)
                .format(instant);
    }
}
