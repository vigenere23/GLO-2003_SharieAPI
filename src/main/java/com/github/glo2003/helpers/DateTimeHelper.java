package com.github.glo2003.helpers;

import java.time.Instant;
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
}
