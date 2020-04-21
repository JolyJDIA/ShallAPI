package jolyjdia.api.constant;

import org.jetbrains.annotations.NotNull;
import java.util.*;

public enum GroupImpl {
    DEFAULT("default", "§7", 1),

    ATOMIC("atomic", "§a§lATOMIC", 1.25f),
    MAGMA("magma", "§c§lMAGMA", 1.5f),
    HYPER("hyper", "§5§lHYPER", 1.75f),
    COLLECTOR( "collector", "§6§lCOLLECTOR", 2),

    BUILDER("builder", "§e§lBUILDER", 2),
    JUNIOR("junior", "§2§lJUNIOR", 2),
    MODER("moder", "§9§lMODER", 2),

    ADMIN("admin", "§4§lСАНИТАР", 2.5f);

    private final String name;
    private final String prefix;
    private final float multiplierCoin;
    private Set<String> permission;

    GroupImpl(String name, String prefix, float multiplierCoin) {
        this.name = name;
        this.prefix = prefix;
        this.multiplierCoin = multiplierCoin;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getStar() {
        return ordinal();
    }

    public Set<String> getPermission() {
        if(permission == null) {
            permission = new HashSet<>();
        }
        return permission;
    }

    public boolean hasPermission(int lvl) {
        return lvl >= ordinal();
    }

    public boolean noPermission(int lvl) {
        return ordinal() < lvl;
    }

    public float getMultiplierCoin() {
        return multiplierCoin;
    }

    public static @NotNull String names() {
        GroupImpl[] groups = values();
        int iMax = groups.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; ; ++i) {
            builder.append(groups[i].name);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(", ");
        }
    }

    public static Optional<GroupImpl> getGroupByLvl(int lvl) {
        return Optional.ofNullable(values()[lvl]);
    }

    public static Optional<GroupImpl> getGroupByName(String name) {
        return Optional.ofNullable(GROUP_TABLE.get(name));
    }

    public static final Map<String, GroupImpl> GROUP_TABLE = new HashMap<>(values().length);

    static {
        for(GroupImpl group : values()) {
            GROUP_TABLE.put(group.name, group);
        }
    }
}
