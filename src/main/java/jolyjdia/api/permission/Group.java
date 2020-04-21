package jolyjdia.api.permission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated
public class Group {
    private final Set<String> permission;
    private String name;
    private String prefix;
    private String suffix;
    private final int level;
    private final float multipleCoins = 1;

    public Group(int level, String name, Set<String> permission) {
        this.level = level;
        this.name = name;
        this.permission = permission;
    }
    public Group(int id, String name, List<String> permission, String prefix, String suffix) {
        this.level = id;
        this.name = name;
        this.permission = new HashSet<>(permission);
        this.prefix = prefix;
        this.suffix = suffix;

    }
    public final String getPrefix() {
        return prefix;
    }

    public final String getSuffix() {
        return suffix;
    }

    public final void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public final void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public final Set<String> getPermission() {
        return permission;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final int getLevel() {
        return level;
    }
}
