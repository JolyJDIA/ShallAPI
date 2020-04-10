package jolyjdia.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <A, B> @NotNull Pair<A, B> put(A first, B second) {
        return new Pair<>(first, second);
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (first != null ? first.hashCode() : 0);
        hash = 31 * hash + (second != null ? second.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Pair) {
            Pair<A, B> pair = (Pair<A, B>) o;
            return (first != null ? first.equals(pair.first) : pair.first == null) && (second != null ? second.equals(pair.second) : pair.second == null);
        }
        return false;
    }
    @NonNls
    @Override
    public String toString() {
        return first + "=" + second;
    }
}
