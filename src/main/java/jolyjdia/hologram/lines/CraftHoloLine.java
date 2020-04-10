package jolyjdia.hologram.lines;

import jolyjdia.api.craftentity.EntityAPI;
import jolyjdia.api.craftentity.stand.CustomStand;
import jolyjdia.api.hologram.HoloLine;
import jolyjdia.api.hologram.Hologram;
import jolyjdia.hologram.CraftHologram;
import jolyjdia.hologram.HologramManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CraftHoloLine implements HoloLine {

    private static final EntityAPI ENTITY_API = null;

    private final HologramManager hologramManager;
    protected final Hologram hologram;
    protected final CustomStand customStand;

    CraftHoloLine(CraftHologram hologram, Location location) {
        this.hologram = hologram;
        this.hologramManager = hologram.getHologramManager();

        customStand = ENTITY_API.createStand(location);
        customStand.setInvisible(true);
        customStand.setSmall(true);
        customStand.setBasePlate(false);

        hologramManager.addCustomStand(hologram, customStand);
    }

    public void remove() {
        hologramManager.removeCustomStand(customStand);
        customStand.remove();
    }

    @Override
    public CustomStand getCustomStand() {
        return customStand;
    }

    @Override
    public void delete() {
        hologram.removeLine(this);
    }

    public void teleport(Location location){
        customStand.onTeleport(location);
    }

    public void showTo(Player player) {
        customStand.showTo(player);
    }

    public void hideTo(Player player) {
        customStand.removeTo(player);
    }

    public void setPublic(boolean vision) {
        customStand.setPublic(vision);
    }

    public boolean isVisibleTo(Player player) {
        return customStand.isVisibleTo(player);
    }
}
