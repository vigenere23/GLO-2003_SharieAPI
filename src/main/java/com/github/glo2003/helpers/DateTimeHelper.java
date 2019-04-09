package com.github.glo2003.helpers;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateTimeHelper {
    public static List<Instant> removeTimeFromInstants(List<Instant> instants) {
        List<Instant> cleanedInstants = new ArrayList<>();
        for (Instant instant : instants) {
            cleanedInstants.add(removeTimeFromInstant(instant));
        }
        return cleanedInstants;
    }

    public static Instant removeTimeFromInstant(Instant instant) {
        return instant.truncatedTo(ChronoUnit.DAYS);
    }

    public static String fromInstantToDateString(Instant instant) {
        return instant.toString().split("T")[0];
    }

    public static Instant fromDateStringToInstant(String stringDate) throws DateTimeParseException {
        return Instant.parse(stringDate + "T00:00:00Z");
    }
}
