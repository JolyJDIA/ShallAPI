package jolyjdia.gadgets.gadgets;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GadgetPlayer {
    public static Map<UUID, GadgetPlayer> map = Maps.newIdentityHashMap();
    private final Set<Gadget> currentGadget = Sets.newHashSet();
    private final Entity player;

    public GadgetPlayer(Entity player) {
        this.player = player;
    }

    public final GadgetPlayer addGadget(Gadget currentGadget) {
        this.currentGadget.add(currentGadget);
        return this;
    }
    public final GadgetPlayer addGadget(Gadget... currentGadget) {
        this.currentGadget.addAll(Sets.newHashSet(currentGadget));
        return this;
    }

    public Set<Gadget> getCurrentGadget() {
        return currentGadget;
    }

    public final Entity getPlayer() {
        return player;
    }

    public static Map<UUID, GadgetPlayer> getMap() {
        return map;
    }
}
