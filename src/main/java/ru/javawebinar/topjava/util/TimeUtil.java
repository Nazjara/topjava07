package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static boolean isBetween(LocalDateTime lt, LocalDateTime startTime, LocalDateTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
}
