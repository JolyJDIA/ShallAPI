package jolyjdia.api.constant;

import com.google.common.collect.ImmutableMap;

public enum GroupImp {
    DEFAULT(0, "default", "§7", 1),

    ATOMIC(2, "atomic", "§a§lATOMIC", 1.25f),
    MAGMA(3, "magma", "§c§lMAGMA", 1.5f),
    HYPER(4, "hyper", "§5§lHYPER", 1.75f),
    COLLECTOR(5, "collector", "§e§lCOLLECTOR", 2),

    BUILDER(6, "builder", "§6§lBUILDER", 2),
    JUNIOR(7, "junior", "§d§lJUNIOR", 2),
    MODER(8, "moder", "§9§lMODER", 2),

    ADMIN(10, "admin", "§4§lСАНИТАР", 5);

    private final int star;
    private final String name;
    private final String prefix;
    private final float multiplierCoin;

    GroupImp(int star, String name, String prefix, float multiplierCoin) {
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
    public boolean hasPermission(int lvl) {
        return lvl >= star;
    }
    public boolean noPermission(int lvl) {
        return star < lvl;
    }

    public float getMultiplierCoin() {
        return multiplierCoin;
    }

    public static GroupImp getGroupByLvl(int lvl) {
        return GROUP_TABLE.get(lvl);
    }
    public static GroupImp getGroupByName(String name) {
        return GROUP_TABLE.get(name);
    }
    private static final ImmutableMap<Object, GroupImp> GROUP_TABLE;

    static {
        ImmutableMap.Builder<Object, GroupImp> builder = ImmutableMap.builder();
        for(GroupImp group : values()) {
            builder.put(group.star, group).put(group.name, group);
        }
        GROUP_TABLE = builder.build();
    }
}
