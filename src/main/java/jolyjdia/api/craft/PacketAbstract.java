package jolyjdia.api.craft;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class PacketAbstract implements PacketObject {
    private final Set<Player> visiblePlayer = new HashSet<>();
    private Player owner;

    @Override
    public Collection<? extends Player> getVisiblePlayers() {
        return visiblePlayer;
    }

    @Nullable
    @Override
    public Player getOwner() {
        return owner;
    }
}
