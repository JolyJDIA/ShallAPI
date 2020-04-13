package jolyjdia.api.permission;

import jolyjdia.nms.util.ReflectionUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

@Deprecated
public final class PermissibleInjector {
    private static final Field PERMISSIBLE_FIELD;

    static {
        @Nullable Field field;
        try {
            Class<?> human = ReflectionUtils.getCraftBukkitClass("entity.CraftHumanEntity");
            field = human.getDeclaredField("perm");
            field.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            field = null;
        }
        PERMISSIBLE_FIELD = field;
    }
    private PermissibleInjector() {}

    public static void inject(Player player, @NotNull SPermissibleBase base) {
        try {
            PERMISSIBLE_FIELD.set(player, base);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}