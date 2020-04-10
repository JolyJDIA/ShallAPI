package jolyjdia.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class UtilLocation {
    private UtilLocation() {}
    public static List<Player> getClosestPlayersFromLocation(Location loc, float v) {
        List<Player> list = new ArrayList<>();
        for (Player p : Objects.requireNonNull(loc.getWorld()).getPlayers()) {
            if (p.getLocation().add(0.0f, 0.85f, 0.0f).distanceSquared(loc) <= (v * v) && !p.hasMetadata("NPC")) {
                list.add(p);
            }
        }

        return list;
    }
}

