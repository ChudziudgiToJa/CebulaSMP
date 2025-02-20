package pl.chudziudgi.lifesteal.util;

import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public final class DurationUtil {

    public static String format(@NonNull final Instant instant) {
        return format(instant, ZoneId.of("Poland"));
    }

    public static String format(@NonNull final Instant instant, @NonNull final ZoneId zoneId) {
        return format(instant, zoneId, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(@NonNull final Instant instant, @NonNull final ZoneId zoneId, @NonNull final String pattern) {
        final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return zonedDateTime.format(formatter);
    }

    public static String convertLong(long seconds) {
        long d = TimeUnit.SECONDS.toDays(seconds);
        long h = TimeUnit.SECONDS.toHours(seconds) - d * 24L;
        long m = TimeUnit.SECONDS.toMinutes(seconds) - d * 24L * 60L - h * 60L;
        long s = seconds - d * 24L * 60L * 60L - h * 60L * 60L - m * 60L;
        if (d != 0L)
            return d + "d " + h + "h " + m + "min " + s + "s";
        if (h != 0L)
            return h + "h " + m + "min " + s + "s";
        if (m != 0L)
            return m + "min " + s + "s";
        return s + "s";
    }

    public static String format(Duration duration) {
        return format(duration, true);
    }

    public static String format(Duration duration, boolean removeMillis) {
        if (removeMillis) {
            duration = Duration.ofSeconds(duration.toSeconds());
        }

        long days = duration.toDaysPart();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long millis = duration.toMillisPart();

        StringBuilder formattedDuration = new StringBuilder();

        if (days > 0) {
            formattedDuration.append(days).append(MessageUtil.smallText("d "));
        }
        if (hours > 0) {
            formattedDuration.append(hours).append(MessageUtil.smallText("g "));
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append(MessageUtil.smallText("m "));
        }
        if (seconds > 0 || formattedDuration.isEmpty()) {
            formattedDuration.append(seconds).append(MessageUtil.smallText("s"));
        }
        if (seconds == 0 && millis > 0) {
            formattedDuration.append(" ").append(millis).append(MessageUtil.smallText("ms"));
        }

        return formattedDuration.toString().trim();
    }

    public static String getTimeFormat(long milliseconds) {
        long days = milliseconds / (24 * 60 * 60 * 1000);
        milliseconds %= (24 * 60 * 60 * 1000);

        long hours = milliseconds / (60 * 60 * 1000);
        milliseconds %= (60 * 60 * 1000);

        long minutes = milliseconds / (60 * 1000);
        milliseconds %= (60 * 1000);

        long seconds = milliseconds / 1000;
        milliseconds %= 1000;

        long millis = milliseconds;

        StringBuilder formattedTime = new StringBuilder();
        if (days != 0) formattedTime.append(days).append("d ");
        if (hours != 0) formattedTime.append(hours).append("h ");
        if (minutes != 0) formattedTime.append(minutes).append("m ");
        if (seconds != 0) formattedTime.append(seconds).append("s ");
//        if (millis != 0) formattedTime.append(millis).append("ms");
        if (!formattedTime.isEmpty() && formattedTime.charAt(formattedTime.length() - 1) == ' ') {
            formattedTime.deleteCharAt(formattedTime.length() - 1);
        }
        return formattedTime.toString();
    }

}
