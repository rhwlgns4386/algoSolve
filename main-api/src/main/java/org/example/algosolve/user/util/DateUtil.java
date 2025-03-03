package org.example.algosolve.user.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtil {

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(toInstant(localDateTime));
    }

    private static Instant toInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant();
    }
}
