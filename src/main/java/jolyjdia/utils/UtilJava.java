package jolyjdia.utils;

import java.lang.reflect.Array;

public final class UtilJava {
    private UtilJava() { }

    public static Object getArray(Class<?> var0, Object... var1) {
        Object var2 = Array.newInstance(var0, var1.length);
        int s = 0;
        try {
            for (Object var4 : var1) {
                Array.set(var2, s, var4);
                s += 1;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return var2;
    }
}
