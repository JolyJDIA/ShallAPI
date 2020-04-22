package jolyjdia.utils.time;

import java.time.Duration;

@FunctionalInterface
public interface CallableDuration {
    long call(Duration duration);
}