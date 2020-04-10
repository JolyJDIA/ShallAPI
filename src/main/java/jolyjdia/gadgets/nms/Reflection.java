package jolyjdia.gadgets.nms;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NonNls;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Reflection {
    private static final String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_PREFIX;
    private static final String VERSION;
    private static final Pattern MATCH_VARIABLE;

    static {
        NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
        VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
        MATCH_VARIABLE = Pattern.compile("\\{([^}]+)}");
    }

    private Reflection() {
    }

    public static <T> FieldAccessor<T> getField(Class<?> var0, String var1, Class<T> var2) {
        return getField(var0, var1, var2, 0);
    }

    public static <T> FieldAccessor<T> getField(String var0, String var1, Class<T> var2) {
        return getField(getClass(var0), var1, var2, 0);
    }

    public static <T> FieldAccessor<T> getField(Class<?> var0, Class<T> var1, int var2) {
        return getField(var0, null, var1, var2);
    }

    public static <T> FieldAccessor<T> getField(String var0, Class<T> var1, int var2) {
        return getField(getClass(var0), var1, var2);
    }

    private static <T> FieldAccessor<T> getField(Class<?> var0, String var1, @NonNls Class<T> var2, int var3) {
        Field[] var7;
        //int var6 = (var7 = var0.getDeclaredFields()).length;

        for(int i = 0; i < (var7 = var0.getDeclaredFields()).length; ++i) {
            Field var4 = var7[i];
            if ((var1 == null || var4.getName().equals(var1)) && var2.isAssignableFrom(var4.getType()) && var3 <= 0) {
                var4.setAccessible(true);
                return new FieldAccessor<T>() {
                    @Override
                    public T get(Object o) {
                        try {
                            return (T)var4.get(o);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }
                    @Override
                    public void set(Object o1, Object o) {
                        try {
                            var4.set(o1, o);
                        } catch (IllegalAccessException var4x) {
                            throw new RuntimeException("Cannot access reflection.", var4x);
                        }
                    }
                    @Override
                    public boolean hasField(Object o) {
                        return var4.getDeclaringClass().isAssignableFrom(o.getClass());
                    }
                };
            }
            var3--;
        }

        if (var0.getSuperclass() != null) {
            return getField(var0.getSuperclass(), var1, var2, var3);
        } else {
            throw new IllegalArgumentException("Cannot find field with type " + var2);
        }
    }

    public static MethodInvoker getMethod(String var0, String var1, Class<?>... var2) {
        return getTypedMethod(getClass(var0), var1, null, var2);
    }

    public static MethodInvoker getMethod(Class<?> var0, String var1, Class<?>... var2) {
        return getTypedMethod(var0, var1, null, var2);
    }



    public static MethodInvoker getTypedMethod(Class<?> var0, String var1, Class<?> var2, Class<?>... var3) {
        Method[] var7;
        //int var6 = (var7 = var0.getDeclaredMethods()).length;

        for(int i = 0; i < (var7 = var0.getDeclaredMethods()).length; ++i) {
            Method var4 = var7[i];
            if ((var1 == null || var4.getName().equals(var1)) && (var2 == null || var4.getReturnType().equals(var2)) && Arrays.equals(var4.getParameterTypes(), var3)) {
                var4.setAccessible(true);
                return (var11, var21) -> {
                    try {
                        return var4.invoke(var11, var21);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (RuntimeException var4x) {
                        throw new RuntimeException("Cannot invoke method " + var4, var4x);
                    }
                    return null;
                };
            }
        }

        if (var0.getSuperclass() != null) {
            return getMethod(var0.getSuperclass(), var1, var3);
        } else {
            throw new IllegalStateException(String.format("Unable to find method %s (%s).", var1, Arrays.asList(var3)));
        }
    }

    public static ConstructorInvoker getConstructor(String var0, Class<?>... var1) {
        return getConstructor(getClass(var0), var1);
    }

    public static ConstructorInvoker getConstructor(Class<?> var0, Class<?>... var1) {
        Constructor<?>[] var5;

        for(int i = 0; i < (var5 = var0.getDeclaredConstructors()).length; i++) {
            Constructor<?> var2 = var5[i];
            if (Arrays.equals(var2.getParameterTypes(), var1)) {
                var2.setAccessible(true);
                return var11 -> {
                    try {
                        return var2.newInstance(var11);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (RuntimeException var31) {
                        throw new RuntimeException("Cannot invoke constructor " + var2, var31);
                    }
                    return null;
                };
            }
        }

        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", var0, Arrays.asList(var1)));
    }

    public static Class<?> getClass(String var0) {
        return getCanonicalClass(expandVariables(var0));
    }

    public static Class<?> getMinecraftClass(String var0) {
        return getCanonicalClass(NMS_PREFIX + '.' + var0);
    }

    public static Class<?> getCraftBukkitClass(String var0) {
        return getCanonicalClass(OBC_PREFIX + '.' + var0);
    }

    private static Class<?> getCanonicalClass(String var0) {
        try {
            return Class.forName(var0);
        } catch (ClassNotFoundException var2) {
            throw new IllegalArgumentException("Cannot find " + var0, var2);
        }
    }

    private static String expandVariables(String var0) {
        StringBuffer var1 = new StringBuffer();

        Matcher var2;
        StringBuilder var4 = new StringBuilder();
        for(var2 = MATCH_VARIABLE.matcher(var0); var2.find(); var2.appendReplacement(var1, Matcher.quoteReplacement(var4.toString()))) {
            String var3 = var2.group(1);
            if ("nms".equalsIgnoreCase(var3)) {
                var4.append(NMS_PREFIX);
            } else if ("obc".equalsIgnoreCase(var3)) {
                var4.append(OBC_PREFIX);
            } else {
                if (!"version".equalsIgnoreCase(var3)) {
                    throw new IllegalArgumentException("Unknown variable: " + var3);
                }

                var4.append(VERSION);
            }

            if (var4.length() > 0 && var2.end() < var0.length() && var0.charAt(var2.end()) != '.') {
                var4.append('.');
            }
        }

        var2.appendTail(var1);
        return var1.toString();
    }

}
