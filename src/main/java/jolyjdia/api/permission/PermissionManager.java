package jolyjdia.api.permission;

import jolyjdia.Main;
import jolyjdia.api.file.ConfigAccessor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Deprecated
public final class PermissionManager {
    private static final Map<Integer, Group> GROUPS = new HashMap<>();

    private PermissionManager() {}

    public static void calculatePermission(Main main) {
        @NonNls FileConfiguration cfg = new ConfigAccessor(main, "permission.yml").getConfig();
        if(cfg.get("group.") == null) {
            throw new NullPointerException("[Group] А где группы, черт?");
        }
        Objects.requireNonNull(cfg.getConfigurationSection("group")).getKeys(false).forEach(groupName -> {
            List<String> list = cfg.getStringList("group." + groupName + ".permissions");
            List<String> inheritance = cfg.getStringList("group." + groupName + ".inheritance");

            inheritance.forEach(depend -> list.addAll(cfg.getStringList("group." + depend + ".permissions")));

            int lvl = cfg.getInt("group." + groupName + ".id");//lvl
            GROUPS.put(lvl, new Group(lvl, groupName, list,
                    cfg.getString("group." + groupName + ".prefix"),
                    cfg.getString("group." + groupName + ".suffix"))
            );
        });
    }
    public static Group getGroup(int groupID) {
        Group group = GROUPS.get(groupID);
        if (group != null) {
            return group;
        }
        return getDefaultGroup();
    }
    public static Group getGroupByName(String name) {
        return GROUPS.values().stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(getDefaultGroup());
    }
    public static void addGroup(Group group) {
        GROUPS.put(group.getLevel(), group);
    }

    public static boolean notExistGroup(String name) {
        return GROUPS.values().stream().noneMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    public static @NotNull Iterable<String> groupNameSet() {
        return GROUPS.values().stream().map(Group::getName).collect(Collectors.toUnmodifiableSet());
    }
    public static Group getDefaultGroup() {
        return GROUPS.get(0);
    }
}
