package com.robbypark.android.idleidea;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final long MONTH_MILLIS = 30 * DAY_MILLIS;
    private static final long YEAR_MILLIS = 12 * MONTH_MILLIS;

    // Creates a time string for completed idea
    // EX: "9/9/19 - 9/12/19. Total: 10m10d10h"
    public static String getTimeString(long startTime, long endTime) {
        return SimpleDateFormat.getDateInstance().format(new Date(startTime))
                + " - "
                + SimpleDateFormat.getDateInstance().format(new Date(endTime))
                + ". Total: "
                + TimeUtils.timeAgo(endTime - startTime);
    }

    // Creates a "time ago" string
    public static String timeAgo(long duration) {
        long years = duration / YEAR_MILLIS;
        duration %= YEAR_MILLIS;
        long months = duration / MONTH_MILLIS;
        duration %= MONTH_MILLIS;
        long days = duration / DAY_MILLIS;
        duration %= DAY_MILLIS;
        long hours = duration / HOUR_MILLIS;

        StringBuilder sb = new StringBuilder();
        if(years > 0) sb.append(years).append("y");
        if(months > 0) sb.append(months).append("m");
        if(days > 0) sb.append(days).append("d");
        if(hours > 0) sb.append(hours).append("h");

        String out = sb.toString();

        if(out.equals("")) return "<1hr";

        return sb.toString();
    }

}
