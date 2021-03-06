package jolyjdia.utils.time;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.*;

public final class TemporalDuration {
    private static final TimeFormatter[] DEFAULT_FORMAT = {
            TimeFormatter.DAYS,
            TimeFormatter.HOURS,
            TimeFormatter.MINUTES,
            TimeFormatter.SECONDS
    };

    private Duration duration;

    private TemporalDuration(Duration duration) {
        if(duration == null) {
            return;
        }
        this.duration = duration;
    }
    public static @NotNull TemporalDuration of(int month, int day, int hour, int minute) {
        LocalDateTime startInclusive = LocalDateTime.now();
        LocalDateTime endExclusive = LocalDateTime.of(startInclusive.getYear(), month, day, hour, minute);
        if(startInclusive.toEpochSecond(ZoneOffset.UTC) > endExclusive.toEpochSecond(ZoneOffset.UTC)) {
            endExclusive = endExclusive.plusYears(1);
        }
        return new TemporalDuration(Duration.between(startInclusive, endExclusive));
    }
    @Contract("_ -> new")
    public static @NotNull TemporalDuration leftTime(long unixTime) {
        LocalDateTime startInclusive = LocalDateTime.now();
        LocalDateTime endExclusive = Instant.ofEpochMilli(unixTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if(startInclusive.toEpochSecond(ZoneOffset.UTC) > endExclusive.toEpochSecond(ZoneOffset.UTC)) {
            endExclusive = endExclusive.plusYears(1);
        }
        return new TemporalDuration(Duration.between(startInclusive, endExclusive));
    }
    public @NotNull String toFormat(@NotNull TimeFormatter... timeFormatter) {
        StringBuilder buf = new StringBuilder();
        for(TimeFormatter formatter : timeFormatter) {
            long x = formatter.getCallable().call(duration);
            if ((x % 100 / 10) == 1) {
                buf.append(x).append(formatter.getPlural());
                continue;
            }
            long y = x % 10;
            if (y == 1) {
                buf.append(x).append(formatter.getSingular());
            } else if (y == 2 || y == 3 || y == 4) {
                buf.append(x).append(formatter.getOther());
            } else {
                buf.append(x).append(formatter.getPlural());
            }
        }
        return buf.toString();
    }
    public @NotNull String toFormat() {
        return toFormat(DEFAULT_FORMAT);
    }
    @Override
    public @NotNull String toString() {
        return toFormat();
    }
}