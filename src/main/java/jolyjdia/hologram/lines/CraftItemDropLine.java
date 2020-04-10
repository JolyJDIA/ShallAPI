package jolyjdia.hologram.lines;

import jolyjdia.api.hologram.Hologram;
import jolyjdia.api.hologram.lines.ItemDropLine;
import jolyjdia.hologram.CraftHologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class CraftItemDropLine extends CraftHoloLine implements ItemDropLine {
    private final Material itemStack;
    private boolean pickup;

    public CraftItemDropLine(CraftHologram hologram, Location location, boolean pickup, Material item) {
        super(hologram, location);
        this.pickup = pickup;
        this.itemStack = item;
        this.setItem(itemStack);
    }

    @Override
    public Material getItemStack() {
        return itemStack;
    }

    @Override
    public void setItem(Material item) {
        customStand.setItemPassenger(item);
    }

    @Override
    public boolean isPickup() {
        return pickup;
    }

    @Override
    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public void checkPickup(Player player, Hologram hologram) {
        if (!(hologram.isVisibleTo(player) || hologram.isPublic())) {
            return;
        }

        /*double range = LocationUtil.distance(player.getLocation(), customStand.getLocation());
        //todo: range != -1
        if (range < 1.5 && range != -1) {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer == null) {
                return;
            }

            GamerPickupHoloEvent event = new GamerPickupHoloEvent(gamer, hologram, this);
            BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
            if (event.isRemove()) {
                delete();
            }
        }*/
    }
}
