package jolyjdia.api.permission;

import jolyjdia.api.player.GamePlayer;
import jolyjdia.api.player.GamerData;
import org.bukkit.permissions.PermissibleBase;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Deprecated
public final class SPermissibleBase extends PermissibleBase {
    private final GamerData account;

    public SPermissibleBase(@NotNull GamePlayer opable) {
        super(opable.getPlayer());
        this.account = opable;
    }
    @Deprecated
    @Override
    public boolean hasPermission(@NotNull String permission) {
        return false;
        //Set<String> permissions = account.getGroup().getPermission();
       // return permissions != null && !permission.isEmpty() && (checkStar(permission) || permissions.contains(permission));
    }
    private boolean checkStar(@NotNull String permission) {
        return false;
       // return permission.endsWith(".*") || account.getGroup().getPermission().stream().anyMatch(e -> !e.isEmpty() && e.charAt(0) == '*');
    }
    //TODO: наследовать все методы
}