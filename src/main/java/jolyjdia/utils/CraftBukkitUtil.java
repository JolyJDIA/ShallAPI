package jolyjdia.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@Deprecated
public final class CraftBukkitUtil {
    @NonNls private static final String CRAFTBUKKIT = "org.bukkit.craftbukkit";

    private CraftBukkitUtil() {}

    @NonNls private static final String SERVER_PACKAGE_VERSION = getServerPackageVersion();

    private static @NotNull String getServerPackageVersion() {
        Class<?> server = Bukkit.getServer().getClass();
        if (!server.getSimpleName().equals("CraftServer")) {
            return ".";
        }
        if (server.getName().equals("org.bukkit.craftbukkit.CraftServer")) {
            // Non versioned class
            return ".";
        } else {
            String version = server.getName().substring(CRAFTBUKKIT.length());
            return version.substring(0, version.length() - "CraftServer".length());
        }
    }



    public static @NotNull String nms(String className) {
        return "net.minecraft.server" + SERVER_PACKAGE_VERSION + className;
    }


    public static @NotNull String obc(String className) {
        return CRAFTBUKKIT + SERVER_PACKAGE_VERSION + className;
    }

    public static Class<?> obcClass(String className) throws ClassNotFoundException {
        return Class.forName(obc(className));
    }
}