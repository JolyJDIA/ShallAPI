package jolyjdia.api.constant;

import jolyjdia.utils.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TwoKeyEnumMap<K1, K2, V extends Enum<V>> {
    private int size;
    private Node<K1, K2, V>[] table;

    public TwoKeyEnumMap(@NotNull Class<V> value) {
        table = new Node[value.getEnumConstants().length];
    }

    public void put(K1 k1, K2 k2, V v) {
        if(size >= table.length) {
            grow();
        }
        table[size] = new Node<>(Pair.put(k1, k2), v);
        ++size;
    }
    public V get(Object o) {
        for(Node<K1, K2, V> node : table) {
            Pair<K1, K2> pair = node.pairKey;
            int targetHash = o.hashCode();

            if(pair.getFirst().hashCode() == targetHash
                    || pair.getSecond().hashCode() == targetHash) {
                return node.value;
            }
        }
        return null;
    }
    public static class Node<K1, K2, V> {
        private final Pair<K1, K2> pairKey;
        private final V value;

        Node(Pair<K1, K2> pairKey, V value) {
            this.pairKey = pairKey;
            this.value = value;
        }
    }
    private void grow() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); //grow 50%
        if (newCapacity < 0) {//переполнение
            newCapacity = Integer.MAX_VALUE - 8;
        }
        table = Arrays.copyOf(table, newCapacity);
    }

    public int size() {
        return size;
    }
}
