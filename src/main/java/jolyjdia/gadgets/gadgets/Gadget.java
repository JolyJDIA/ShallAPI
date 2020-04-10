package jolyjdia.gadgets.gadgets;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public abstract class Gadget implements Runnable {
    private final Entity player;
    private boolean async;

    protected Gadget(Entity player, boolean async) {
        this.player = player;
        this.async = async;
    }
    protected Gadget(Entity player) {
        this.player = player;
    }

    public abstract void start();
    public abstract void stop();

    public Entity getPlayer() {
        return player;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
    public Location getLocation() {
        return player.getLocation();
    }
    public World getWorld() {
        return player.getWorld();
    }
}
