package jolyjdia.gadgets.nms;

public interface FieldAccessor<T> {
    T get(Object var1);

    void set(Object var1, Object var2);

    boolean hasField(Object var1);
}
