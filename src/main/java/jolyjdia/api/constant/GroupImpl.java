package jolyjdia.api.constant;

import com.google.common.collect.Sets;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum GroupImpl {
    DEFAULT(0, "default", "§7", 1),

    ATOMIC(2, "atomic", "§a§lATOMIC", 1.25f),
    MAGMA(3, "magma", "§c§lMAGMA", 1.5f),
    HYPER(4, "hyper", "§5§lHYPER", 1.75f),
    COLLECTOR(5, "collector", "§6§lCOLLECTOR", 2),

    BUILDER(6, "builder", "§e§lBUILDER", 2),
    JUNIOR(7, "junior", "§2§lJUNIOR", 2),
    MODER(8, "moder", "§9§lMODER", 2),

    ADMIN(10, "admin", "§4§lСАНИТАР", 2.5f);

    private final int star;
    private final String name;
    private final String prefix;
    private final float multiplierCoin;
    private Set<String> permission;

    GroupImpl(int star, String name, String prefix, float multiplierCoin) {
        this.star = star;
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
        return star;
    }

    public Set<String> getPermission() {
        if(permission == null) {
            permission = new HashSet<>();
        }
        return permission;
    }

    public boolean hasPermission(int lvl) {
        return lvl >= star;
    }

    public boolean noPermission(int lvl) {
        return star < lvl;
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
        return Optional.ofNullable(GROUP_TABLE.get(lvl));
    }

    public static Optional<GroupImpl> getGroupByName(String name) {
        return Optional.ofNullable(GROUP_TABLE.get(name));
    }

    public static final TwoKeyEnumMap<Integer, String, GroupImpl> GROUP_TABLE = new TwoKeyEnumMap<>(GroupImpl.class);

    static {
        for (GroupImpl group : values()) {
            GROUP_TABLE.put(group.star, group.name, group);
        }
    }
}
