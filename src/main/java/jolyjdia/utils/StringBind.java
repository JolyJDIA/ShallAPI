package jolyjdia.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class StringBind {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private StringBind() {}

    public static @NotNull String toString(@NotNull String[] a) {
        int iMax = a.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; ; ++i) {
            builder.append(a[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(' ');
        }
    }
    @NotNull
    public static String toString(@NotNull Iterator<?> it) {
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (;;) {
            sb.append(it.next());
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(',');
        }
    }

    @NotNull
    public static String toString(@NotNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (;;) {
            sb.append(it.next());
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(',');
        }
    }

    @SafeVarargs
    @NotNull
    public static <U> String toString(@NotNull U... element) {
        int iMax = element.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; ; ++i) {
            builder.append(element[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(',');
        }
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    //todo: ГОВНО
    public static List<String> getAnimation(@NonNls String displayName) {
        List<String> animation = new ArrayList<>();

        String displayLine = displayName + "  ";
        char[] displayInfoArray = displayLine.toCharArray();
        char[] displayWorkArray = new char[displayInfoArray.length];
        int slotTextSee = 0;

        for (char sym : displayInfoArray) {
            int slot = displayInfoArray.length - 1;
            for (int g = 0; g < getSizeCharArray(displayWorkArray); g++) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < displayInfoArray.length; i++) {
                    if (displayWorkArray[i] == 0) {
                        if (i == slot) {
                            if (i == slotTextSee) {
                                displayWorkArray[slotTextSee] = sym;
                                line.append(sym);
                                slotTextSee++;
                            } else {
                                line.append("§e§l").append(sym);
                                slot -= 1;
                            }
                        } else {
                            line.append(' ');
                        }
                    } else {
                        line.append(displayWorkArray[i]);
                    }
                }

                animation.add(" §8§l» §6§l" + line);

            }
        }

        return animation;
    }
    private static int getSizeCharArray(char[] array) {
        int i = 0;
        for (char arr : array) {
            if (arr == 0) {
                i++;
            }
        }
        return i;
    }
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(JsonElement json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }
    public static <T> T fromJson(Reader json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }
}